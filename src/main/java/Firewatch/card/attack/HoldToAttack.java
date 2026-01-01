package Firewatch.card.attack;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class HoldToAttack extends AbstractFirewatchCard {
    public static final String ID = "firewatch:HoldToAttack";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HoldToAttack() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY);
        baseDamage = damage = 4;
    }

    @Override
    public void applyPowers() {
        int tmp = baseDamage;
        int size = AmbushPatch.typeEnters.size();
        for(int i =0;i<size;i++) {
            baseDamage *= 2;
        }
        super.applyPowers();
        baseDamage = tmp;
        isDamageModified = damage != baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = baseDamage;
        int size = AmbushPatch.typeEnters.size();
        for(int i =0;i<size;i++) {
            baseDamage *= 2;
        }
        super.calculateCardDamage(mo);
        baseDamage = tmp;
        isDamageModified = damage != baseDamage;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        attackOnce();
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(1);
        }
    }
}



