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
package info.varden.andesite.action.base;

import info.varden.andesite.core.Action;
import info.varden.andesite.core.Utils;
import info.varden.andesite.io.AndesiteIO;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Abstraction layer that simplifies creation of actions storing data as a String-T key-value pair.
 * @author Marius
 * @param <T> The type of data this action stores
 */
public abstract class IdDataAction<T> extends DataStreamActionWrapper implements Action {
    
    /**
     * The key of the data key-value pair.
     */
    private String id;
    /**
     * The value of the data key-value pair.
     */
    private T data;
    
    /**
     * Returns the T generic data value type for this action.
     * @return The type class
     */
    public abstract Class<T> getDataClass();

    /**
     * Creates an action instance from the given input stream.
     * @param input The input stream to read from
     * @return An action instance
     * @throws IOException I/O fails when reading from input, or T is not supported by Utils
     */
    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.id = AndesiteIO.readString(input);
        Utils.readGenericFromInput(input, getDataClass());
        return this;
    }

    /**
     * Writes the action instance to the given output stream.
     * @param output The output stream to write to
     * @throws IOException I/O fails when writing to output, or T is not supported by Utils
     */
    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.id, output);
        Utils.writeGenericToOutput(output, this.data);
    }
    
    /**
     * Gets the data stored in the key-value pair.
     * @return The stored T-type data
     */
    protected T getData() {
        return this.data;
    }
    
    /**
     * Gets the ID stored in the key-value pair.
     * @return The stored String key
     */
    protected String getID() {
        return this.id;
    }
    
    /**
     * Sets the data value of the key-value pair.
     * @param data The T-type data to set.
     */
    protected void setData(T data) {
        this.data = data;
    }
    
    /**
     * Sets the String key of the key-value pair.
     * @param id The String key to set.
     */
    protected void setID(String id) {
        this.id = id;
    }
    
}
