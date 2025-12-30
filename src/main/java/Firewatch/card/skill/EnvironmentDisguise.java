package Firewatch.card.skill;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.power.debuff.EnvironmentDisguisePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

public class EnvironmentDisguise extends AbstractFirewatchCard {
    public static final String ID = "firewatch:EnvironmentDisguise";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public EnvironmentDisguise() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.BASIC,CardTarget.SELF);
        baseBlock = block = 5;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new IntangiblePlayerPower(abstractPlayer,1),1));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new EnvironmentDisguisePower(abstractPlayer)));
        if(isAmbushCard()){
            addToBot(new GainBlockAction(abstractPlayer, block));
            ambushOnce();
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
