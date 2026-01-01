package Firewatch.card.skill;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.power.buff.YesTerraPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class YesTerra extends AbstractFirewatchCard {
    public static final String ID = "firewatch:YesTerra";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public YesTerra() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.SELF);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new YesTerraPower(abstractPlayer,upgraded)));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}

