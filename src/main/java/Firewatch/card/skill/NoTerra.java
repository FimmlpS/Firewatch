package Firewatch.card.skill;

import Firewatch.action.DiscardAllAmbushAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.power.debuff.NoTerraPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class NoTerra extends AbstractFirewatchCard {
    public static final String ID = "firewatch:NoTerra";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public NoTerra() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = block = 12;
        cardsToPreview = new YesTerra();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new DiscardAllAmbushAction());
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new NoTerraPower(abstractPlayer,1),1));
        addToBot(new MakeTempCardInHandAction(cardsToPreview,1,true));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(3);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            cardsToPreview = new YesTerra();
            cardsToPreview.upgrade();
        }
    }
}






