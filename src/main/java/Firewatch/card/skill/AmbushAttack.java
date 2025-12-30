package Firewatch.card.skill;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.power.buff.AmbushPower;
import Firewatch.power.buff.RetainCardFixPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConservePower;

public class AmbushAttack extends AbstractFirewatchCard {
    public static final String ID = "firewatch:AmbushAttack";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public AmbushAttack() {
        super(ID,cardStrings.NAME,3,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.RARE,CardTarget.ALL);
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
       addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new AmbushPower(abstractPlayer)));
       for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
           if(!m.isDeadOrEscaped()){
               addToBot(new ApplyPowerAction(m, abstractPlayer, new AmbushPower(m)));
           }
       }
       addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new RetainCardFixPower(abstractPlayer,10),10));
       if(upgraded){
           addToBot(new ApplyPowerAction(abstractPlayer, abstractPlayer, new ConservePower(abstractPlayer,1),1));
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
}





