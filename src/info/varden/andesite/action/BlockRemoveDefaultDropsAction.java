package info.varden.andesite.action;

import info.varden.andesite.action.base.DataStreamActionWrapper;
import info.varden.andesite.core.Action;
import info.varden.andesite.core.ActionData;
import info.varden.andesite.core.BlockAction;
import info.varden.andesite.io.AndesiteIO;
import info.varden.andesite.modloader.BlockWrapper;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@ActionData(id = 5, version = 1)
public class BlockRemoveDefaultDropsAction extends DataStreamActionWrapper implements Action, BlockAction {
    
    private String blockId;

    @Override
    public void execute() {
        BlockWrapper.getFor(blockId).removeDefaultDrops();
    }
    
    public static BlockRemoveDefaultDropsAction create(String blockId) {
        BlockRemoveDefaultDropsAction a = new BlockRemoveDefaultDropsAction();
        a.setBlockId(blockId);
        return a;
    }
    
    public void setBlockId(String id) {
        this.blockId = id;
    }
    
    @Override
    public String getBlockId() {
        return this.blockId;
    }

    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.blockId = AndesiteIO.readString(input);
        return this;
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.blockId, output);
    }

}
