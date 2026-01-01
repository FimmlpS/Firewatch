package Firewatch.power.buff;

import Firewatch.helper.AmbushHelper;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class RevengeFormPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:RevengeFormPower";

    public RevengeFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (AmbushHelper.isInAmbush(card)) {
            AbstractPower de = owner.getPower(DexterityPower.POWER_ID);
            if(de == null){
                return;
            }
            int transformCount = Math.min(de.amount,amount);
            if(transformCount > 0) {
                this.flashWithoutSound();
                addToBot(new ReducePowerAction(owner, owner, de, transformCount));
                addToBot(new ApplyPowerAction(owner,owner,new StrengthPower(owner, transformCount),transformCount));
            }
        }
    }
}


