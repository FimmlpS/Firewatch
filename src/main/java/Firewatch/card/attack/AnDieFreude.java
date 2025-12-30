package Firewatch.card.attack;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.helper.FirewatchHelper;
import Firewatch.helper.SoundHelper;
import Firewatch.modifier.SoundModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AnDieFreude extends AbstractFirewatchCard {
    public static final String ID = "firewatch:AnDieFreude";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public AnDieFreude() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 5;
        isMultiDamage = true;
        CardModifierManager.addModifier(this,new SoundModifier());
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        attackOnce();
        addToBot(new DamageAllEnemiesAction(abstractPlayer,multiDamage,damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    private int getCount(){
        int count = 0;
        for(AbstractCard c: FirewatchHelper.getAllCardsInCombat()){
            if(SoundHelper.isSoundCard(c))
                count++;
        }
        return count;
    }

    public void applyPowers() {
        baseDamage = magicNumber * getCount();
        super.applyPowers();
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    public void calculateCardDamage(AbstractMonster mo) {
        baseDamage = magicNumber * getCount();
        super.calculateCardDamage(mo);
        this.rawDescription = cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
        }
    }
}




