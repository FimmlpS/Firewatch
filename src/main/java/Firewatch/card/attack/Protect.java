package Firewatch.card.attack;

import Firewatch.action.ProtectAction;
import Firewatch.card.AbstractFirewatchCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class Protect extends AbstractFirewatchCard {
    public static final String ID = "firewatch:Protect";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Protect() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        attackOnce();
        int max = 0;
        if(isAmbushCard()){
            ambushOnce();
            max = magicNumber;
        }
        addToBot(new ProtectAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn),max));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(3);
        }
    }
}





