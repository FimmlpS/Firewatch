package Firewatch.action;

import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class DiscardOverflowAction extends AbstractGameAction {
    public DiscardOverflowAction() {

    }

    @Override
    public void update() {
        for(int i =AmbushPatch.ambushArea.getTopLimit();i<AmbushPatch.ambushGroup.size();i++){
            AbstractCard c = AmbushPatch.ambushGroup.group.get(i);
            addToTop(new DiscardSpecificCardAction(c,AmbushPatch.ambushGroup));
        }

        this.isDone = true;
    }
}
