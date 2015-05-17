/*
 * The MIT License
 *
 * Copyright 2015 Marius.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.varden.andesite.action;

import info.varden.andesite.action.base.DataStreamActionWrapper;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.core.BlockBreakSource;
import info.varden.andesite.core.PlayerCondition;
import info.varden.andesite.core.PlayerConditions;
import info.varden.andesite.core.PlayerRequirements;
import info.varden.andesite.core.SilkTouchMode;
import info.varden.andesite.core.VariableVersionParser;
import info.varden.andesite.io.AndesiteIO;
import info.varden.andesite.modloader.BlockWrapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.minecraft.item.ItemStack;

/**
 * Action for changing drops of blocks when certain criteria is met.
 * @author Marius
 */
@ActionData(id = 6, version = 1)
public class BlockConditionalDropAction extends DataStreamActionWrapper implements Action, BlockAction, VariableVersionParser {
    
    /**
     * The ID of the block this action applies to.
     */
    private String blockId;
    /**
     * Silk touch status criterion.
     */
    private SilkTouchMode mode;
    /**
     * Source of block breaking criterion.
     */
    private BlockBreakSource source;
    /**
     * Fortune level criterion.
     */
    private int fortuneLevel;
    /**
     * List of items the block should drop.
     */
    private List<ItemStack> items; // TODO: Can't use net.minecraft here, I need to write a wrapper for this.
    /**
     * The chance for each ItemStack to drop.
     */
    private float dropChance;
    /**
     * Whether or not this action should be set to override other drops when all criteria for this action are met.
     */
    private boolean overrideDrops;
    /**
     * List of player conditions required for this action to apply if the block break source was a player.
     */
    private PlayerRequirements conditions;
    
    /**
     * Minimum required Andesite version for this action instance to execute.
     */
    private int version = 1;
    /**
     * Whether or not this action is supported by the current Andesite instance.
     */
    private boolean supported = true;

    /**
     * Executes the action.
     */
    @Override
    public void execute() {
        BlockWrapper.getFor(blockId).setConditionalDrops(this.mode, this.source, this.fortuneLevel, this.items, this.dropChance, this.overrideDrops, this.conditions);
    }
    
    /**
     * Creates an instance of BlockConditionalDropAction.
     * @param blockId The block ID this action applies to
     * @param mode Required silk touch status
     * @param source Required block break origin
     * @param fortuneLevel Required exact Fortune level, or -1 for any value
     * @param items List of items to drop
     * @param dropChance The chance for each item stack to drop
     * @param overrideDrops Whether or not this action should be set to override other drops when all criteria for this action are met
     * @param conditions List of player conditions required for this action to apply if the block break source was a player
     * @return An instance of BlockConditionalDropAction initialized with the given criteria
     */
    public static BlockConditionalDropAction create(String blockId, SilkTouchMode mode, BlockBreakSource source, int fortuneLevel, List<ItemStack> items, float dropChance, boolean overrideDrops, PlayerRequirements conditions) {
        BlockConditionalDropAction a = new BlockConditionalDropAction();
        a.setBlockId(blockId);
        a.setSilkTouchMode(mode);
        a.setBlockBreakSource(source);
        a.setFortuneLevel(fortuneLevel);
        a.setItems(items);
        a.setDropChance(dropChance);
        a.setOverrideDrops(overrideDrops);
        a.setPlayerRequirements(conditions);
        return a;
    }
    
    /**
     * Gets the required Andesite version for this action to run.
     * @return Andesite version number
     */
    @Override
    public int getVersion() {
        return version;
    }
    
    /**
     * Gets whether or not this action instance is supported under the current Andesite instance.
     * @return True if supported, false otherwise
     */
    @Override
    public boolean isSupported() {
        return this.supported;
    }
    
