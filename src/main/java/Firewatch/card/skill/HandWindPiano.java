package Firewatch.card.skill;

import Firewatch.action.DelayActionAction;
import Firewatch.action.HandWindPianoAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.modifier.SoundModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HandWindPiano extends AbstractFirewatchCard {
    public static final String ID = "firewatch:HandWindPiano";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HandWindPiano() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.NONE);
        baseMagicNumber = magicNumber = 6;
        exhaust = true;
        isEthereal = true;
        CardModifierManager.addModifier(this,new SoundModifier());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(upgraded){
            addToBot(new DrawCardAction(2));
        }
        addToBot(new DelayActionAction(new HandWindPianoAction()));
    }

    @Override
    public void onTogetherSound() {
        addToBot(new DrawCardAction(2));
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



