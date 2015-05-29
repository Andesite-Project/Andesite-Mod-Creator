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
package info.varden.andesite.playercondition;

import info.varden.andesite.core.PlayerCondition;
import info.varden.andesite.core.PlayerConditionData;
import info.varden.andesite.modloader.PlayerWrapper;
import info.varden.andesite.playercondition.base.SingleFieldCondition;

@PlayerConditionData(id = 0, version = 1)
public class PlayerAbsorbtionCondition extends SingleFieldCondition<Float> implements PlayerCondition {

    @Override
    public Class<Float> getDataClass() {
        return Float.class;
    }

    @Override
    protected Float getFieldForComparison(PlayerWrapper player) {
        return player.getAbsorptionAmount();
    }

}
