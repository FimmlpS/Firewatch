package Firewatch.action;

import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Collections;

public class YesTerraAction extends AbstractGameAction {
    public YesTerraAction(boolean upgraded) {
        this.up = upgraded;
    }

    boolean up;

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(AbstractDungeon.player.hand.group);
        if(up){
            cards.addAll(AbstractDungeon.player.drawPile.group);
            cards.addAll(AbstractDungeon.player.discardPile.group);
        }

        Collections.shuffle(cards,AbstractDungeon.cardRandomRng.random);
        if(!cards.isEmpty()){
            AbstractCard c = cards.get(0);
            if(AmbushPatch.ambushGroup.size()>=AmbushPatch.ambushArea.getTopLimit()){
                boolean continueIt = AmbushPatch.ambushArea.replaceStrategy(AmbushPatch.ambushGroup,c);
                if(!continueIt){
                    this.isDone = true;
                    return;
                }
            }

            if(AbstractDungeon.player.hand.contains(c)){
                AbstractDungeon.player.hand.removeCard(c);
            }
            else if(AbstractDungeon.player.drawPile.contains(c)){
                AbstractDungeon.player.drawPile.removeCard(c);
            }
            else if(AbstractDungeon.player.discardPile.contains(c)){
                AbstractDungeon.player.discardPile.removeCard(c);
            }
            AbstractDungeon.actionManager.removeFromQueue(c);
            AmbushPatch.addToTop(c);
        }

        this.isDone = true;
    }
}
