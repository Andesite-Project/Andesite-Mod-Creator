package info.varden.andesite.modloader;

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

}
