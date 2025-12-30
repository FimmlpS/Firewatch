package Firewatch.power.buff;

import Firewatch.action.FireNowAction;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class GoodEquipmentPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:GoodEquipmentPower";

    public GoodEquipmentPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(info.type == DamageInfo.DamageType.NORMAL){
            this.flashWithoutSound();
            addToBot(new FireNowAction(amount,false));
        }
    }
}

