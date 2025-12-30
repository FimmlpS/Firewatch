package Firewatch.power.buff;

import Firewatch.action.DiscardToHandAction;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class WindAcrossRiverPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:WindAcrossRiverPower";

    public WindAcrossRiverPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

    @Override
    public void onSpecificTrigger() {
        this.flashWithoutSound();
        addToBot(new DamageRandomEnemyAction(new DamageInfo(owner,amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DiscardToHandAction(1,false));
    }
}


