package info.varden.andesite.action.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import info.varden.andesite.core.Action;

public abstract class DataStreamActionWrapper implements Action {

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
	
	public abstract Action parse(DataInputStream input) throws IOException;
	public abstract void write(DataOutputStream output) throws IOException;

}
