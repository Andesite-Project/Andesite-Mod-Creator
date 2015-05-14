package info.varden.andesite.action.base;

import info.varden.andesite.core.Action;
import info.varden.andesite.io.AndesiteIO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class IdFloatDataAction implements Action {

    @Override
    public Action parse(byte[] aeData) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(aeData));
        String id;
        try {
            id = AndesiteIO.readString(dis);
            float data = dis.readFloat();
            dis.close();
            return parse(id, data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] toData() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            AndesiteIO.writeString(getID(), dos);
            dos.writeFloat(getData());
            dos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public abstract Action parse(String id, float data);
    public abstract float getData();
    public abstract String getID();
    
}
