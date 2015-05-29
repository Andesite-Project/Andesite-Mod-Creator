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
 * Action that decides the particle gravity of a block.
 * @author Marius
 */
@ActionData(id = 4, version = 1)
public class BlockParticleGravityAction extends IdDataAction<Float> implements Action, BlockAction {

    /**
     * Executes this action.
     */
    @Override
    public void execute() {
        BlockWrapper.getFor(getID()).setParticleGravity(getData().floatValue());
    }
    
    /**
     * Creates an instance of BlockParticleGravityAction.
     * @param blockId The block ID this action should apply to.
     * @param gravityLevel The gravitational force in meters per square seconds.
     * @return An instance of BlockParticleGravityAction
     */
    public static BlockParticleGravityAction create(String blockId, float gravityLevel) {
        BlockParticleGravityAction a = new BlockParticleGravityAction();
        a.setBlockId(blockId);
        a.setParticleGravity(gravityLevel);
        return a;
    }
    
    /**
     * Sets the block ID this action applies to.
     * @param id The block ID to set
     */
    public void setBlockId(String id) {
        setID(id);
    }
    
    /**
     * Gets the block ID this applies to.
     * @return The block ID
     */
    @Override
    public String getBlockId() {
        return getID();
    }
    
    /**
     * Sets the gravitational force of the block's particles.
     * @param gravityLevel The gravitational force to set in meters per square seconds
     */
    public void setParticleGravity(float gravityLevel) {
        setData(gravityLevel);
    }
    
    /**
     * Gets the gravitational force of the block's particles.
     * @return The gravitational force in meters per square seconds
     */
    public float getParticleGravity() {
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
