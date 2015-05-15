package info.varden.andesite.action.base;

import info.varden.andesite.core.Action;
import info.varden.andesite.io.AndesiteIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public abstract class IdDataAction<T> extends DataStreamActionWrapper implements Action {
    
    private String id;
    private T data;
    
    public abstract Class<T> getDataClass();

    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.id = AndesiteIO.readString(input);
        if (getDataClass() == Float.class) {
            this.data = (T) Float.valueOf(input.readFloat());
        } else if (getDataClass() == Integer.class) {
            this.data = (T) Integer.valueOf(input.readInt());
        } else if (getDataClass() == Long.class) {
            this.data = (T) Long.valueOf(input.readLong());
        } else if (getDataClass() == String.class) {
            this.data = (T) AndesiteIO.readString(input);
        } else {
            // This turns into an InvocationTargetException when reflected in AndesiteIO.
            throw new IOException("Type " + this.data.getClass().getName() + " is not supported by IdDataAction");
        }
        return this;
    }
    
    @Override
    public Action parse(byte[] data) {
        return super.parse(data);
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.id, output);
        if (this.data instanceof Float) {
            output.writeFloat(((Float) this.data));
        } else if (this.data instanceof Integer) {
            output.writeInt(((Integer) this.data));
        } else if (this.data instanceof Long) {
            output.writeLong(((Long) this.data));
        } else if (this.data instanceof String) {
            AndesiteIO.writeString((String) this.data, output);
        } else {
            throw new IOException("Type " + this.data.getClass().getName() + " is not supported by IdDataAction");
        }
    }
    
    protected T getData() {
        return this.data;
    }
    
    protected String getID() {
        return this.id;
    }
    
    protected void setData(T data) {
        this.data = data;
    }
    
    protected void setID(String id) {
        this.id = id;
    }
    
}
