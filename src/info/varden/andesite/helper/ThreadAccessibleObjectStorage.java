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
package info.varden.andesite.helper;

/**
 * Class that allows getting and setting of objects in a final context.
 * @author Marius
 */
public class ThreadAccessibleObjectStorage<T> {
    /**
     * The current data.
     */
    private volatile T object;
    
    /**
     * Initializes the storage with initial data.
     * @param init The initial data
     */
    public ThreadAccessibleObjectStorage(T init) {
        this.object = init;
    }
    
    /**
     * Sets the stored data.
     * @param object The data to set
     */
    public void set(T object) {
        this.object = object;
    }
    
    /**
     * Gets the stored data.
     * @return The data
     */
    public T get() {
        return this.object;
    }
}
