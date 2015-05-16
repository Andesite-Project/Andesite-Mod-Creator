package info.varden.andesite.modloader;

import java.lang.reflect.Field;

import net.minecraft.block.Block;
import net.minecraftforge.fml.common.registry.GameData;

public class BlockWrapper {
    
    private final Block block;
    
    private BlockWrapper(Block base) {
        this.block = base;
    }
    
    public static BlockWrapper getFor(String name) {
        return new BlockWrapper(GameData.getBlockRegistry().getObject(name));
    }
    
    public BlockWrapper setLightLevel(float value) {
        this.block.setLightLevel(value);
        return this;
    }
    
    public BlockWrapper setStepSound(Block.SoundType sound) {
        this.block.setStepSound(sound);
        return this;
    }
    
    public BlockWrapper setResistance(float resistance) {
        this.block.setResistance(resistance / 3F);
        return this;
    }
    
    public BlockWrapper setHardness(float hardness) {
        this.block.setHardness(hardness);
        return this;
    }
    
    public BlockWrapper setSlipperiness(float value) {
        this.block.slipperiness = value;
        return this;
    }

}
