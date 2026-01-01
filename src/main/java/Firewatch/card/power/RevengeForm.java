package Firewatch.card.power;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.power.buff.RevengeFormPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class RevengeForm extends AbstractFirewatchCard {
    public static final String ID = "firewatch:RevengeForm";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public RevengeForm() {
        super(ID,cardStrings.NAME,3,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.RARE,CardTarget.SELF);
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new DexterityPower(abstractPlayer,upgraded?6:4),upgraded?6:4));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new RevengeFormPower(abstractPlayer,magicNumber),magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}




