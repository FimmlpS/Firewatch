package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class LightAndShadowPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:LightAndShadowPower";

    public LightAndShadowPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

}

