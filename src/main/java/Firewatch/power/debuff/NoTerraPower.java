package Firewatch.power.debuff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class NoTerraPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:NoTerraPower";

    public NoTerraPower(AbstractCreature owner,int amount) {
        super(POWER_ID,owner,amount);
        setDebuff();
        setAmountDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(amount>1){
            addToBot(new ReducePowerAction(owner, owner, POWER_ID, 1));
        }
        else
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
    }
}

