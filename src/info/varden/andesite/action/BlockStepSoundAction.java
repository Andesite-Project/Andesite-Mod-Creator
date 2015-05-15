package info.varden.andesite.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import info.varden.andesite.action.base.DataStreamActionWrapper;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.io.AndesiteIO;
import info.varden.andesite.modloader.BlockWrapper;

@ActionData(id = 1, version = 1)
public class BlockStepSoundAction extends DataStreamActionWrapper implements Action, BlockAction {
    
    private String blockId;
    private String soundName;
    private String breakSound;
    private float volume;
    private float freq;

    @Override
    public void execute() {
        BlockWrapper b = BlockWrapper.getFor(blockId);
        b.setStepSound(new Block.SoundType(this.soundName, this.volume, this.freq) {
            @Override
            public String getBreakSound() {
                return "dig." + breakSound;
            }
        });
    }

    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.blockId = AndesiteIO.readString(input);
        this.soundName = AndesiteIO.readString(input);
        this.breakSound = AndesiteIO.readString(input);
        this.volume = input.readFloat();
        this.freq = input.readFloat();
        return this;
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.blockId, output);
        AndesiteIO.writeString(this.soundName, output);
        AndesiteIO.writeString(this.breakSound, output);
        output.writeFloat(this.volume);
        output.writeFloat(this.freq);
    }
    
    public static BlockStepSoundAction create(String blockId, String soundName, float volume, float freq) {
        return create(blockId, soundName, soundName, volume, freq);
    }
    
    public static BlockStepSoundAction create(String blockId, String soundName, String breakSound, float volume, float freq) {
        BlockStepSoundAction a = new BlockStepSoundAction();
        a.setBlockId(blockId);
        a.setSoundName(soundName);
        a.setBreakSound(breakSound);
        a.setVolume(volume);
        a.setFrequency(freq);
        return a;
    }
    
    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }
    
    @Override
    public String getBlockId() {
        return this.blockId;
    }
    
    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }
    
    public String getSoundName() {
        return this.soundName;
    }
    
    public void setBreakSound(String breakSound) {
        this.breakSound = breakSound;
    }
    
    public String getBreakSound() {
        return this.breakSound;
    }
    
    public void setVolume(float volume) {
        this.volume = volume;
    }
    
    public float getVolume() {
        return this.volume;
    }
    
    public void setFrequency(float freq) {
        this.freq = freq;
    }
    
    public float getFrequency() {
        return this.freq;
    }

}
