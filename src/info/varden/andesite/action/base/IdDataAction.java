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

    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.id = AndesiteIO.readString(input);
        if (this.data instanceof Float) {
        	this.data = (T) Float.valueOf(input.readFloat());
        } else if (this.data instanceof Integer) {
        	this.data = (T) Integer.valueOf(input.readInt());
        } else if (this.data instanceof Long) {
        	this.data = (T) Long.valueOf(input.readLong());
        } else if (this.data instanceof String) {
        	this.data = (T) AndesiteIO.readString(input);
        } else {
        	throw new IOException("Type " + this.data.getClass().getName() + " is not supported by IdDataAction");
        }
		return this;
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.id, output);
        if (this.data instanceof Float) {
        	output.writeFloat(((Float) this.data).floatValue());
        } else if (this.data instanceof Integer) {
        	output.writeInt(((Integer) this.data).intValue());
        } else if (this.data instanceof Long) {
        	output.writeLong(((Long) this.data).longValue());
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
