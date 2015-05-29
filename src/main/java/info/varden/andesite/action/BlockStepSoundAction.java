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
package info.varden.andesite.action;

import info.varden.andesite.action.base.DataStreamActionWrapper;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.io.AndesiteIO;
import info.varden.andesite.modloader.BlockWrapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Action that sets the sounds when stepping on and breaking blocks.
 * @author Marius
 */
@ActionData(id = 1, version = 1)
public class BlockStepSoundAction extends DataStreamActionWrapper implements Action, BlockAction {
    
    /**
     * The block ID this action applies to.
     */
    private String blockId;
    /**
     * The name of the sound to play when stepped on.
     */
    private String soundName;
    /**
     * The name of the sound to play when broken.
     */
    private String breakSound;
    /**
     * The volume of the sounds.
     */
    private float volume;
    /**
     * The frequency of the sounds.
     */
    private float freq;

    /**
     * Executes the action.
     */
    @Override
    public void execute() {
        BlockWrapper.getFor(blockId).setStepSound(new net.minecraft.block.Block.SoundType(this.soundName, this.volume, this.freq) {
            @Override
            public String getBreakSound() {
                return "dig." + breakSound;
            }
        });
    }

    /**
     * Creates an instance of BlockStepSoundAction from an input stream.
     * @param input The input stream to read from
     * @return An instance of BlockStepSoundAction
     * @throws IOException I/O fails when reading from input
     */
    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.blockId = AndesiteIO.readString(input);
        this.soundName = AndesiteIO.readString(input);
        this.breakSound = AndesiteIO.readString(input);
        this.volume = input.readFloat();
        this.freq = input.readFloat();
        return this;
    }

    /**
     * Writes this action instance to an output stream.
     * @param output The output stream to write to
     * @throws IOException I/O fails when writing to output
     */
    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.blockId, output);
        AndesiteIO.writeString(this.soundName, output);
        AndesiteIO.writeString(this.breakSound, output);
        output.writeFloat(this.volume);
        output.writeFloat(this.freq);
    }
    
    /**
     * Creates an instance of BlockStepSoundAction with given parameters.
     * @param blockId The block ID this action should apply to
     * @param soundName The name of the sound to play when stepped on
     * @param volume The volume of the sounds
     * @param freq The frequency of the sounds
     * @return An instance of BlockStepSoundAction
     */
    public static BlockStepSoundAction create(String blockId, String soundName, float volume, float freq) {
        return create(blockId, soundName, soundName, volume, freq);
    }
    
    /**
     * Creates an instance of BlockStepSoundAction with given parameters.
     * @param blockId The block ID this action should apply to
     * @param soundName The name of the sound to play when stepped on
     * @param breakSound The name of the sound to play when broken
     * @param volume The volume of the sounds
     * @param freq The frequency of the sounds
     * @return An instance of BlockStepSoundAction
     */
    public static BlockStepSoundAction create(String blockId, String soundName, String breakSound, float volume, float freq) {
        BlockStepSoundAction a = new BlockStepSoundAction();
        a.setBlockId(blockId);
        a.setSoundName(soundName);
        a.setBreakSound(breakSound);
        a.setVolume(volume);
        a.setFrequency(freq);
        return a;
    }
    
    /**
     * Sets the block ID this action should apply to.
     * @param blockId The block ID to set
     */
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }
    
    /**
     * Gets the block ID this action applies to.
     * @return The block ID
     */
    @Override
    public String getBlockId() {
        return this.blockId;
    }
    
    /**
     * Sets the name of the sound played when stepped on.
     * @param soundName The name of the sound to set
     */
    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }
    
    /**
     * Gets the name of the sound played when stepped on.
     * @return The name of the sound
     */
    public String getSoundName() {
        return this.soundName;
    }
    
    /**
     * Sets the name of the sound played when broken.
     * @param breakSound The name of the sound to set
     */
    public void setBreakSound(String breakSound) {
        this.breakSound = breakSound;
    }
    
    /**
     * Gets the name of the sound played when broken.
     * @return The name of the sound
     */
    public String getBreakSound() {
        return this.breakSound;
    }
    
    /**
     * Sets the volume of the sounds.
     * @param volume The volume to set
     */
    public void setVolume(float volume) {
        this.volume = volume;
    }
    
    /**
     * Gets the volume of the sounds.
     * @return The volume to set
     */
    public float getVolume() {
        return this.volume;
    }
    
    /**
     * Sets the frequency of the sounds.
     * @param freq The frequency to set
     */
    public void setFrequency(float freq) {
        this.freq = freq;
    }
    
    /**
     * Gets the frequency of the sounds.
     * @return The frequency
     */
    public float getFrequency() {
        return this.freq;
    }

}
