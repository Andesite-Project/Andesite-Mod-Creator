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
import info.varden.andesite.io.Serializable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Abstraction layer made to make it easier for actions to parse and write by converting the required byte arrays to data input and output streams.
 * @author Marius
 */
public abstract class DataStreamActionWrapper implements Action, Serializable {

    /**
     * Creates an action instance from the given raw data.
     * @param aeData The raw data to parse
     * @return An action instance
     */
    @Override
    public Action parse(byte[] aeData) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(aeData));
        try {
            Action a = parse(dis);
            dis.close();
            return a;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Writes the action instance to a raw data byte array.
     * @return Raw data representing the action
     */
    @Override
    public byte[] toData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            write(dos);
            dos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Creates an action instance from the given input stream.
     * @param input The input stream to read from
     * @return An action instance
     * @throws IOException I/O fails when reading from the input stream
     */
    public abstract Action parse(DataInputStream input) throws IOException;
    /**
     * Writes the action instance to the given output stream.
     * @param output The output stream to write to
     * @throws IOException I/O fails when writing to the output stream
     */
    public abstract void write(DataOutputStream output) throws IOException;

}
