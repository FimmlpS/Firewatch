package Firewatch.ambush;

import Firewatch.action.PlantAction;
import Firewatch.modifier.HillModifier;
import Firewatch.patch.AmbushPatch;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AmbushHill extends AbstractAmbushArea{
    public AmbushHill() {
        this.ambushType = AmbushPatch.AmbushType.Hill;
    }

    boolean triggered = false;

    @Override
    public int getTopLimit() {
        return 3 + super.getTopLimit();
    }

    @Override
    public void atTurnStart() {
        triggered = false;
    }

    @Override
    public boolean replaceStrategy(CardGroup ambushGroup, AbstractCard card) {
        if(!ambushGroup.isEmpty()){
            AbstractCard c = ambushGroup.getRandomCard(AbstractDungeon.cardRandomRng);
            ambushGroup.moveToDiscardPile(c);
            if(!triggered && !CardModifierManager.hasModifier(c,HillModifier.ID)){
                triggered = true;
                CardModifierManager.addModifier(c,new HillModifier());
            }
            PlantAction.leaveCardCount++;
            return true;
        }
        return false;
    }
}
