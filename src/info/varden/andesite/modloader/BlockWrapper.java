package info.varden.andesite.modloader;

import info.varden.andesite.core.BlockBreakSource;
import info.varden.andesite.core.PlayerRequirements;
import info.varden.andesite.core.SilkTouchMode;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameData;

public class BlockWrapper {
    
    private final Block block;
    
    private BlockWrapper(Block base) {
        this.block = base;
    }
    
    public static BlockWrapper getFor(String name) {
        return new BlockWrapper(GameData.getBlockRegistry().getObject(name));
    }
    
    public float getLightLevel() {
        return ((float) this.block.getLightValue()) / 15F;
    }
    
    public BlockWrapper setLightLevel(float value) {
        this.block.setLightLevel(value);
        return this;
    }
    
    public Block.SoundType getStepSound() {
        return this.block.stepSound;
    }
    
    public BlockWrapper setStepSound(Block.SoundType sound) {
        this.block.setStepSound(sound);
        return this;
    }
    
    public float getResistance() {
        return this.block.getExplosionResistance(null) * 5F;
    }
    
    public BlockWrapper setResistance(float resistance) {
        this.block.setResistance(resistance / 3F);
        return this;
    }
    
    public float getHardness() {
        return this.block.getBlockHardness(null, null);
    }
    
    public BlockWrapper setHardness(float hardness) {
        this.block.setHardness(hardness);
        return this;
    }
    
    public float getSlipperiness() {
        return this.block.slipperiness;
    }
    
    public BlockWrapper setSlipperiness(float value) {
        this.block.slipperiness = value;
        return this;
    }
    
    public float getParticleGravity() {
        return this.block.blockParticleGravity;
    }
    
    public BlockWrapper setParticleGravity(float value) {
        this.block.blockParticleGravity = value;
        return this;
    }
    
    public BlockWrapper removeDefaultDrops() {
        MinecraftForge.EVENT_BUS.register(new Object() {
            @SubscribeEvent(priority = EventPriority.LOW)
            public void harvestDropsEvent(BlockEvent.HarvestDropsEvent event) {
                event.drops.clear();
                event.dropChance = 0.0F;
            }
        });
        return this;
    }
    
    public BlockWrapper setConditionalDrops(final SilkTouchMode mode, final BlockBreakSource source, final int fortuneLevel, final List<ItemStack> items, final float dropChance, final boolean overrideDrops, final PlayerRequirements conditions) {
        MinecraftForge.EVENT_BUS.register(new Object() {
            @SubscribeEvent(priority = EventPriority.LOWEST)
            public void harvestDropsEvent(BlockEvent.HarvestDropsEvent event) {
                if (
                        (mode == SilkTouchMode.ANY || (mode == SilkTouchMode.SILKTOUCH) == event.isSilkTouching) &&
                        (fortuneLevel == -1 || (event.fortuneLevel == fortuneLevel)) &&
                        (source == BlockBreakSource.ANY || (source == BlockBreakSource.OTHER) == (event.harvester == null))
                    ) {
                    if (event.harvester != null && conditions != null && !conditions.satisfiedBy(event.harvester)) {
                        return;
                    }
                    event.dropChance = dropChance;
                    if (overrideDrops) {
                        event.drops.clear();
                    }
                    event.drops.addAll(items);
                }
            }
        });
        return this;
    }

}
