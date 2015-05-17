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

/**
 * Action that removes all drops from a given block.
 * @author Marius
 */
@ActionData(id = 5, version = 1)
public class BlockRemoveDefaultDropsAction extends DataStreamActionWrapper implements Action, BlockAction {
    
    /**
     * The block ID this action applies to.
     */
    private String blockId;

    /**
     * Executes the action.
     */
    @Override
    public void execute() {
        BlockWrapper.getFor(blockId).removeDefaultDrops();
    }
    
    /**
     * Creates an instance of BlockRemoveDefaultDropsAction.
     * @param blockId The block ID this action should apply to
     * @return An instance of BlockRemoveDefaultDropsAction.
     */
    public static BlockRemoveDefaultDropsAction create(String blockId) {
        BlockRemoveDefaultDropsAction a = new BlockRemoveDefaultDropsAction();
        a.setBlockId(blockId);
        return a;
    }
    
    /**
     * Sets the block ID this action applies to.
     * @param id The block ID to set
     */
    public void setBlockId(String id) {
        this.blockId = id;
    }
    
    /**
     * Gets the block ID this action applies to.
     * @return The block ID
     */
    @Override
    public String getBlockId() {
        return this.blockId;
    }

    /**
     * Creates an instance of this action from an input stream.
     * @param input The input stream to read from
     * @return An instance of BlockRemvoeDefaultDropsAction
     * @throws IOException I/O fails when reading from input
     */
    @Override
    public Action parse(DataInputStream input) throws IOException {
        this.blockId = AndesiteIO.readString(input);
        return this;
    }

    /**
     * Writes this action instance to an output stream.
     * @param output The output stream to write to
     * @throws IOException I/O fails when writing to output
     */
    @Override
    public void write(DataOutputStream output) throws IOException {
        AndesiteIO.writeString(this.blockId, output);
    }

}
