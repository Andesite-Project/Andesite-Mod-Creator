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

@ActionData(id = 3, version = 1)
public class BlockHardnessAction extends DataStreamActionWrapper implements Action, BlockAction {
    
    private String blockId;
    private float resistance;
    private float hardness;

    @Override
    public void execute() {
        BlockWrapper.getFor(blockId).setHardness(hardness).setResistance(resistance);
    }
    
    public static BlockHardnessAction create(String blockId, float hardness, float resistance) {
        BlockHardnessAction a = new BlockHardnessAction();
        a.setBlockId(blockId);
        a.setHardness(hardness);
        a.setResistance(resistance);
        return a;
    }
    
    public void setBlockId(String id) {
        this.blockId = id;
    }
    
    @Override
    public String getBlockId() {
        return this.blockId;
    }
    
    public void setHardness(float hardness) {
        this.hardness = hardness;
    }
    
    public float getHardness() {
        return this.hardness;
    }
    
    public void setResistance(float resistance) {
        this.resistance = resistance;
    }
    
    public float getResistance() {
        return this.resistance;
    }

    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.blockId = AndesiteIO.readString(input);
        this.hardness = input.readFloat();
        this.resistance = input.readFloat();
        return this;
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.blockId, output);
        output.writeFloat(this.hardness);
        output.writeFloat(this.resistance);
    }

}
