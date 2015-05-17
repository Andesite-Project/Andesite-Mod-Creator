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
 * Action that removes all drops from a given block.
 * @author Marius
 */
@ActionData(id = 5, version = 1)
public class BlockRemoveDefaultDropsAction extends DataStreamActionWrapper implements Action, BlockAction {
    
    /**
     * The block ID this action applies to.
     */
    private String blockId;

    /**
     * Executes the action.
     */
    @Override
    public void execute() {
        BlockWrapper.getFor(blockId).removeDefaultDrops();
    }
    
    /**
     * Creates an instance of BlockRemoveDefaultDropsAction.
     * @param blockId The block ID this action should apply to
     * @return An instance of BlockRemoveDefaultDropsAction.
     */
    public static BlockRemoveDefaultDropsAction create(String blockId) {
        BlockRemoveDefaultDropsAction a = new BlockRemoveDefaultDropsAction();
        a.setBlockId(blockId);
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
     * Creates an instance of this action from an input stream.
     * @param input The input stream to read from
     * @return An instance of BlockRemvoeDefaultDropsAction
     * @throws IOException I/O fails when reading from input
     */
    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.blockId = AndesiteIO.readString(input);
        return this;
    }

    /**
     * Writes this action instance to an output stream.
     * @param output The output stream to write to
     * @throws IOException I/O fails when writing to output
     */
    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.blockId, output);
    }

}
