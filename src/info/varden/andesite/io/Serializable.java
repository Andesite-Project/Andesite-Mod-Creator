package info.varden.andesite.io;

/**
 * Interface which indicates a class can be read from and written to raw data.
 * @author Marius
 */
public interface Serializable {
    /**
     * Creates a Serializable from the given raw data.
     * @param data The raw data to read from
     * @return A Serializable instance
     */
    public Serializable parse(byte[] data);
    /**
     * Writes the Serializable to a raw data array.
     * @return The resulting raw data
     */
    public byte[] toData();
}
