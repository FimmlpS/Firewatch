package Firewatch.ambush;

import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AmbushSnowForest extends AbstractAmbushArea{
    public AmbushSnowForest() {
        this.ambushType = AmbushPatch.AmbushType.SnowForest;
    }

    @Override
    public int getTopLimit() {
        return 6 + super.getTopLimit();
    }

    @Override
    public boolean replaceStrategy(CardGroup ambushGroup, AbstractCard card) {
        if(!ambushGroup.isEmpty()){
            AbstractCard c = ambushGroup.getBottomCard();
            ambushGroup.moveToDiscardPile(c);
            return true;
        }
        return false;
    }

    @Override
    public void onPlayCard(AbstractCard card) {
        if(AmbushPatch.ambushGroup.size()>3){
            AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(1));
            for(int i = 3;i<AmbushPatch.ambushGroup.size();i++){
                AbstractCard c = AmbushPatch.ambushGroup.group.get(i);
                AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(c,AmbushPatch.ambushGroup));
            }
        }
    }
}
