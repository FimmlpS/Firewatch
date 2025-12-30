package Firewatch.action;

import Firewatch.helper.FirewatchHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class FireNowAction extends AbstractGameAction {
    public FireNowAction(int amount,boolean checkTrigger) {
        this.amount = amount;
        this.checkTrigger = checkTrigger;
    }

    boolean checkTrigger = false;

    @Override
    public void update() {
        FirewatchHelper.increaseRadio(amount, checkTrigger);
        this.isDone = true;
    }
}
