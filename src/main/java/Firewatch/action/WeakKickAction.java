package Firewatch.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class WeakKickAction extends AbstractGameAction {
    public WeakKickAction(AbstractCreature target){
        this.target = target;
    }

    @Override
    public void update() {
        if(target.lastDamageTaken>0 || target.currentHealth == 0){
            addToTop(new ApplyPowerAction(target, AbstractDungeon.player,new VulnerablePower(target, 1, false), 1));
            for(AbstractMonster m : AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()){
                    addToTop(new ApplyPowerAction(m,AbstractDungeon.player,new WeakPower(m,2,false),2));
                }
            }
        }

        this.isDone = true;
    }
}
