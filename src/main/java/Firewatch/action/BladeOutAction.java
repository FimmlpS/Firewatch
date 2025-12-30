package Firewatch.action;

import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

import java.util.ArrayList;

public class BladeOutAction extends AbstractGameAction {
    public BladeOutAction(AbstractCard except, boolean upgraded) {
        this.duration = startDuration = Settings.ACTION_DUR_FAST;
        this.upgraded = upgraded;
        this.exceptCard = except;
    }

    AbstractCard exceptCard;
    boolean upgraded;

    @Override
    public void update() {
        if(duration == startDuration) {
            ArrayList<AbstractCard> cards = new ArrayList<>(AmbushPatch.ambushGroup.group);
            for(AbstractCard c : cards) {
                if(c == exceptCard)
                    continue;
                AmbushPatch.ambushGroup.moveToDeck(c,true);
            }
            if(upgraded) {
                addToTop(new DrawCardAction(cards.size()));
            }
        }

        this.tickDuration();
    }
}
