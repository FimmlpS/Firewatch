package Firewatch.power.debuff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class NoTerraPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:NoTerraPower";

    public NoTerraPower(AbstractCreature owner) {
        super(POWER_ID,owner);
        setDebuff();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
    }
}

