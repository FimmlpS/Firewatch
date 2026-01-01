package Firewatch.card.power;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.power.buff.OurHomePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OurHome extends AbstractFirewatchCard {
    public static final String ID = "firewatch:OurHome";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public OurHome() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.POWER,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
       addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new OurHomePower(abstractPlayer,upgraded)));
    }

    @Override
    public void upgrade() {
        if(!this.upgraded) {
            this.upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}

