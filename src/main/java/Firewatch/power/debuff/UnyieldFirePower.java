package Firewatch.power.debuff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class UnyieldFirePower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:UnyieldFirePower";

    public UnyieldFirePower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setDebuff();
        setAmountDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        addToBot(new DamageAction(owner,new DamageInfo(owner,amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new RemoveSpecificPowerAction(owner,owner,this));
    }
}

