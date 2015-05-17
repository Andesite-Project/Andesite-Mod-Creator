package info.varden.andesite.core;

/**
 * Comparisons between two values.
 * @author Marius
 */
public enum CompareMode {
    /**
     * A less than B
     */
    LESS_THAN(0),
    /**
     * A less than or equal to B
     */
    LESS_THAN_OR_EQUALS(1),
    /**
     * A equal to B
     */
    EQUALS(2),
    /**
     * A greater than or equal to B
     */
    GREATER_THAN_OR_EQUALS(3),
    /**
     * A greater than B
     */
    GREATER_THAN(4),
    /**
     * A not equal to B
     */
    NOT_EQUALS(5);
    
    /**
     * The save ID for this CompareMode.
     */
    private final int id;
    
    /**
     * Initializes the CompareMode.
     * @param id The save ID of the CompareMode.
     */
    private CompareMode(int id) {
        this.id = id;
    }
    
    /**
     * Gets the save ID of the CompareMode.
     * @return The save ID
     */
    public int getID() {
        return this.id;
    }
    
    /**
     * Finds a CompareMode by its save ID.
     * @param id The CompareMode save ID to look up
     * @return A CompareMode
     */
    public static CompareMode findById(int id) {
        for (CompareMode mode : CompareMode.values()) {
            if (mode.getID() == id) {
                return mode;
            }
        }
        throw new UnsupportedOperationException("Comparison mode ID " + id + " not found!");
    }
}
