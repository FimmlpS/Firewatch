package Firewatch.card.status;

import Firewatch.action.ReplaceAreaAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.patch.AmbushPatch;
import Firewatch.power.buff.OurHomePower;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AmbushAreaCard extends AbstractFirewatchCard {
    public static final String ID = "firewatch:AmbushAreaCard";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public AmbushAreaCard() {
        super(ID,cardStrings.NAME,-2,cardStrings.DESCRIPTION,CardType.STATUS,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.NONE);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }

    @Override
    public void upgrade() {

    }

    @Override
    public void onChoseThisOption() {
        AmbushPatch.AmbushType at = null;
        switch (type) {
            case 0:
                at = AmbushPatch.AmbushType.Forest;
                break;
            case 1:
                at = AmbushPatch.AmbushType.Riverside;
                break;
            case 2:
                at = AmbushPatch.AmbushType.SnowForest;
                break;
            case 3:
                at = AmbushPatch.AmbushType.Hill;
                break;
        }
        if(at != null) {
            OurHomePower.ambushTypes.add(at);
            addToBot(new ReplaceAreaAction(at));
        }
    }

    int type;

    public void setType(int type){
        this.type = type;
        this.name = cardStrings.EXTENDED_DESCRIPTION[type*2];
        this.rawDescription = cardStrings.EXTENDED_DESCRIPTION[type*2+1];
        initializeTitle();
        initializeDescription();
    }
}

