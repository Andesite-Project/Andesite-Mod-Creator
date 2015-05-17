package info.varden.andesite.core;

/**
 * Enum which signifies what broke a block.
 * @author Marius
 */
public enum BlockBreakSource {
    /**
     * Player broke the block.
     */
    PLAYER(0),
    /**
     * Non-player broke the block.
     */
    OTHER(1),
    /**
     * Anything broke the block.
     */
    ANY(2);
    
    /**
     * The save ID of the BlockBreakSource.
     */
    private final int id;
    
    /**
     * Initializes the BlockBreakSource.
     * @param id The ID of the source
     */
    private BlockBreakSource(int id) {
        this.id = id;
    }
    
    /**
     * Gets the save ID of the BlockBreakSource.
     * @return The ID of the source
     */
    public int getID() {
        return this.id;
    }
    
    /**
     * Finds a BlockBreakSource by its save ID.
     * @param id The ID of the source to look up
     * @return A BlockBreakSource
     */
    public static BlockBreakSource findById(int id) {
        for (BlockBreakSource mode : BlockBreakSource.values()) {
            if (mode.getID() == id) {
                return mode;
            }
        }
        throw new UnsupportedOperationException("Block break source ID " + id + " not found!");
    }
}
