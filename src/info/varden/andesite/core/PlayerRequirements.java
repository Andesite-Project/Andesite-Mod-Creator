/*
 * The MIT License
 *
 * Copyright 2015 Marius.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
