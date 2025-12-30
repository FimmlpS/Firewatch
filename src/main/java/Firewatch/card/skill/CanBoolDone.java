package Firewatch.card.skill;

import Firewatch.action.CanBoolDoneAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class CanBoolDone extends AbstractFirewatchCard {
    public static final String ID = "firewatch:CanBoolDone";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public CanBoolDone() {
        super(ID,cardStrings.NAME,3,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.ENEMY);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new CanBoolDoneAction(this,abstractMonster));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        ArrayList<CardType> types = new ArrayList<>();
        for(AbstractCard c : AmbushPatch.ambushGroup.group){
            if(c==this)
                continue;
            if(!types.contains(c.type)){
                types.add(c.type);
            }
        }
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if(c==this)
                continue;
            if(!types.contains(c.type)){
                types.add(c.type);
            }
        }
        if(types.size()<3){
            cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
}




