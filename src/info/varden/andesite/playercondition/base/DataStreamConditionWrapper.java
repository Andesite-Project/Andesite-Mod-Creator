package info.varden.andesite.playercondition.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import info.varden.andesite.core.Action;
import info.varden.andesite.core.PlayerCondition;

public abstract class DataStreamConditionWrapper implements PlayerCondition {

    @Override
    public PlayerCondition parse(byte[] aeData) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(aeData));
        try {
            PlayerCondition pc = parse(dis);
            dis.close();
            return pc;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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
    
    public abstract PlayerCondition parse(DataInputStream input) throws IOException;
    public abstract void write(DataOutputStream output) throws IOException;

}
