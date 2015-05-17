package info.varden.andesite.action;

import info.varden.andesite.action.base.IdDataAction;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.modloader.BlockWrapper;

/**
 * Action that sets the brightness of a block.
 * @author Marius
 */
@ActionData(id = 0, version = 1)
public class BlockLightValueAction extends IdDataAction<Float> implements Action, BlockAction {

    /**
     * Executes the action.
     */
    @Override
    public void execute() {
        BlockWrapper.getFor(getID()).setLightLevel(getData().floatValue());
    }
    
    /**
     * Creates an instance of BlockLightValueAction.
     * @param blockId The block ID this action applies to
     * @param lightValue The desired light value of the block
     * @return An instance of BlockLightValueAction
     */
    public static BlockLightValueAction create(String blockId, float lightValue) {
        BlockLightValueAction a = new BlockLightValueAction();
        a.setBlockId(blockId);
        a.setLightValue(lightValue);
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
     * Gets the block ID this action applies to.
     * @return The block ID
     */
    @Override
    public String getBlockId() {
        return getID();
    }
    
    /**
     * Sets the desired light value of the associated block.
     * @param lightValue The light value to set
     */
    public void setLightValue(float lightValue) {
        setData(lightValue);
    }
    
    /**
     * Gets the desired light value of the associated block.
     * @return The light value
     */
    public float getLightValue() {
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
