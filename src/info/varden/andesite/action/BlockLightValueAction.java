package info.varden.andesite.action;

import info.varden.andesite.action.base.IdFloatDataAction;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.modloader.BlockWrapper;

@ActionData(id = 0, version = 1)
public class BlockLightValueAction extends IdFloatDataAction implements Action {
	
	private float lightLevel;
	private String blockId;

	@Override
	public void execute() {
		BlockWrapper b = BlockWrapper.getFor(blockId);
		b.setLightLevel(lightLevel);
	}

	@Override
	public float getData() {
		return this.lightLevel;
	}

	@Override
    public Action parse(String id, float data) {
		this.blockId = id;
	    this.lightLevel = data;
	    return this;
    }

	@Override
    public String getID() {
	    return blockId;
    }

}
