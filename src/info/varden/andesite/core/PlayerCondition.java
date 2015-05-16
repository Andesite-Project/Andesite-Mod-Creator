package info.varden.andesite.core;

import info.varden.andesite.io.Serializable;
import info.varden.andesite.modloader.PlayerWrapper;
import net.minecraft.entity.player.EntityPlayer;

public interface PlayerCondition extends Serializable {
    @Override
    public PlayerCondition parse(byte[] data);
    public boolean meetsRequirements(PlayerWrapper player);
    @Override
    public byte[] toData();
}
