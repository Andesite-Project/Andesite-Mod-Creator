/*
 * The MIT License
 *
 * Copyright 2015 Marius.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package info.varden.andesite.playercondition.base;

import info.varden.andesite.core.CompareMode;
import info.varden.andesite.core.PlayerCondition;
import info.varden.andesite.core.Utils;
import info.varden.andesite.modloader.PlayerWrapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
