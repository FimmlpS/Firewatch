package Firewatch.power.buff;

import Firewatch.helper.FirewatchHelper;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class GrowingPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:GrowingPower";

    private boolean applied = false;

    public void judgeApply(){
        if(FirewatchHelper.isWild() && !applied){
            applied = true;
            this.flash();
            addToBot(new ApplyPowerAction(owner,owner,new StrengthPower(owner,amount),amount));
        }
        else if(!FirewatchHelper.isWild() && applied){
            applied = false;
            addToBot(new ApplyPowerAction(owner,owner,new StrengthPower(owner,-amount),-amount));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        if(FirewatchHelper.isWild() && applied){
            addToBot(new ApplyPowerAction(owner,owner,new StrengthPower(owner,stackAmount),stackAmount));
        }
    }

    @Override
    public void onRemove() {
        judgeApply();
    }

    public GrowingPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

    @Override
    public void atStartOfTurn() {
        if(FirewatchHelper.isWild()){
            this.flash();
            addToBot(new GainEnergyAction(1));
        }
    }

    @Override
    public void onInitialApplication() {
        judgeApply();
    }
}


