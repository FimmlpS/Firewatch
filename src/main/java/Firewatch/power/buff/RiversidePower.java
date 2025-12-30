package Firewatch.power.buff;

import Firewatch.helper.AmbushHelper;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class RiversidePower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:RiversidePower";

    public RiversidePower(AbstractCreature owner){
        super(POWER_ID, owner);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.SKILL && (!card.freeToPlayOnce && card.costForTurn>0) && AmbushHelper.isInAmbush(card)){
            this.flash();
            addToBot(new GainEnergyAction(card.costForTurn));
        }
    }

    @Override
    public float modifyBlock(float blockAmount, AbstractCard card) {
        if(card.type == AbstractCard.CardType.SKILL && AmbushHelper.isInAmbush(card))
            return 0;
        return super.modifyBlock(blockAmount, card);
    }
}
