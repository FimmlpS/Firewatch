package Firewatch.card.skill;

import Firewatch.action.ReplaceAreaAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class OverRiver extends AbstractFirewatchCard {
    public static final String ID = "firewatch:OverRiver";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public OverRiver() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 3;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new DrawCardAction(magicNumber));
        if(abstractPlayer.currentBlock>0){
            addToBot(new LoseBlockAction(abstractPlayer,abstractPlayer,8));
            addToBot(new DrawCardAction(2));
            if(upgraded){
                addToBot(new ReplaceAreaAction(AmbushPatch.AmbushType.Riverside));
            }
        }
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


