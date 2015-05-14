/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.varden.andesite.core;

/**
 * Base interface for all actions.
 * @author Marius
 */
public interface Action {
    /**
     * Constructs an action from binary file data.
     * @param aeData The binary entry data
     * @return The resulting action instance
     */
    public Action parse(byte[] aeData);
    /**
     * Executes the action in game.
     */
    public void execute();
    /**
     * Converts the action into a byte array for file storage.
     * @return Raw data array representing this action
     */
    public byte[] toData();
}
