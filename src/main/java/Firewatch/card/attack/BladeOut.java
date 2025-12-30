package Firewatch.card.attack;

import Firewatch.action.BladeOutAction;
import Firewatch.card.AbstractFirewatchCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BladeOut extends AbstractFirewatchCard {
    public static final String ID = "firewatch:BladeOut";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public BladeOut() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        baseDamage = damage = 24;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        attackOnce();
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new BladeOutAction(this,upgraded));
        if(isAmbushCard()){
            shuffleBackIntoDrawPile = true;
            ambushOnce();
        }
        else {
            shuffleBackIntoDrawPile = false;
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



