package Firewatch.ambush;

import Firewatch.action.PlantAction;
import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class AmbushForest extends AbstractAmbushArea{
    public AmbushForest() {
        this.ambushType = AmbushPatch.AmbushType.Forest;
    }

    @Override
    public int getTopLimit() {
        return 3 + super.getTopLimit();
    }

    @Override
    public boolean replaceStrategy(CardGroup ambushGroup, AbstractCard card) {
        if(!ambushGroup.isEmpty()){
            AbstractCard c = ambushGroup.getBottomCard();
            ambushGroup.moveToDiscardPile(c);
            PlantAction.leaveCardCount++;
            return true;
        }
        return false;
    }
}
