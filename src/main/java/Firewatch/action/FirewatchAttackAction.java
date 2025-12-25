package Firewatch.action;

import Firewatch.character.Firewatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class FirewatchAttackAction extends AbstractGameAction {
    public FirewatchAttackAction(){
        this(false);
    }

    public FirewatchAttackAction(boolean skill){
        this.skill = skill;
    }

    boolean skill;

    public void update() {
        Firewatch.onAttack(skill);
        this.isDone = true;
    }
}
