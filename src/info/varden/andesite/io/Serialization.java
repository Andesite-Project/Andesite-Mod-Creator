package info.varden.andesite.io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class Serialization {
    public static void writeToOutput(Serializable obj, DataOutputStream output) throws IOException {
        AndesiteIO.writeByteArray(obj.toData(), output);
    }
    
    public static <T extends Serializable> T readFromInput(DataInputStream input, Class<T> clazz) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, InstantiationException, IOException {
        byte[] blockData = AndesiteIO.readByteArray(input);
        Constructor constructor = clazz.getConstructors()[0];
        Object action = constructor.newInstance();
        Method parse = clazz.getMethod("parse", byte[].class);
        T initObj = (T) parse.invoke((T) action, new Object[] {blockData});
        return (T) initObj;
    }
}
