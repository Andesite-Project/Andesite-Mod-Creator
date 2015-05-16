package info.varden.andesite.action;

import info.varden.andesite.action.base.IdDataAction;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.modloader.BlockWrapper;

@ActionData(id = 4, version = 1)
public class BlockParticleGravityAction extends IdDataAction<Float> implements Action, BlockAction {

    @Override
    public void execute() {
        BlockWrapper.getFor(getID()).setParticleGravity(getData().floatValue());
    }
    
    public static BlockParticleGravityAction create(String blockId, float gravityLevel) {
        BlockParticleGravityAction a = new BlockParticleGravityAction();
        a.setBlockId(blockId);
        a.setParticleGravity(gravityLevel);
        return a;
    }
    
    public void setBlockId(String id) {
        setID(id);
    }
    
    @Override
    public String getBlockId() {
        return getID();
    }
    
    public void setParticleGravity(float gravityLevel) {
        setData(gravityLevel);
    }
    
    public float getParticleGravity() {
        return getData();
    }

    @Override
    public Class<Float> getDataClass() {
        return Float.class;
    }

}
