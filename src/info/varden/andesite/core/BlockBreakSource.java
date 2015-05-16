package info.varden.andesite.core;

public enum BlockBreakSource {
    PLAYER(0),
    OTHER(1),
    ANY(2);
    
    private final int id;
    
    private BlockBreakSource(int id) {
        this.id = id;
    }
    
    public int getID() {
        return this.id;
    }
    
    public static BlockBreakSource findById(int id) {
        for (BlockBreakSource mode : BlockBreakSource.values()) {
            if (mode.getID() == id) {
                return mode;
            }
        }
        throw new UnsupportedOperationException("Block break source ID " + id + " not found!");
    }
}
