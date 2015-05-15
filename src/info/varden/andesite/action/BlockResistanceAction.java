package info.varden.andesite.action;

import info.varden.andesite.action.base.IdDataAction;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.modloader.BlockWrapper;

@ActionData(id = 2, version = 1)
public class BlockResistanceAction extends IdDataAction<Float> implements Action, BlockAction {

    @Override
    public void execute() {
        BlockWrapper.getFor(getID()).setResistance(getData().floatValue());
    }
    
    public static BlockResistanceAction create(String blockId, float resistance) {
        BlockResistanceAction a = new BlockResistanceAction();
        a.setBlockId(blockId);
        a.setResistance(resistance);
        return a;
    }
    
    public void setBlockId(String id) {
        setID(id);
    }
    
    @Override
    public String getBlockId() {
        return getID();
    }
    
    public void setResistance(float resistance) {
        setData(resistance);
    }
    
    public float getResistance() {
        return getData();
    }

    @Override
    public Class<Float> getDataClass() {
        return Float.class;
    }

}
