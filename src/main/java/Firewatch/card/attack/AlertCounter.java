package Firewatch.card.attack;

import Firewatch.card.AbstractFirewatchCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AlertCounter extends AbstractFirewatchCard {
    public static final String ID = "firewatch:AlertCounter";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public AlertCounter() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.ATTACK,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.ENEMY);
        baseDamage = damage = 3;
        baseBlock = block = 3;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        attackOnce();
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if(abstractMonster.getIntentBaseDmg()>0){
            addToBot(new GainBlockAction(abstractPlayer,block));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}
