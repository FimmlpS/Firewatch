package Firewatch.card.skill;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.helper.FirewatchHelper;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HappyToCry extends AbstractFirewatchCard {
    public static final String ID = "firewatch:HappyToCry";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HappyToCry() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(FirewatchHelper.playedColorless){
            addToBot(new GainEnergyAction(energyOnUse));
        }
        AbstractCard colorless = AbstractDungeon.returnTrulyRandomColorlessCardInCombat();
        if(colorless != null) {
            AbstractCard copy = colorless.makeCopy();
            CardModifierManager.addModifier(copy, new ExhaustMod());
            CardModifierManager.addModifier(copy, new EtherealMod());
            addToBot(new MakeTempCardInHandAction(copy));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            exhaust = false;
        }
    }
}



