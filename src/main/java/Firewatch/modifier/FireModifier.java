package Firewatch.modifier;

import Firewatch.helper.StringHelper;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;

import java.util.ArrayList;

public class FireModifier extends AbstractCardModifier {
    public static String ID = "firewatch:FireModifier";

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + " NL " + StringHelper.MODIFIER.TEXT[0] + (Settings.lineBreakViaCharacter ? " " : "") + LocalizedStrings.PERIOD;
    }

    public boolean shouldApply(AbstractCard card) {
        ArrayList<AbstractCardModifier> fires = CardModifierManager.getModifiers(card, ID);
        return fires.size() < 2;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        ArrayList<AbstractCardModifier> fires = CardModifierManager.getModifiers(card, ID);
        if(fires.size() == 1){
            card.cost++;
            card.baseDamage += 10;
            card.baseMagicNumber += 8;
            card.damage = card.baseDamage;
            card.magicNumber = card.baseMagicNumber;
            card.costForTurn = card.cost;
        }
        else if(fires.size() == 2){
            card.cost++;
            card.baseDamage += 20;
            card.baseMagicNumber += 8;
            card.damage = card.baseDamage;
            card.magicNumber = card.baseMagicNumber;
            card.costForTurn = card.cost;
        }
    }

    @Override
    public void onRemove(AbstractCard card) {
        ArrayList<AbstractCardModifier> fires = CardModifierManager.getModifiers(card, ID);
        if(fires.isEmpty()){
            card.cost--;
            card.baseDamage -= 10;
            card.baseMagicNumber -= 8;
            card.damage = card.baseDamage;
            card.magicNumber = card.baseMagicNumber;
            card.costForTurn = card.cost;
        }
        else if(fires.size() == 1){
            card.cost--;
            card.baseDamage -= 20;
            card.baseMagicNumber -= 8;
            card.damage = card.baseDamage;
            card.magicNumber = card.baseMagicNumber;
            card.costForTurn = card.cost;
        }
    }

    public AbstractCardModifier makeCopy() {
        return new FireModifier();
    }

    public String identifier(AbstractCard card) {
        return ID;
    }
}

