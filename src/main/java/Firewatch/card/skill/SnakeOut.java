package Firewatch.card.skill;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.patch.AmbushPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class SnakeOut extends AbstractFirewatchCard {
    public static final String ID = "firewatch:SnakeOut";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public SnakeOut() {
        super(ID,cardStrings.NAME,-2,cardStrings.DESCRIPTION,CardType.SKILL,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.NONE);
        baseMagicNumber = magicNumber = 3;
        isEthereal = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    boolean trigger = false;

    @Override
    public void tookDamage() {
        if(!trigger){
            trigger = true;
        }
        else {
            return;
        }
        this.flash();
        addToTop(new ExhaustSpecificCardAction(this,AbstractDungeon.player.hand));
        addToTop(new ExhaustSpecificCardAction(this,AbstractDungeon.player.drawPile));
        addToTop(new ExhaustSpecificCardAction(this,AbstractDungeon.player.discardPile));
        addToTop(new ExhaustSpecificCardAction(this, AmbushPatch.ambushGroup));
        if(DamagePatch.source == null){
            DamagePatch.source = AbstractDungeon.getRandomMonster();
        }
        if(DamagePatch.source!=null){
            addToBot(new ApplyPowerAction(DamagePatch.source,AbstractDungeon.player,new VulnerablePower(DamagePatch.source,magicNumber,false),magicNumber));
            if(upgraded){
                addToBot(new ApplyPowerAction(DamagePatch.source,AbstractDungeon.player,new WeakPower(DamagePatch.source,magicNumber,false),magicNumber));
            }
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

    @SpirePatch(clz = AbstractPlayer.class,method = "damage")
    public static class DamagePatch {
        public static AbstractCreature source = null;

        @SpirePrefixPatch
        public static void onDamage(AbstractPlayer _inst, DamageInfo info) {
            source = info.owner;
        }
    }
}
