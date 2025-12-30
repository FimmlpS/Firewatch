package Firewatch.ambush;

import Firewatch.action.PlantAction;
import Firewatch.action.PlayAllAmbushAction;
import Firewatch.action.ReplaceAreaAction;
import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AmbushSnowTown extends AbstractAmbushArea{
    public boolean wouldNotTriggerReplace = false;

    public AmbushSnowTown() {
        this.ambushType = AmbushPatch.AmbushType.SnowTown;
    }

    @Override
    public int getTopLimit() {
        return 5 + super.getTopLimit();
    }

    @Override
    public void onCardBecomeAmbush(AbstractCard card) {
        //每次操作都会有概率弃掉最早进入的牌，此判定结束后容量为满，才会打出所有牌并更换游击区。目标随机
        boolean discard;
        int remain = getTopLimit() - AmbushPatch.ambushGroup.size();
        if(remain<0)
            remain = 0;
        discard = AbstractDungeon.cardRandomRng.randomBoolean(0.8F-0.1F*remain);
        if(discard){
            if(!AmbushPatch.ambushGroup.isEmpty()){
                AmbushPatch.ambushGroup.moveToDiscardPile(AmbushPatch.ambushGroup.getBottomCard());
                PlantAction.leaveCardCount++;
            }
        }
        int remainSize = AmbushPatch.ambushGroup.size()- (discard?1:0);
        if(remainSize>=getTopLimit() && !wouldNotTriggerReplace){
            PlantAction.leaveCardCount+=AmbushPatch.ambushGroup.size();
            wouldNotTriggerReplace = true;
            AbstractDungeon.actionManager.addToBottom(new PlayAllAmbushAction());
            AbstractDungeon.actionManager.addToBottom(new ReplaceAreaAction(AmbushPatch.AmbushType.SnowForest));
        }
    }

    @Override
    public void onEnterArea() {
        if(AmbushPatch.ambushGroup.size()>=getTopLimit() && !wouldNotTriggerReplace){
            wouldNotTriggerReplace = true;
            AbstractDungeon.actionManager.addToBottom(new PlayAllAmbushAction());
            AbstractDungeon.actionManager.addToBottom(new ReplaceAreaAction(AmbushPatch.AmbushType.SnowForest));
        }
    }
}
