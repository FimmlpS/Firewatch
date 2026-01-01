package Firewatch.card.attack;

import Firewatch.action.BladeOutAction;
import Firewatch.action.FireAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.modifier.FireModifier;
import Firewatch.power.debuff.UnyieldFirePower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class UnyieldFire extends AbstractFirewatchCard {
    public static final String ID = "firewatch:UnyieldFire";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public UnyieldFire() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY);
        baseDamage = damage = 20;
        baseMagicNumber = magicNumber = 0;
        this.isEthereal = true;
    }

    @Override
    public void applyPowers() {
        int tmp = baseDamage;
        baseDamage = baseMagicNumber;
        super.applyPowers();
        magicNumber = damage;
        isMagicNumberModified = baseMagicNumber != magicNumber;
        baseDamage = tmp;

        super.applyPowers();
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        attackOnce();
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new UnyieldFirePower(abstractPlayer,magicNumber),magicNumber));
        addToBot(new FireAction(this));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            CardModifierManager.removeModifiersById(this, FireModifier.ID,true);
            upgradeName();
            upgradeDamage(10);
        }
    }
}



