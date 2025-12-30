package Firewatch.power.buff;

import Firewatch.card.attack.MoonTalk;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class MoonTalkNextPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:MoonTalkNextPower";

    public MoonTalkNextPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        setAmountDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        if(card instanceof MoonTalk) {
            return damage+amount;
        }
        return super.atDamageGive(damage, type);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card instanceof MoonTalk) {
            this.flash();
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}


