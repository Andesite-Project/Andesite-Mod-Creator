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
 * Enum signifying a silk touch status.
 * @author Marius
 */
public enum SilkTouchMode {
    /**
     * No silk touch.
     */
    NORMAL(0),
    /**
     * Silk touch applies.
     */
    SILKTOUCH(1),
    /**
     * Any silk touch mode.
     */
    ANY(2);
    
    /**
     * The save ID of this SilkTouchMode.
     */
    private final int id;
    
    /**
     * Initializes a SilkTouchMode.
     * @param id The save ID
     */
    private SilkTouchMode(int id) {
        this.id = id;
    }
    
    /**
     * Gets the save ID of the SilkTouchMode.
     * @return The save ID
     */
    public int getID() {
        return this.id;
    }
    
    /**
     * Finds a SilkTouchMode by its save ID.
     * @param id The save ID to look up
     * @return A SilkTouchMode
     */
    public static SilkTouchMode findById(int id) {
        for (SilkTouchMode mode : SilkTouchMode.values()) {
            if (mode.getID() == id) {
                return mode;
            }
        }
        throw new UnsupportedOperationException("Silk touch mode ID " + id + " not found!");
    }
}
