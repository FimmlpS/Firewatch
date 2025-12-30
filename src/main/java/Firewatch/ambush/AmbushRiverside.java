package Firewatch.ambush;

import Firewatch.action.PlantAction;
import Firewatch.patch.AmbushPatch;
import Firewatch.power.buff.RiversidePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AmbushRiverside extends AbstractAmbushArea{
    public AmbushRiverside(){
        this.ambushType = AmbushPatch.AmbushType.Riverside;
    }

    @Override
    public int getTopLimit() {
        return 4 + super.getTopLimit();
    }

    @Override
    public boolean replaceStrategy(CardGroup ambushGroup, AbstractCard card) {
        if(!ambushGroup.isEmpty()){
            AbstractCard c = ambushGroup.getBottomCard();
            for(AbstractCard tmp:ambushGroup.group){
                if(tmp.type == AbstractCard.CardType.SKILL){
                    c=tmp;
                    break;
                }
            }
            ambushGroup.moveToDiscardPile(c);
            PlantAction.leaveCardCount++;
            return true;
        }
        return false;
    }

    @Override
    public void onEnterArea() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new RiversidePower(AbstractDungeon.player)));
    }

    @Override
    public void onExitArea() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player,AbstractDungeon.player,RiversidePower.POWER_ID));
    }
}
