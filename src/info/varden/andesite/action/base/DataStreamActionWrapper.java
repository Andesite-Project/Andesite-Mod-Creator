package info.varden.andesite.action.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import info.varden.andesite.core.Action;
import info.varden.andesite.io.Serializable;

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
