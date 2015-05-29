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
 * Comparisons between two values.
 * @author Marius
 */
public enum CompareMode {
    /**
     * A less than B
     */
    LESS_THAN(0),
    /**
     * A less than or equal to B
     */
    LESS_THAN_OR_EQUALS(1),
    /**
     * A equal to B
     */
    EQUALS(2),
    /**
     * A greater than or equal to B
     */
    GREATER_THAN_OR_EQUALS(3),
    /**
     * A greater than B
     */
    GREATER_THAN(4),
    /**
     * A not equal to B
     */
    NOT_EQUALS(5);
    
    /**
     * The save ID for this CompareMode.
     */
    private final int id;
    
    /**
     * Initializes the CompareMode.
     * @param id The save ID of the CompareMode.
     */
    private CompareMode(int id) {
        this.id = id;
    }
    
    /**
     * Gets the save ID of the CompareMode.
     * @return The save ID
     */
    public int getID() {
        return this.id;
    }
    
    /**
     * Finds a CompareMode by its save ID.
     * @param id The CompareMode save ID to look up
     * @return A CompareMode
     */
    public static CompareMode findById(int id) {
        for (CompareMode mode : CompareMode.values()) {
            if (mode.getID() == id) {
                return mode;
            }
        }
        throw new UnsupportedOperationException("Comparison mode ID " + id + " not found!");
    }
}
