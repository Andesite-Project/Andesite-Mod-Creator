package info.varden.andesite.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Class for serializing Serializable classes.
 * @author Marius
 */
public final class Serialization {
    /**
     * Writes the given serializable object to the given output stream.
     * @param obj The serializable object to write
     * @param output The output stream to write to
     * @throws IOException I/O operation fails
     */
    public static void writeToOutput(Serializable obj, DataOutputStream output) throws IOException {
        AndesiteIO.writeByteArray(obj.toData(), output);
    }
    
    /**
     * Reads a serializable object of type T from the given input stream.
     * @param <T> The serializable type
     * @param input The input stream to read from
     * @param clazz The serializable type class
     * @return A Serializable object of type T
     * @throws IllegalAccessException The action class corresponding to an action stored in the project stream can not be accessed
     * @throws IllegalArgumentException The constructor for an action is invalid
     * @throws InvocationTargetException The constructor for an action in the project format stream fails to invoke
     * @throws SecurityException An action cannot be read
     * @throws NoSuchMethodException An action does not support instantiation
     * @throws IOException I/O operation fails
     * @throws InstantiationException Can not instantiate an action contained in the project stream
     */
    public static <T extends Serializable> T readFromInput(DataInputStream input, Class<T> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, IOException {
        byte[] blockData = AndesiteIO.readByteArray(input);
        Constructor constructor = clazz.getConstructors()[0];
        Object action = constructor.newInstance();
        Method parse = clazz.getMethod("parse", byte[].class);
        T initObj = (T) parse.invoke((T) action, new Object[] {blockData});
        return (T) initObj;
    }
}
