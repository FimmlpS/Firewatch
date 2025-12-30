package Firewatch.action;

import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class PlayAllAmbushAction extends AbstractGameAction {
    public PlayAllAmbushAction() {}

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = new ArrayList<>(AmbushPatch.ambushGroup.group);
        for(AbstractCard c : cards) {
            AmbushPatch.ambushGroup.group.remove(c);
            AbstractDungeon.player.hand.group.remove(c);
            AbstractDungeon.getCurrRoom().souls.remove(c);
            AbstractDungeon.player.limbo.group.add(c);
            c.current_y = -200.0F * Settings.scale;
            c.target_x = (float)Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            c.target_y = (float)Settings.HEIGHT / 2.0F;
            c.targetAngle = 0.0F;
            c.lighten(false);
            c.drawScale = 0.12F;
            c.targetDrawScale = 0.75F;
            c.applyPowers();
            this.addToTop(new NewQueueCardAction(c, true, false, true));
            this.addToTop(new UnlimboAction(c));
        }

        this.isDone = true;
    }
}
