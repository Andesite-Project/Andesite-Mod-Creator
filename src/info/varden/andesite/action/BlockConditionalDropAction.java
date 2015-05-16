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
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.item.ItemStack;

@ActionData(id = 6, version = 1)
public class BlockConditionalDropAction extends DataStreamActionWrapper implements Action, BlockAction, VariableVersionParser {
    
    private String blockId;
    private SilkTouchMode mode;
    private BlockBreakSource source;
    private int fortuneLevel;
    private List<ItemStack> items; // TODO: Can't use net.minecraft here, I need to write a wrapper for this.
    private float dropChance;
    private boolean overrideDrops;
    private PlayerRequirements conditions;
    
    private int version = 1;
    private boolean supported = true;

    @Override
    public void execute() {
        BlockWrapper.getFor(blockId).setConditionalDrops(this.mode, this.source, this.fortuneLevel, this.items, this.dropChance, this.overrideDrops, this.conditions);
    }
    
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
    
    @Override
    public int getVersion() {
        return version;
    }
    
    @Override
    public boolean isSupported() {
        return this.supported;
    }
    
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
    
    public void setBlockId(String id) {
        this.blockId = id;
    }
    
    @Override
    public String getBlockId() {
        return this.blockId;
    }
    
    public void setSilkTouchMode(SilkTouchMode mode) {
        this.mode = mode;
    }
    
    public SilkTouchMode getSilkTouchMode() {
        return this.mode;
    }
    
    public void setBlockBreakSource(BlockBreakSource source) {
        this.source = source;
    }
    
    public BlockBreakSource getBlockBreakSource() {
        return this.source;
    }
    
    public void setFortuneLevel(int fortuneLevel) {
        this.fortuneLevel = fortuneLevel;
    }
    
    public int getFortuneLevel() {
        return this.fortuneLevel;
    }
    
    public void setItems(List<ItemStack> items) {
        this.items = items;
    }
    
    public List<ItemStack> getItems() {
        return this.items;
    }
    
    public void setDropChance(float dropChance) {
        this.dropChance = dropChance;
    }
    
    public float getDropChance() {
        return this.dropChance;
    }
    
    public void setOverrideDrops(boolean overrideDrops) {
        this.overrideDrops = overrideDrops;
    }
    
    public boolean getOverrideDrops() {
        return this.overrideDrops;
    }
    
    public void setPlayerRequirements(PlayerRequirements conditions) {
        this.conditions = conditions;
    }
    
    public PlayerRequirements getPlayerRequirements() {
        return this.conditions;
    }

}
