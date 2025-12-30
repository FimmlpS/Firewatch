package Firewatch.power.buff;

import Firewatch.helper.SoundHelper;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class AloudPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:AloudPower";

    public AloudPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(SoundHelper.isSoundCard(card)) {
            this.flashWithoutSound();
            addToBot(new DrawCardAction(amount));
        }
    }
}

