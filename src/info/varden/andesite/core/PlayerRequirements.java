package info.varden.andesite.core;

import info.varden.andesite.modloader.PlayerWrapper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

/**
 * List of requirements a player has to satisfy.
 * @author Marius
 */
public class PlayerRequirements {
    /**
     * The list of requirements.
     */
    private final ArrayList<PlayerCondition> conditions = new ArrayList<PlayerCondition>();
    
    /**
     * Checks whether or not the given player satisfies the requirements.
     * @param player The player to check
     * @return True if satisfied, false otherwise
     */
    public boolean satisfiedBy(EntityPlayer player) {
        PlayerWrapper pw = PlayerWrapper.getFor(player);
        for (PlayerCondition condition : this.conditions) {
            if (!condition.meetsRequirements(pw)) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Adds a requirement to the list of requirements.
     * @param condition The requirement to add
     * @return The PlayerRequirements instance
     */
    public PlayerRequirements require(PlayerCondition condition) {
        this.conditions.add(condition);
        return this;
    }
    
    /**
     * Removes a requirement from the list of requirements.
     * @param condition The requirement to remove
     * @return The PlayerRequirements instance
     */
    public PlayerRequirements removeRequirement(PlayerCondition condition) {
        this.conditions.remove(condition);
        return this;
    }
    
    /**
     * Gets the number of requirements.
     * @return Number of requirements
     */
    public int getRequirementCount() {
        return this.conditions.size();
    }
    
    /**
     * Gets a list of requirements.
     * @return The underlying list of requirements
     */
    public List<PlayerCondition> getRequirements() {
        return this.conditions;
    }
}
