package Firewatch.ambush;

import Firewatch.action.DiscardOverflowAction;
import Firewatch.action.ReplaceAreaAction;
import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AmbushPlainTown extends AbstractAmbushArea{
    public AmbushPlainTown() {
        this.ambushType = AmbushPatch.AmbushType.PlainTown;
    }

    boolean wontTriggerAnyMore = false;

    int increase = 0;

    @Override
    public int getTopLimit() {
        return super.getTopLimit() + 1 + increase;
    }

    @Override
    public void onCardBecomeAmbush(AbstractCard card) {
        //不会自己丢自己
        if(!AmbushPatch.ambushGroup.isEmpty()){
            AbstractCard bottom = AmbushPatch.ambushGroup.getBottomCard();
            if(bottom!=card){
                AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(bottom,AmbushPatch.ambushGroup));
            }
        }
    }

    public void shuffleIncrease(){
        increase += 2;
        if(increase > 4)
            increase = 4;
    }

    public void monsterDie(){
        if(wontTriggerAnyMore){
            return;
        }
        wontTriggerAnyMore = true;
        AbstractDungeon.actionManager.addToBottom(new ReplaceAreaAction(AmbushPatch.AmbushType.Forest));
        AbstractDungeon.actionManager.addToBottom(new DiscardOverflowAction());
    }

}
