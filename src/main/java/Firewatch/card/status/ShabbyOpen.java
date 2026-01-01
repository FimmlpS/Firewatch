package Firewatch.card.status;

import Firewatch.action.EnemyAttackAction;
import Firewatch.card.AbstractFirewatchCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ShabbyOpen extends AbstractFirewatchCard {
    public static final String ID = "firewatch:ShabbyOpen";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public ShabbyOpen() {
        super(ID,cardStrings.NAME,-2,cardStrings.DESCRIPTION,CardType.STATUS,CardColor.COLORLESS,CardRarity.SPECIAL,CardTarget.ENEMY);
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        this.dontTriggerOnUseCard = true;
        this.purgeOnUse = true;
        addToBot(new EnemyAttackAction(abstractMonster));
    }

    @Override
    public void upgrade() {

    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return true;
    }
}
