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
