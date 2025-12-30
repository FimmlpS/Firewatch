package Firewatch.action;

import Firewatch.helper.StringHelper;
import Firewatch.power.buff.StasisFixPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AbandonedAction extends AbstractGameAction {
    
    public AbandonedAction(AbstractCreature target,int amount) {
        this.target = target;
        this.amount = amount;
        this.duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if(startDuration==duration){
            if(shouldCancelAction()){
                this.isDone = true;
                return;
            }
            if(amount>AbstractDungeon.player.hand.size()){
                amount = AbstractDungeon.player.hand.size();
            }
            if(amount==0){
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(StringHelper.OPERATION.TEXT[0]+amount+StringHelper.OPERATION.TEXT[1],amount,true,true);
        }
        else if(!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved){
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            for(AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group){
                AbstractDungeon.player.limbo.addToBottom(c);
                c.setAngle(0.0F);
                c.targetDrawScale = 0.75F;
                c.lighten(false);
                c.unfadeOut();
                c.unhover();
                c.untip();
                c.stopGlowing();
                addToTop(new ApplyPowerAction(target,AbstractDungeon.player,new StasisFixPower(target,c)));
                addToTop(new ShowCardAction(c));
            }
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
        }
        
        this.tickDuration();
    }
}
