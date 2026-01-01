package Firewatch.action;

import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SouthHorseAction extends AbstractGameAction {
    public SouthHorseAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        int amt =0;
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if(c.type== AbstractCard.CardType.ATTACK){
                addToTop(new DiscardSpecificCardAction(c));
                amt++;
            }
        }
        for(AbstractCard c : AmbushPatch.ambushGroup.group) {
            if(c.type == AbstractCard.CardType.ATTACK){
                addToTop(new DiscardSpecificCardAction(c,AmbushPatch.ambushGroup));
                amt++;
            }
        }
        for(int i = 0; i < amt; i++){
            addToBot(new GainBlockAction(AbstractDungeon.player,amount,true));
        }

        this.isDone = true;
    }

}
