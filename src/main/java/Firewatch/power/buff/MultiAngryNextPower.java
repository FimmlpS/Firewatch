package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MultiAngryNextPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:MultiAngryNextPower";

    boolean upgraded;

    public MultiAngryNextPower(AbstractCreature owner, boolean upgraded) {
        super(POWER_ID,owner);
        this.upgraded = upgraded;
        this.name = powerStrings.DESCRIPTIONS[upgraded?2:0];
        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[upgraded?3:1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if(card.type == AbstractCard.CardType.STATUS || card.type == AbstractCard.CardType.CURSE) {
            this.flash();
            addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.drawPile));
            addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));
            addToBot(new ExhaustSpecificCardAction(card, AbstractDungeon.player.discardPile));
            addToBot(new DrawCardAction(3));
            if(upgraded) {
                addToBot(new GainEnergyAction(1));
            }
        }
    }
}


