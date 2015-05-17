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

import info.varden.andesite.action.base.IdDataAction;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.modloader.BlockWrapper;

/**
 * Action that sets a block's slipperiness.
 * @author Marius
 */
@ActionData(id = 2, version = 1)
public class BlockSlipperinessAction extends IdDataAction<Float> implements Action, BlockAction {

    /**
     * Executes the action.
     */
    @Override
    public void execute() {
        BlockWrapper.getFor(getID()).setSlipperiness(getData().floatValue());
    }
    
    /**
     * Creates an instance of BlockSlipperinessAction.
     * @param blockId The block ID this action should apply to
     * @param slipperiness The desired slipperiness of the block
     * @return An instance of BlockSlipperinessAction
     */
    public static BlockSlipperinessAction create(String blockId, float slipperiness) {
        BlockSlipperinessAction a = new BlockSlipperinessAction();
        a.setBlockId(blockId);
        a.setSlipperiness(slipperiness);
        return a;
    }
   
    /**
     * Sets the block ID this action should apply to.
     * @param id The block ID to set
     */
    public void setBlockId(String id) {
        setID(id);
    }
    
    /**
     * Gets the block ID this action should apply to.
     * @return The block ID
     */
    @Override
    public String getBlockId() {
        return getID();
    }
    
    /**
     * Sets the slipperiness of the block
     * @param slipperiness The slipperiness to set
     */
    public void setSlipperiness(float slipperiness) {
        setData(slipperiness);
    }
    
    /**
     * Gets the slipperiness of the block
     * @return The slipperiness
     */
    public float getSlipperiness() {
        return getData();
    }

    /**
     * Returns the underlying data type for this action.
     * @return The data type
     */
    @Override
    public Class<Float> getDataClass() {
        return Float.class;
    }

}
