package info.varden.andesite.playercondition.base;

import info.varden.andesite.core.Action;
import info.varden.andesite.core.CompareMode;
import info.varden.andesite.core.PlayerCondition;
import info.varden.andesite.core.Utils;
import info.varden.andesite.io.AndesiteIO;
import info.varden.andesite.modloader.PlayerWrapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public abstract class SingleFieldCondition<T> extends DataStreamConditionWrapper implements PlayerCondition {
    
    private T data;
    private CompareMode cm;
    
    public abstract Class<T> getDataClass();
    protected abstract T getFieldForComparison(PlayerWrapper player);

    @Override
    public PlayerCondition parse(DataInputStream input) throws IOException {
        this.cm = CompareMode.findById(input.readInt());
        this.data = Utils.readGenericFromInput(input, getDataClass());
        return this;
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        output.writeInt(this.cm.getID());
        Utils.writeGenericToOutput(output, this.data);
    }
    
    @Override
    public boolean meetsRequirements(PlayerWrapper player) {
        return Utils.comparisonValid(getFieldForComparison(player), this.data, this.cm, getDataClass());
    }
    
    protected T getData() {
        return this.data;
    }
    
    protected CompareMode getCompareMode() {
        return this.cm;
    }
    
    protected void setData(T data) {
        this.data = data;
    }
    
    protected void setCompareMode(CompareMode cm) {
        this.cm = cm;
    }
    
}
