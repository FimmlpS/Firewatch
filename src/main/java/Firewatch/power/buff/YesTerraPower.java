package Firewatch.power.buff;

import Firewatch.action.YesTerraAction;
import Firewatch.helper.AmbushHelper;
import Firewatch.power.AbstractFirewatchPower;
import Firewatch.power.debuff.NoTerraPower;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class YesTerraPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:YesTerraPower";

    public YesTerraPower(AbstractCreature owner, boolean upgraded) {
        super(POWER_ID,owner);
        this.upgraded = upgraded;
        if(upgraded){
            this.ID += "Plus";
        }
        updateDescription();
    }

    boolean upgraded = false;

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[upgraded?1:0];
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(AmbushHelper.isInAmbush(card)) {
            if(!owner.hasPower(NoTerraPower.POWER_ID)){
                this.flash();
                addToBot(new YesTerraAction(upgraded));
            }
        }
    }
}

