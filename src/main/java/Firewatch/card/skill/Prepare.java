package Firewatch.card.skill;

import Firewatch.action.PrepareAction;
import Firewatch.card.AbstractFirewatchCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Prepare extends AbstractFirewatchCard {
    public static final String ID = "firewatch:Prepare";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Prepare() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 8;
        baseMagicNumber = magicNumber = 2;
        cardsToPreview = new YesTerra();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new PrepareAction(1,false).setTurns(magicNumber));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(-1);
        }
    }
}






