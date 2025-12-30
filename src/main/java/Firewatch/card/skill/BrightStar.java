package Firewatch.card.skill;

import Firewatch.action.BrightStarAction;
import Firewatch.card.AbstractFirewatchCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

public class BrightStar extends AbstractFirewatchCard {
    public static final String ID = "firewatch:BrightStar";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public BrightStar() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new DrawCardNextTurnPower(abstractPlayer,10),10));
        boolean increaseCost = true;
        if(upgraded && isAmbushCard()){
            increaseCost = false;
            ambushOnce();
        }
        addToBot(new BrightStarAction(this,increaseCost));
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




