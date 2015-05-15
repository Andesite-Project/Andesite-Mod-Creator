package info.varden.andesite.action;

import info.varden.andesite.action.base.IdDataAction;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.modloader.BlockWrapper;

@ActionData(id = 5, version = 1)
public class BlockSlipperinessAction extends IdDataAction<Float> implements Action, BlockAction {

    @Override
    public void execute() {
        BlockWrapper.getFor(getID()).setSlipperiness(getData().floatValue());
    }
    
    public static BlockSlipperinessAction create(String blockId, float slipperiness) {
        BlockSlipperinessAction a = new BlockSlipperinessAction();
        a.setBlockId(blockId);
        a.setSlipperiness(slipperiness);
        return a;
    }
    
    public void setBlockId(String id) {
        setID(id);
    }
    
    @Override
    public String getBlockId() {
        return getID();
    }
    
    public void setSlipperiness(float slipperiness) {
        setData(slipperiness);
    }
    
    public float getSlipperiness() {
        return getData();
    }

    @Override
    public Class<Float> getDataClass() {
        return Float.class;
    }

}
