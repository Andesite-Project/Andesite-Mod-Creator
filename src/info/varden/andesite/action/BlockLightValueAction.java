package info.varden.andesite.action;

import info.varden.andesite.action.base.IdDataAction;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.modloader.BlockWrapper;

@ActionData(id = 0, version = 1)
public class BlockLightValueAction extends IdDataAction<Float> implements Action {

    @Override
    public void execute() {
        BlockWrapper b = BlockWrapper.getFor(getID());
        b.setLightLevel(getData().floatValue());
    }
    
    public static BlockLightValueAction create(String blockId, float lightValue) {
        BlockLightValueAction a = new BlockLightValueAction();
        a.setBlockId(blockId);
        a.setLightValue(lightValue);
        return a;
    }
    
    public void setBlockId(String id) {
        setID(id);
    }
    
    public String getBlockId() {
        return getID();
    }
    
    public void setLightValue(float lightValue) {
        setData(Float.valueOf(lightValue));
    }
    
    public float getLightValue() {
        return getData().floatValue();
    }

}
