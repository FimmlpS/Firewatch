package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class MultiAngryPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:MultiAngryPower";

    boolean upgraded;

    public MultiAngryPower(AbstractCreature owner, boolean upgraded) {
        super(POWER_ID,owner);
        this.upgraded = upgraded;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        addToBot(new ApplyPowerAction(owner,owner,new MultiAngryNextPower(owner,upgraded)));
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[upgraded?1:0];
    }
}


