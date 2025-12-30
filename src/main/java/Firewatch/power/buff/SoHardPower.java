package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class SoHardPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:SoHardPower";

    public SoHardPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

    @Override
    public void onSpecificTrigger() {
        this.flash();
        addToBot(new GainBlockAction(owner, amount));
    }
}


