package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class PreparePower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:PreparePower";

    AbstractCard upgraded;

    public PreparePower(AbstractCreature owner, AbstractCard c, int amount) {
        super(POWER_ID,owner,amount);
        setOffsetID();
        upgraded = c.makeStatEquivalentCopy();
        upgraded.upgrade();
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(upgraded==null)
            return;
        description = powerStrings.DESCRIPTIONS[0] + amount + powerStrings.DESCRIPTIONS[1] + upgraded.name + powerStrings.DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() {
        if(amount>1){
            addToBot(new ReducePowerAction(owner, owner, this, 1));
        }
        else {
            this.flash();
            addToBot(new MakeTempCardInHandAction(upgraded));
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}

