package info.varden.andesite.modloader;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerWrapper {
    
    private final EntityPlayer player;
    
    private PlayerWrapper(EntityPlayer player) {
        this.player = player;
    }
    
    public static PlayerWrapper getFor(EntityPlayer player) {
        return new PlayerWrapper(player);
    }
    
    public float getAbsorptionAmount() {
        return this.player.getAbsorptionAmount();
    }
    
    public PlayerWrapper setAbsorptionAmount(float amount) {
        this.player.setAbsorptionAmount(amount);
        return this;
    }
}
