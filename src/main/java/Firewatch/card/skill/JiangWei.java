package Firewatch.card.skill;

import Firewatch.card.AbstractFirewatchCard;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

public class JiangWei extends AbstractFirewatchCard {
    public static final String ID = "firewatch:JiangWei";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public JiangWei() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseBlock = block = 6;
        cardsToPreview = new SnakeOut();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new GainBlockAction(abstractPlayer, block));
        addToBot(new MakeTempCardInHandAction(cardsToPreview,1,true));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(4);
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            if(cardsToPreview != null) {
                cardsToPreview.upgrade();
            }
        }
    }
}




