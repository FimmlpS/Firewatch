package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class HardToSayPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:HardToSayPower";

    boolean upgraded;

    public HardToSayPower(AbstractCreature owner, int amount, boolean upgraded) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
        setOffsetID();
        this.upgraded = upgraded;
        updateDescription();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount>0 || upgraded){
            this.flash();
            addToBot(new ApplyPowerAction(owner,owner,new EnergizedPower(owner,amount),amount));
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[upgraded?1:0] + amount + powerStrings.DESCRIPTIONS[2];
    }
}
