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
import info.varden.andesite.io.AndesiteIO;
import info.varden.andesite.modloader.BlockWrapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Action that determines the hardness and resistance of a given block.
 * @author Marius
 */
@ActionData(id = 3, version = 1)
public class BlockHardnessAction extends DataStreamActionWrapper implements Action, BlockAction {
    
    /**
     * The block ID for this action.
     */
    private String blockId;
    /**
     * Resistance value for the given block.
     */
    private float resistance;
    /**
     * Hardness value for the given block.
     */
    private float hardness;

    /**
     * Executes the action.
     */
    @Override
    public void execute() {
        BlockWrapper.getFor(blockId).setHardness(hardness).setResistance(resistance);
    }
    
    /**
     * Creates an instance of BlockHardnessAction.
     * @param blockId The block ID this action applies to
     * @param hardness The hardness of the block
     * @param resistance The resistance of the block
     * @return An instance of BlockHardnessAction with the given hardness and resistance values.
     */
    public static BlockHardnessAction create(String blockId, float hardness, float resistance) {
        BlockHardnessAction a = new BlockHardnessAction();
        a.setBlockId(blockId);
        a.setHardness(hardness);
        a.setResistance(resistance);
        return a;
    }
    
    /**
     * Sets the block ID this action applies to.
     * @param id The block ID to set
     */
    public void setBlockId(String id) {
        this.blockId = id;
    }
    
    /**
     * Gets the block ID this action applies to.
     * @return The block ID
     */
    @Override
    public String getBlockId() {
        return this.blockId;
    }
    
    /**
     * Sets the hardness value for the given block.
     * @param hardness The hardness value to set
     */
    public void setHardness(float hardness) {
        this.hardness = hardness;
    }
    
    /**
     * Gets the hardness value for the given block.
     * @return The hardness value
     */
    public float getHardness() {
        return this.hardness;
    }
    
    /**
     * Sets the resistance value for the given block.
     * @param resistance The resistance value to set
     */
    public void setResistance(float resistance) {
        this.resistance = resistance;
    }
    
    /**
     * Gets the resistance value for the given block.
     * @return The resistance value
     */
    public float getResistance() {
        return this.resistance;
    }

    /**
     * Creates an instance of BlockHardnessAction by reading from a stream.
     * @param input The input stream to read from
     * @return An instance of BlockHardnessAction
     * @throws IOException I/O fails when reading from stream
     */
    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.blockId = AndesiteIO.readString(input);
        this.hardness = input.readFloat();
        this.resistance = input.readFloat();
        return this;
    }

    /**
     * Writes this action to a stream.
     * @param output The output stream to write to
     * @throws IOException I/O fails when writing to the stream
     */
    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.blockId, output);
        output.writeFloat(this.hardness);
        output.writeFloat(this.resistance);
    }

}
