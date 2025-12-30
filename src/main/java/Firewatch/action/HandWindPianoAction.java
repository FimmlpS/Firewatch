package Firewatch.action;

import Firewatch.helper.SoundHelper;
import Firewatch.modifier.SoundModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HandWindPianoAction extends AbstractGameAction {
    public HandWindPianoAction() {}

    @Override
    public void update() {
        for(AbstractCard c: AbstractDungeon.player.hand.group){
            if(SoundHelper.isSoundCard(c))
                continue;
            c.flash();
            CardModifierManager.addModifier(c,new SoundModifier());
        }

        this.isDone = true;
    }
}
