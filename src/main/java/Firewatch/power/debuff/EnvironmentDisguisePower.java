package Firewatch.power.debuff;

import Firewatch.helper.FirewatchHelper;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class EnvironmentDisguisePower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:EnvironmentDisguisePower";

    public EnvironmentDisguisePower(AbstractCreature owner) {
        super(POWER_ID,owner);
        setDebuff();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(AbstractDungeon.player.hand.contains(card) && !card.isInAutoplay) {
            this.flash();
            addToBot(new PressEndTurnButtonAction());
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
    }
}
