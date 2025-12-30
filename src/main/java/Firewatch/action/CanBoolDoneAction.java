package Firewatch.action;

import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

public class CanBoolDoneAction extends AbstractGameAction {
    public CanBoolDoneAction(AbstractCard except, AbstractMonster target) {
        this.except = except;
        this.target = target;
    }
    AbstractCard except;

    @Override
    public void update() {

        ArrayList<AbstractCard.CardType> types = new ArrayList<>();
        types.add(AbstractCard.CardType.ATTACK);
        types.add(AbstractCard.CardType.SKILL);
        types.add(AbstractCard.CardType.POWER);
        types.add(AbstractCard.CardType.STATUS);
        types.add(AbstractCard.CardType.CURSE);

        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(AmbushPatch.ambushGroup.group);
        cards.addAll(AbstractDungeon.player.hand.group);

        for(AbstractCard.CardType type : types) {
            boolean done = false;
            Iterator<AbstractCard> iterator = cards.iterator();
            while(iterator.hasNext()) {
                AbstractCard c = iterator.next();
                if(c == except){
                    iterator.remove();
                    continue;
                }
                if(c.type == type) {
                    iterator.remove();
                    boolean changeFree = true;
                    if(c.freeToPlayOnce){
                        changeFree = false;
                    }
                    else
                        c.freeToPlayOnce = true;
                    if(c.canUse(AbstractDungeon.player,(AbstractMonster) target)){
                        done = true;
                    }
                    if(changeFree)
                        c.freeToPlayOnce = false;

                    if(done){
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
                        c.calculateCardDamage((AbstractMonster) target);
                        this.addToTop(new NewQueueCardAction(c, target, false, true));
                        this.addToTop(new UnlimboAction(c));
                        break;
                    }
                }
            }
        }

        this.isDone = true;
    }
}
