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
package info.varden.andesite.core;

/**
 * Enum which signifies what broke a block.
 * @author Marius
 */
public enum BlockBreakSource {
    /**
     * Player broke the block.
     */
    PLAYER(0),
    /**
     * Non-player broke the block.
     */
    OTHER(1),
    /**
     * Anything broke the block.
     */
    ANY(2);
    
    /**
     * The save ID of the BlockBreakSource.
     */
    private final int id;
    
    /**
     * Initializes the BlockBreakSource.
     * @param id The ID of the source
     */
    private BlockBreakSource(int id) {
        this.id = id;
    }
    
    /**
     * Gets the save ID of the BlockBreakSource.
     * @return The ID of the source
     */
    public int getID() {
        return this.id;
    }
    
    /**
     * Finds a BlockBreakSource by its save ID.
     * @param id The ID of the source to look up
     * @return A BlockBreakSource
     */
    public static BlockBreakSource findById(int id) {
        for (BlockBreakSource mode : BlockBreakSource.values()) {
            if (mode.getID() == id) {
                return mode;
            }
        }
        throw new UnsupportedOperationException("Block break source ID " + id + " not found!");
    }
}
