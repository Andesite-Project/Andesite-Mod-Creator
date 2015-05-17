package info.varden.andesite.action;

import info.varden.andesite.action.base.IdDataAction;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.modloader.BlockWrapper;

/**
 * Action that sets a block's slipperiness.
 * @author Marius
 */
@ActionData(id = 2, version = 1)
public class BlockSlipperinessAction extends IdDataAction<Float> implements Action, BlockAction {

    /**
     * Executes the action.
     */
    @Override
    public void execute() {
        BlockWrapper.getFor(getID()).setSlipperiness(getData().floatValue());
    }
    
    /**
     * Creates an instance of BlockSlipperinessAction.
     * @param blockId The block ID this action should apply to
     * @param slipperiness The desired slipperiness of the block
     * @return An instance of BlockSlipperinessAction
     */
    public static BlockSlipperinessAction create(String blockId, float slipperiness) {
        BlockSlipperinessAction a = new BlockSlipperinessAction();
        a.setBlockId(blockId);
        a.setSlipperiness(slipperiness);
        return a;
    }
   
    /**
     * Sets the block ID this action should apply to.
     * @param id The block ID to set
     */
    public void setBlockId(String id) {
        setID(id);
    }
    
    /**
     * Gets the block ID this action should apply to.
     * @return The block ID
     */
    @Override
    public String getBlockId() {
        return getID();
    }
    
    /**
     * Sets the slipperiness of the block
     * @param slipperiness The slipperiness to set
     */
    public void setSlipperiness(float slipperiness) {
        setData(slipperiness);
    }
    
    /**
     * Gets the slipperiness of the block
     * @return The slipperiness
     */
    public float getSlipperiness() {
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
