package info.varden.andesite.io;

public interface Serializable {
    public Serializable parse(byte[] data);
    public byte[] toData();
}
