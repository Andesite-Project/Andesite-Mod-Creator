package info.varden.andesite.core;

public enum SilkTouchMode {
    NORMAL(0),
    SILKTOUCH(1),
    ANY(2);
    
    private final int id;
    
    private SilkTouchMode(int id) {
        this.id = id;
    }
    
    public int getID() {
        return this.id;
    }
    
    public static SilkTouchMode findById(int id) {
        for (SilkTouchMode mode : SilkTouchMode.values()) {
            if (mode.getID() == id) {
                return mode;
            }
        }
        throw new UnsupportedOperationException("Silk touch mode ID " + id + " not found!");
    }
}
