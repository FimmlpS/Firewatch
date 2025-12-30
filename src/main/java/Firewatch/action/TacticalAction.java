package Firewatch.action;

import Firewatch.power.buff.GoodEquipmentPower;
import Firewatch.relic.TacticalRadio;
import Firewatch.relic.UpgradeTacticalRadio;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TacticalAction extends AbstractGameAction {
    public TacticalAction(TacticalRadio radio, AbstractCreature m, int amount) {
        this.radio = radio;
        this.m = m;
        this.amount = amount;
    }

    public TacticalAction(UpgradeTacticalRadio radio, AbstractCreature m, int amount) {
        this.upgradeRadio = radio;
        this.m = m;
        this.amount = amount;
    }

    @Override
    public void update() {
        if(!m.isDeadOrEscaped()){
            if(radio!=null){
                radio.increaseCounter(amount);
            }

            if(upgradeRadio!=null){
                upgradeRadio.increaseCounter(amount);
            }
        }
        this.isDone = true;
    }

    TacticalRadio radio;
    UpgradeTacticalRadio upgradeRadio;
    AbstractCreature m;
}
