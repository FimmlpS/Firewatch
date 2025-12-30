package Firewatch.card.skill;

import Firewatch.action.MorningBirdAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.modifier.SoundModifier;
import Firewatch.power.debuff.IncreaseSpiritPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

public class IncreaseSpirit extends AbstractFirewatchCard {
    public static final String ID = "firewatch:IncreaseSpirit";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public IncreaseSpirit() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new StrengthPower(abstractPlayer,magicNumber),magicNumber));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new LoseStrengthPower(abstractPlayer,magicNumber),magicNumber));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new VigorPower(abstractPlayer,magicNumber),magicNumber));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new IncreaseSpiritPower(abstractPlayer,1),1));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}



