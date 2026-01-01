package Firewatch.card.power;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.power.buff.HardPlacePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HardPlace extends AbstractFirewatchCard {
    public static final String ID = "firewatch:HardPlace";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HardPlace() {
        super(ID,cardStrings.NAME,3,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.RARE,CardTarget.SELF);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new HardPlacePower(abstractPlayer)));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}



