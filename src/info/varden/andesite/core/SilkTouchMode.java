package info.varden.andesite.core;

/**
 * Enum signifying a silk touch status.
 * @author Marius
 */
public enum SilkTouchMode {
    /**
     * No silk touch.
     */
    NORMAL(0),
    /**
     * Silk touch applies.
     */
    SILKTOUCH(1),
    /**
     * Any silk touch mode.
     */
    ANY(2);
    
    /**
     * The save ID of this SilkTouchMode.
     */
    private final int id;
    
    /**
     * Initializes a SilkTouchMode.
     * @param id The save ID
     */
    private SilkTouchMode(int id) {
        this.id = id;
    }
    
    /**
     * Gets the save ID of the SilkTouchMode.
     * @return The save ID
     */
    public int getID() {
        return this.id;
    }
    
    /**
     * Finds a SilkTouchMode by its save ID.
     * @param id The save ID to look up
     * @return A SilkTouchMode
     */
    public static SilkTouchMode findById(int id) {
        for (SilkTouchMode mode : SilkTouchMode.values()) {
            if (mode.getID() == id) {
                return mode;
            }
        }
        throw new UnsupportedOperationException("Silk touch mode ID " + id + " not found!");
    }
}
