package Firewatch.card.attack;

import Firewatch.action.FireNowAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.patch.AmbushPatch;
import Firewatch.relic.TacticalRadio;
import Firewatch.relic.UpgradeTacticalRadio;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Introduce extends AbstractFirewatchCard {
    public static final String ID = "firewatch:Introduce";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Introduce() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);
        baseDamage = damage = 0;
        baseMagicNumber = magicNumber = 2;
    }

    @Override
    public void applyPowers() {
        int tmp = baseDamage;
        AbstractRelic r = AbstractDungeon.player.getRelic(TacticalRadio.ID);
        if(r instanceof TacticalRadio) {
            baseDamage += Math.max(0,((TacticalRadio) r).maxCounter-r.counter);
        }
        else {
            r = AbstractDungeon.player.getRelic(UpgradeTacticalRadio.ID);
            if(r instanceof UpgradeTacticalRadio) {
                baseDamage += Math.max(0,((UpgradeTacticalRadio) r).maxCounter-r.counter);
            }
            else {
                baseDamage += 8;
            }
        }
        super.applyPowers();
        baseDamage = tmp;
        isDamageModified = damage != baseDamage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int tmp = baseDamage;
        AbstractRelic r = AbstractDungeon.player.getRelic(TacticalRadio.ID);
        if(r instanceof TacticalRadio) {
            baseDamage += Math.max(0,((TacticalRadio) r).maxCounter-r.counter);
        }
        else {
            r = AbstractDungeon.player.getRelic(UpgradeTacticalRadio.ID);
            if(r instanceof UpgradeTacticalRadio) {
                baseDamage += Math.max(0,((UpgradeTacticalRadio) r).maxCounter-r.counter);
            }
            else {
                baseDamage += 8;
            }
        }
        super.calculateCardDamage(mo);
        baseDamage = tmp;
        isDamageModified = damage != baseDamage;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        attackOnce();
        addToBot(new DamageAction(abstractMonster,new DamageInfo(abstractPlayer,damage,damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new FireNowAction(magicNumber,false));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(1);
        }
    }
}




