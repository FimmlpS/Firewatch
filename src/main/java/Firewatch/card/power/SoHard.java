package Firewatch.card.power;

import Firewatch.action.ReplaceAreaAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.patch.AmbushPatch;
import Firewatch.power.buff.SoHardPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SoHard extends AbstractFirewatchCard {
    public static final String ID = "firewatch:SoHard";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public SoHard() {
        super(ID,cardStrings.NAME,2,cardStrings.DESCRIPTION,CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);
        baseMagicNumber = magicNumber = 10;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AmbushPatch.AmbushType type;
        if(AmbushPatch.ambushType== AmbushPatch.AmbushType.SnowTown)
            type = AmbushPatch.AmbushType.PlainTown;
        else if(AmbushPatch.ambushType== AmbushPatch.AmbushType.PlainTown)
            type = AmbushPatch.AmbushType.SnowTown;
        else if(AbstractDungeon.cardRandomRng.randomBoolean())
            type = AmbushPatch.AmbushType.SnowTown;
        else
            type = AmbushPatch.AmbushType.PlainTown;
        addToBot(new ApplyPowerAction(abstractPlayer,abstractPlayer,new SoHardPower(abstractPlayer,magicNumber),magicNumber));
        addToBot(new ReplaceAreaAction(type));
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}



