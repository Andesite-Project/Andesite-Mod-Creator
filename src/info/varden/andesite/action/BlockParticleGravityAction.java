package info.varden.andesite.action;

import info.varden.andesite.action.base.IdDataAction;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.modloader.BlockWrapper;

/**
 * Action that decides the particle gravity of a block.
 * @author Marius
 */
@ActionData(id = 4, version = 1)
public class BlockParticleGravityAction extends IdDataAction<Float> implements Action, BlockAction {

    /**
     * Executes this action.
     */
    @Override
    public void execute() {
        BlockWrapper.getFor(getID()).setParticleGravity(getData().floatValue());
    }
    
    /**
     * Creates an instance of BlockParticleGravityAction.
     * @param blockId The block ID this action should apply to.
     * @param gravityLevel The gravitational force in meters per square seconds.
     * @return An instance of BlockParticleGravityAction
     */
    public static BlockParticleGravityAction create(String blockId, float gravityLevel) {
        BlockParticleGravityAction a = new BlockParticleGravityAction();
        a.setBlockId(blockId);
        a.setParticleGravity(gravityLevel);
        return a;
    }
    
    /**
     * Sets the block ID this action applies to.
     * @param id The block ID to set
     */
    public void setBlockId(String id) {
        setID(id);
    }
    
    /**
     * Gets the block ID this applies to.
     * @return The block ID
     */
    @Override
    public String getBlockId() {
        return getID();
    }
    
    /**
     * Sets the gravitational force of the block's particles.
     * @param gravityLevel The gravitational force to set in meters per square seconds
     */
    public void setParticleGravity(float gravityLevel) {
        setData(gravityLevel);
    }
    
    /**
     * Gets the gravitational force of the block's particles.
     * @return The gravitational force in meters per square seconds
     */
    public float getParticleGravity() {
        return getData();
    }

    /**
     * Returns the underlying data type for this action.
     * @return The data type
     */
    @Override
    public Class<Float> getDataClass() {
        return Float.class;
    }

}
