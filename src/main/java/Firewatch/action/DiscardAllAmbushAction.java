package Firewatch.action;

import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class DiscardAllAmbushAction extends AbstractGameAction {
    public DiscardAllAmbushAction() {}

    @Override
    public void update() {
        for(AbstractCard c: AmbushPatch.ambushGroup.group){
            addToTop(new DiscardSpecificCardAction(c,AmbushPatch.ambushGroup));
        }
        this.isDone = true;
    }
}
