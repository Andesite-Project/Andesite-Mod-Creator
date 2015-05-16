package info.varden.andesite.core;

import info.varden.andesite.modloader.PlayerWrapper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerRequirements {
    private final ArrayList<PlayerCondition> conditions = new ArrayList<PlayerCondition>();
    
    public boolean satisfiedBy(EntityPlayer player) {
        PlayerWrapper pw = PlayerWrapper.getFor(player);
        for (PlayerCondition condition : this.conditions) {
            if (!condition.meetsRequirements(pw)) {
                return false;
            }
        }
        return true;
    }
    
    public PlayerRequirements require(PlayerCondition condition) {
        this.conditions.add(condition);
        return this;
    }
    
    public PlayerRequirements removeRequirement(PlayerCondition condition) {
        this.conditions.remove(condition);
        return this;
    }
    
    public int getRequirementCount() {
        return this.conditions.size();
    }
    
    public List<PlayerCondition> getRequirements() {
        return this.conditions;
    }
}
