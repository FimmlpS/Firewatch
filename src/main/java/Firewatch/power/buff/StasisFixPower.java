package Firewatch.power.buff;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class StasisFixPower extends AbstractPower {
    public static final String POWER_ID = "firewatch:StasisFixPower";
    private static final PowerStrings powerStrings;
    private AbstractCard card;
    private static int offset = 0;

    public StasisFixPower(AbstractCreature owner, AbstractCard card) {
        this.name = powerStrings.NAME;
        offset++;
        this.ID = POWER_ID + offset;
        this.owner = owner;
        this.card = card;
        this.amount = -1;
        this.updateDescription();
        this.loadRegion("stasis");
    }

    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0] + FontHelper.colorString(this.card.name, "y") + powerStrings.DESCRIPTIONS[1];
    }

    public void onDeath() {
        if (AbstractDungeon.player.hand.size() != 10) {
            this.addToBot(new MakeTempCardInHandAction(this.card, false, true));
        } else {
            this.addToBot(new MakeTempCardInDiscardAction(this.card, true));
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings("Stasis");
    }
}

