package Firewatch.card.skill;

import Firewatch.action.MorningBirdAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.modifier.SoundModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MorningBird extends AbstractFirewatchCard {
    public static final String ID = "firewatch:MorningBird";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public MorningBird() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseBlock = block = 6;
        baseMagicNumber = magicNumber = 6;
        exhaust = true;
        tags.add(CardTags.HEALING);
        CardModifierManager.addModifier(this,new SoundModifier());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(upgraded){
            addToBot(new GainBlockAction(abstractPlayer, block));
        }
        addToBot(new MorningBirdAction(magicNumber));
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



