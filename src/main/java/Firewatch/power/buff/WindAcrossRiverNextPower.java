package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.ArrayList;

public class WindAcrossRiverNextPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:WindAcrossRiverNextPower";

    public WindAcrossRiverNextPower(AbstractCreature owner) {
        super(POWER_ID,owner);
    }

    ArrayList<AbstractCard.CardType> cardTypes = new ArrayList<>();

    public boolean onTriggerAmbush(AbstractCard c) {
        if(!cardTypes.contains(c.type)) {
            cardTypes.add(c.type);
            return true;
        }
        return false;
    }
}


