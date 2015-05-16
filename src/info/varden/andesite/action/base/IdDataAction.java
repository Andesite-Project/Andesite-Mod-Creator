package info.varden.andesite.action.base;

import info.varden.andesite.core.Action;
import info.varden.andesite.core.Utils;
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
        Utils.readGenericFromInput(input, getDataClass());
        return this;
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.id, output);
        Utils.writeGenericToOutput(output, this.data);
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
