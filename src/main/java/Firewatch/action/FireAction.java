package Firewatch.action;

import Firewatch.card.attack.UnyieldFire;
import Firewatch.modifier.FireModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class FireAction extends AbstractGameAction {
    public FireAction(UnyieldFire unyieldFire) {
        this.unyieldFire = unyieldFire;
    }

    @Override
    public void update() {
        CardModifierManager.addModifier(unyieldFire,new FireModifier());
        this.isDone = true;
    }

    UnyieldFire unyieldFire;
}
