package Firewatch.card.skill;

import Firewatch.action.SouthHorseAction;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.helper.FirewatchHelper;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

public class SouthHorse extends AbstractFirewatchCard {
    public static final String ID = "firewatch:SouthHorse";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public SouthHorse() {
        super(ID,cardStrings.NAME,1,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);
        baseBlock = block = 5;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        addToBot(new SouthHorseAction(block));
        boolean apply = true;
        if(!upgraded){
            for(AbstractCard c: FirewatchHelper.cardsThisTurn){
                if(c.type==CardType.ATTACK){
                    apply = false;
                    break;
                }
            }
        }
        if(apply){
            for(AbstractMonster m: AbstractDungeon.getMonsters().monsters){
                if(!m.isDeadOrEscaped()){
                    addToBot(new ApplyPowerAction(m,abstractPlayer,new WeakPower(m,1,false),1));
                }
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
}




