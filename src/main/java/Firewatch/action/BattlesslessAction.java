package Firewatch.action;

import Firewatch.card.status.ShabbyOpen;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BattlesslessAction extends AbstractGameAction {
    public BattlesslessAction(AbstractCreature target) {
        this.target = target;
    }

    @Override
    public void update() {
        if(target == null){
            this.isDone = true;
            return;
        }
        ArrayList<AbstractCard> cards = new ArrayList<>(AbstractDungeon.player.hand.group);
        cards.removeIf(c -> c.type != AbstractCard.CardType.ATTACK);
        cards.add(0,new ShabbyOpen());
        for (AbstractCard c : cards) {
            AbstractDungeon.player.hand.group.remove(c);
            AbstractDungeon.getCurrRoom().souls.remove(c);
            AbstractDungeon.player.limbo.group.add(c);
            c.current_y = -200.0F * Settings.scale;
            c.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            c.target_y = (float) Settings.HEIGHT / 2.0F;
            c.targetAngle = 0.0F;
            c.lighten(false);
            c.drawScale = 0.12F;
            c.targetDrawScale = 0.75F;
            c.applyPowers();
            c.calculateCardDamage((AbstractMonster) target);
            this.addToTop(new NewQueueCardAction(c, target, false, true));
            this.addToTop(new UnlimboAction(c));
        }

        this.isDone = true;
    }
}
