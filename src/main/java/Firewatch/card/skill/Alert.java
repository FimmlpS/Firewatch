package Firewatch.card.skill;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.card.attack.AlertCounter;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class Alert extends AbstractFirewatchCard {
    public static final String ID = "firewatch:Alert";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Alert() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.BASIC,CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 1;
        cardsToPreview = new AlertCounter();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDeadOrEscaped()){
                addToBot(new MakeTempCardInHandAction(cardsToPreview,1));
            }
        }
        if(isAmbushCard()){
            addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new StrengthPower(abstractPlayer,magicNumber),magicNumber));
            addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new LoseStrengthPower(abstractPlayer,magicNumber),magicNumber));
            ambushOnce();
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            cardsToPreview.upgrade();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

