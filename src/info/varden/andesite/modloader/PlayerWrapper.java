package info.varden.andesite.modloader;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Wrapper for EntityPlayer.
 * @author Marius
 */
public class PlayerWrapper {
    
	/**
	 * The wrapped player.
	 */
    private final EntityPlayer player;
    
    /**
     * Wraps a player entity and creates a PlayerWrapper.
     * @param player The player to wrap.
     */
    private PlayerWrapper(EntityPlayer player) {
        this.player = player;
    }
    
    /**
     * Creates a PlayerWrapper for the given player.
     * @param player Player entity to wrap.
     * @return A PlayerWrapper instance.
     */
    public static PlayerWrapper getFor(EntityPlayer player) {
        return new PlayerWrapper(player);
    }
    
    /**
     * Gets the absorption amount of the player.
     * @return Absorption amount.
     */
    public float getAbsorptionAmount() {
        return this.player.getAbsorptionAmount();
    }
    
    /**
     * Sets the absorption amount for the player.
     * @param amount The absorption amount to set.
     * @return The PlayerWrapper instance.
     */
    public PlayerWrapper setAbsorptionAmount(float amount) {
        this.player.setAbsorptionAmount(amount);
        return this;
    }
}
