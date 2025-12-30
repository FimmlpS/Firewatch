package Firewatch.power.debuff;

import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class IncreaseSpiritPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:IncreaseSpiritPower";

    public IncreaseSpiritPower(AbstractCreature owner,int amount) {
        super(POWER_ID,owner, amount);
        setDebuff();
        setAmountDescription();
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.ATTACK) {
            this.flash();
            addToBot(new ApplyPowerAction(owner,owner,new StrengthPower(AbstractDungeon.player,-amount),-amount));
            addToBot(new ApplyPowerAction(owner,owner,new LoseStrengthPower(AbstractDungeon.player,-amount),-amount));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
    }
}

