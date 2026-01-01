package Firewatch.card.skill;

import Firewatch.action.BattlesslessAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.card.status.ShabbyOpen;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class Battlessless extends AbstractFirewatchCard {
    public static final String ID = "firewatch:Battlessless";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Battlessless() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION, CardType.SKILL, CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        cardsToPreview = new ShabbyOpen();
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(m == null || m.getIntentBaseDmg()<=0){
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        if(upgraded){
            addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new WeakPower(abstractMonster,1,false),1));
            addToBot(new ApplyPowerAction(abstractMonster,abstractPlayer,new VulnerablePower(abstractMonster,1,false),1));
        }
        addToBot(new BattlesslessAction(abstractMonster));
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