    /**
     * Reads an instance of this action from a DataInputStream.
     * @param input The input stream to read from
     * @return An instance of BlockConditionalDropAction
     * @throws IOException I/O fail when reading from input
     */
    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.blockId = AndesiteIO.readString(input);
        this.mode = SilkTouchMode.findById(input.readInt());
        this.source = BlockBreakSource.findById(input.readInt());
        this.fortuneLevel = input.readInt();
        // items
        int itemCount = input.readInt();
        for (int i = 0; i < itemCount; i++) {
            
        }
        this.dropChance = input.readFloat();
        this.overrideDrops = input.readBoolean();
        this.conditions = new PlayerRequirements();
        int condCount = input.readInt();
        for (int i = 0; i < condCount; i++) {
            try {
                PlayerCondition condition = PlayerConditions.readFromInput(input);
                if (condition == null) {
                    this.supported = false;
                    continue;
                }
                this.conditions.require(condition);
            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException("Exception while parsing action", e);
            }
        }
        return this;
    }

    /**
     * Writes this instance of BlockConditionalDropAction to a DataOutputStream.
     * @param output The output stream to write to
     * @throws IOException I/O fail when writing to output
     */
    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.blockId, output);
        output.writeInt(this.mode.getID());
        output.writeInt(this.source.getID());
        output.writeInt(this.fortuneLevel);
        // items
        output.writeFloat(this.dropChance);
        output.writeBoolean(this.overrideDrops);
        output.writeInt(this.conditions.getRequirementCount());
        for (PlayerCondition condition : this.conditions.getRequirements()) {
            PlayerConditions.writeToOutput(condition, output);
        }
    }
    
    /**
     * Sets the block ID for this action.
     * @param id The block ID
     */
    public void setBlockId(String id) {
        this.blockId = id;
    }
    
    /**
     * Gets the block ID for this action.
     * @return The block ID
     */
    @Override
    public String getBlockId() {
        return this.blockId;
    }
    
    /**
     * Sets the required silk touch mode for this action.
     * @param mode The silk touch mode to set
     */
    public void setSilkTouchMode(SilkTouchMode mode) {
        this.mode = mode;
    }
    
    /**
     * Gets the required silk touch mode for this action.
     * @return The silk touch mode
     */
    public SilkTouchMode getSilkTouchMode() {
        return this.mode;
    }
    
    /**
     * Sets the required block break source for this action.
     * @param source The block break source to set
     */
    public void setBlockBreakSource(BlockBreakSource source) {
        this.source = source;
    }
    
    /**
     * Gets the required block break source for this action.
     * @return The block break source
     */
    public BlockBreakSource getBlockBreakSource() {
        return this.source;
    }
    
    /**
     * Sets the required fortune level for this action.
     * @param fortuneLevel The fortune level to set, or -1 for any
     */
    public void setFortuneLevel(int fortuneLevel) {
        this.fortuneLevel = fortuneLevel;
    }
    
    /**
     * Gets the required fortune level for this action
     * @return The fortune level, or -1 for any
     */
    public int getFortuneLevel() {
        return this.fortuneLevel;
    }
    
    /**
     * Sets the item drops for this action
     * @param items List of item drops to set
     */
    public void setItems(List<ItemStack> items) {
        this.items = items;
    }
    
    /**
     * Gets the item drops for this action
     * @return List of item drops
     */
    public List<ItemStack> getItems() {
        return this.items;
    }
    
    /**
     * Sets the drop chance for this action
     * @param dropChance The drop chance to set
     */
    public void setDropChance(float dropChance) {
        this.dropChance = dropChance;
    }
    
    /**
     * Gets the drop chance for this action
     * @return The drop chance
     */
    public float getDropChance() {
        return this.dropChance;
    }
    
    /**
     * Sets whether or not this action should be set to override other drops when all criteria for this action are met.
     * @param overrideDrops True to override existing drops, false otherwise
     */
    public void setOverrideDrops(boolean overrideDrops) {
        this.overrideDrops = overrideDrops;
    }
    
    /**
     * Gets whether or not this action should be set to override other drops when all criteria for this action are met.
     * @return True if overriding existing drops, false otherwise
     */
    public boolean getOverrideDrops() {
        return this.overrideDrops;
    }
    
    /**
     * Sets the requirements for the player breaking the block, if any.
     * @param conditions The player requirements to set
     */
    public void setPlayerRequirements(PlayerRequirements conditions) {
        this.conditions = conditions;
    }
    
    /**
     * Gets the requirements for the player breaking the block, if any.
     * @return The player requirements
     */
    public PlayerRequirements getPlayerRequirements() {
        return this.conditions;
    }

}
