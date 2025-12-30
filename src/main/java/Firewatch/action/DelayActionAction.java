package Firewatch.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public class DelayActionAction extends AbstractGameAction {
    public DelayActionAction(AbstractGameAction action) {
        this.action = action;
    }

    @Override
    public void update() {
        addToBot(action);
        this.isDone = true;
    }

    AbstractGameAction action;
}
