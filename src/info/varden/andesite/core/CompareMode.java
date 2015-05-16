package info.varden.andesite.core;

public enum CompareMode {
    LESS_THAN(0),
    LESS_THAN_OR_EQUALS(1),
    EQUALS(2),
    GREATER_THAN_OR_EQUALS(3),
    GREATER_THAN(4),
    NOT_EQUALS(5);
    
    private final int id;
    
    private CompareMode(int id) {
        this.id = id;
    }
    
    public int getID() {
        return this.id;
    }
    
    public static CompareMode findById(int id) {
        for (CompareMode mode : CompareMode.values()) {
            if (mode.getID() == id) {
                return mode;
            }
        }
        throw new UnsupportedOperationException("Comparison mode ID " + id + " not found!");
    }
}
