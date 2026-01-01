package Firewatch.card.attack;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.helper.FirewatchHelper;
import basemod.cardmods.EtherealMod;
import basemod.cardmods.ExhaustMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Collections;

public class IcelandTrace extends AbstractFirewatchCard {
    public static final String ID = "firewatch:IcelandTrace";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public IcelandTrace() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);
        baseDamage = damage = 7;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        attackOnce();
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        ArrayList<AbstractCard> tmpGroup = new ArrayList<>();
        for(AbstractCard tmp : FirewatchHelper.cardsLastTurn){
            if(tmp.type == CardType.ATTACK && !(tmp instanceof IcelandTrace))
                tmpGroup.add(tmp);
        }
        if(tmpGroup.size()>1)
            Collections.shuffle(tmpGroup, AbstractDungeon.cardRandomRng.random);
        if(!tmpGroup.isEmpty()) {
            AbstractCard c = tmpGroup.get(0).makeStatEquivalentCopy();
            CardModifierManager.addModifier(c,new ExhaustMod());
            CardModifierManager.addModifier(c,new EtherealMod());
            addToBot(new MakeTempCardInHandAction(c,1));
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }
}



