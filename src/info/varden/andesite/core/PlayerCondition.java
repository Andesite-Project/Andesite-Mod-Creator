package info.varden.andesite.core;

import info.varden.andesite.io.Serializable;
import info.varden.andesite.modloader.PlayerWrapper;

/**
 * Base interface for all player conditions.
 * @author Marius
 */
public interface PlayerCondition extends Serializable {
    /**
     * Constructs a PlayerCondition from raw data.
     * @param data The raw PlayerCondition data
     * @return A PlayerCondition instance
     */
    @Override
    public PlayerCondition parse(byte[] data);
    /**
     * Checks whether the given player meets the condition.
     * @param player The player to check
     * @return True if condition is satisfied, false otherwise
     */
    public boolean meetsRequirements(PlayerWrapper player);
    /**
     * Writes the PlayerCondition to a raw data array.
     * @return The raw data which represents the PlayerCondition
     */
    @Override
    public byte[] toData();
}
