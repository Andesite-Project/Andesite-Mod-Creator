package info.varden.andesite.modloader;

import info.varden.andesite.modloader.util.RegistryUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameData;

public class BlockWrapper extends Block {
	
	public static BlockWrapper getFor(String name) {
		AndesiteML.info("Obtaining block wrapper for " + name);
		Block base = GameData.getBlockRegistry().getObject(name);
		if (base instanceof BlockWrapper) {
			AndesiteML.info("Wrapper already exists for " + name + ", returning existing");
			return (BlockWrapper) base;
		} else {
			AndesiteML.info("Creating new wrapper for " + name);
			BlockWrapper wrapped = new BlockWrapper(base);
			try {
	            RegistryUtils.overwriteEntry(GameData.getBlockRegistry(), name, wrapped);
	            AndesiteML.info("Overwrite successful for " + name);
            } catch (Throwable e) {
            	AndesiteML.error("Could not overwrite registry entry for " + name + " with the wrapped block!");
	            e.printStackTrace();
            }
			return new BlockWrapper(base);
		}
	}

	private BlockWrapper(Block base) {
	    this(base, base.getMaterial());
    }
	
	private BlockWrapper(Block base, Material material) {
		super(material);
		Field[] fieldsAll = Block.class.getDeclaredFields();
		for (Field field : fieldsAll) {
			if (Modifier.isStatic(field.getModifiers())) {
				continue;
			}
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			try {
	            field.set(this, field.get(base));
            } catch (IllegalArgumentException e) {
	            e.printStackTrace();
            } catch (IllegalAccessException e) {
	            e.printStackTrace();
            }
		}
	}

}
