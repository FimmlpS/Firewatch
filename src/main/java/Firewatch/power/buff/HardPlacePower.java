package Firewatch.power.buff;

import Firewatch.patch.AmbushPatch;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class HardPlacePower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:HardPlacePower";

    public HardPlacePower(AbstractCreature owner) {
        super(POWER_ID,owner, AmbushPatch.cardEnterCount);
        setAmountDescription();
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if(this.amount != AmbushPatch.cardEnterCount){
            this.fontScale = 4.0F;
            this.amount = AmbushPatch.cardEnterCount;
            updateDescription();
        }
    }

    @Override
    public void atStartOfTurn() {
        if(amount>0){
            this.flash();
            addToBot(new GainBlockAction(owner,amount));
        }
    }
}

