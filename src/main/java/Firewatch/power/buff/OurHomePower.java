package Firewatch.power.buff;

import Firewatch.card.status.AmbushAreaCard;
import Firewatch.patch.AmbushPatch;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import java.util.ArrayList;

public class OurHomePower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:OurHomePower";

    boolean upgraded = false;
    public static ArrayList<AmbushPatch.AmbushType> ambushTypes = new ArrayList<>();

    public OurHomePower(AbstractCreature owner, boolean upgraded) {
        super(POWER_ID,owner);
        this.upgraded = upgraded;
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        ambushTypes.clear();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        ArrayList<AbstractCard> choices = new ArrayList<>();
        ArrayList<AmbushPatch.AmbushType> tmp = new ArrayList<>();
        tmp.add(AmbushPatch.AmbushType.Forest);
        tmp.add(AmbushPatch.AmbushType.Riverside);
        tmp.add(AmbushPatch.AmbushType.SnowForest);
        tmp.add(AmbushPatch.AmbushType.Hill);
        for(int i =0;i<tmp.size();i++) {
            AmbushPatch.AmbushType type = tmp.get(i);
            if(upgraded || !ambushTypes.contains(type)) {
                AmbushAreaCard areaCard = new AmbushAreaCard();
                areaCard.setType(i);
                choices.add(areaCard);
            }
        }

        if(!choices.isEmpty()) {
            AmbushAreaCard areaCard = new AmbushAreaCard();
            areaCard.setType(4);
            choices.add(areaCard);
            this.flash();
            addToBot(new ChooseOneAction(choices));
        }
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[upgraded?1:0];
    }
}

