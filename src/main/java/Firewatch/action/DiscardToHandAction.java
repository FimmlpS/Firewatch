package Firewatch.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DiscardToHandAction extends AbstractGameAction {
    public DiscardToHandAction(int count) {
        this(count,false);
    }

    public DiscardToHandAction(int count,boolean freeOnce) {
        this.amount = count;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.free = freeOnce;
    }

    AbstractCard c;
    boolean free;

    public DiscardToHandAction(AbstractCard c) {
        this.c = c;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            CardGroup dPile = AbstractDungeon.player.discardPile;
            ArrayList<AbstractCard> discard = dPile.group;
            for (int i = 0; i < this.amount; i++) {
                if (discard.size() > 0 && AbstractDungeon.player.hand.size()<10) {
                    int random = AbstractDungeon.cardRandomRng.random(discard.size() - 1);
                    AbstractCard card = discard.get(random);
                    card.freeToPlayOnce = this.free;
                    AbstractDungeon.player.hand.addToHand(card);
                    card.unhover();
                    card.setAngle(0.0F, true);
                    card.lighten(false);
                    card.drawScale = 0.12F;
                    card.targetDrawScale = 0.75F;
                    card.applyPowers();
                    AbstractDungeon.player.discardPile.removeCard(card);
                    AbstractDungeon.player.onCardDrawOrDiscard();
                }
            }
            AbstractDungeon.player.hand.refreshHandLayout();
            AbstractDungeon.player.hand.glowCheck();

        }
        this.tickDuration();
        this.isDone = true;
    }
}

