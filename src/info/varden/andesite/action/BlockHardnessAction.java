package info.varden.andesite.action;

import info.varden.andesite.action.base.IdDataAction;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.modloader.BlockWrapper;

@ActionData(id = 3, version = 1)
public class BlockHardnessAction extends IdDataAction<Float> implements Action, BlockAction {

    @Override
    public void execute() {
        BlockWrapper.getFor(getID()).setHardness(getData().floatValue());
    }
    
    public static BlockHardnessAction create(String blockId, float hardness) {
        BlockHardnessAction a = new BlockHardnessAction();
        a.setBlockId(blockId);
        a.setHardness(hardness);
        return a;
    }
    
    public void setBlockId(String id) {
        setID(id);
    }
    
    @Override
    public String getBlockId() {
        return getID();
    }
    
    public void setHardness(float hardness) {
        setData(hardness);
    }
    
    public float getHardness() {
        return getData();
    }

    @Override
    public Class<Float> getDataClass() {
        return Float.class;
    }

}
