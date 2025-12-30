package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class AmbushPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:AmbushPower";

    public AmbushPower(AbstractCreature owner) {
        super(POWER_ID,owner);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if(info.type == DamageInfo.DamageType.NORMAL) {
            return 0;
        }
        return super.onAttackedToChangeDamage(info, damageAmount);
    }

    @Override
    public void atEndOfRound() {
        addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this));
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        this.flash();
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        addToBot(new ApplyPowerAction(owner, owner, new VulnerablePower(owner, 2, !owner.isPlayer), 2));
    }
}

