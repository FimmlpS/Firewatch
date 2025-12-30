package Firewatch.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MorningBirdAction extends AbstractGameAction {
    public MorningBirdAction(int amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        int amt = AbstractDungeon.player.currentBlock;
        if(amt>amount)
            amt=amount;
        if(amt>0){
            addToTop(new HealAction(AbstractDungeon.player,AbstractDungeon.player,amt));
            addToTop(new LoseBlockAction(AbstractDungeon.player,AbstractDungeon.player,amt));
        }
        this.isDone = true;
    }
}
