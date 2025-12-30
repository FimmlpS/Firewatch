package Firewatch.power.buff;

import Firewatch.action.FireNowAction;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AssassinNextPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:AssassinNextPower";

    public AssassinNextPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(info.type == DamageInfo.DamageType.NORMAL && target instanceof AbstractMonster && ((AbstractMonster) target).getIntentBaseDmg() <=0){
            this.flashWithoutSound();
            addToBot(new FireNowAction(amount,false));
        }
    }
}


