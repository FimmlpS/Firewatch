package Firewatch.action;

import Firewatch.character.Firewatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;
import java.util.Collections;

public class RadioAction extends AbstractGameAction {
    public RadioAction(int baseDamage, int times) {
        this.amount = baseDamage;
        this.times = times;
    }

    int times;

    @Override
    public void update() {
        Firewatch.onAttack(true);
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower s = p.getPower(StrengthPower.POWER_ID);
        if(s!=null){
            this.amount += s.amount;
            if(amount<0)
                amount=0;
        }
        ArrayList<AbstractMonster> tobeSelected = new ArrayList<>();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(!m.isDeadOrEscaped()) {
                tobeSelected.add(m);
            }
        }
        if(tobeSelected.isEmpty()) {
            this.isDone = true;
            return;
        }
        ArrayList<AbstractGameAction> actions = new ArrayList<>();
        Collections.shuffle(tobeSelected,AbstractDungeon.cardRandomRng.random);
        for(int i = 0; i < times; i++) {
            AbstractMonster m = tobeSelected.get(0);
            if(tobeSelected.size()>1){
                tobeSelected.remove(0);
            }

            actions.add(0,new DamageAction(m,new DamageInfo(p,amount, DamageInfo.DamageType.THORNS),AttackEffect.FIRE));
        }

        for(AbstractGameAction action : actions) {
            addToTop(action);
        }

        this.isDone = true;
    }
}
