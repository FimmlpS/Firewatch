package Firewatch.action;

import Firewatch.helper.AmbushHelper;
import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class ReplaceAreaAction extends AbstractGameAction {
    public ReplaceAreaAction(AmbushPatch.AmbushType newType) {
        this.ambushType = newType;
    }

    @Override
    public void update() {
        AmbushHelper.setAmbushType(ambushType);
        this.isDone = true;
    }

    AmbushPatch.AmbushType ambushType;
}
