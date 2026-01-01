package Firewatch.modifier;

import Firewatch.helper.SoundHelper;
import Firewatch.helper.StringHelper;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

public class HillModifier extends AbstractCardModifier {
    public static String ID = "firewatch:HillModifier";

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + StringHelper.MODIFIER.TEXT[1] + (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD;
    }

    public boolean shouldApply(AbstractCard card) {
        return !CardModifierManager.hasModifier(card,ID);
    }

    public AbstractCardModifier makeCopy() {
        return new HillModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
    }
}
