package Firewatch.card.attack;

import Firewatch.action.MoonTalkAction;
import Firewatch.card.AbstractFirewatchCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MoonTalk extends AbstractFirewatchCard {
    public static final String ID = "firewatch:MoonTalk";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public MoonTalk() {
        super(ID,cardStrings.NAME,-1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.RARE,CardTarget.ALL_ENEMY);
        baseDamage = damage = 12;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        attackOnce();
        addToBot(new DamageAllEnemiesAction(abstractPlayer,multiDamage,damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new MoonTalkAction(abstractPlayer,freeToPlayOnce,energyOnUse,upgraded));
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



