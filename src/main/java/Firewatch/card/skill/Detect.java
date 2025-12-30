package Firewatch.card.skill;

import Firewatch.card.AbstractFirewatchCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class Detect extends AbstractFirewatchCard {
    public static final String ID = "firewatch:Detect";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public Detect() {
        super(ID,cardStrings.NAME,0,cardStrings.DESCRIPTION,CardType.SKILL,CardRarity.COMMON,CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int amt = 0;
        for(AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if(!m.isDeadOrEscaped()){
                amt++;
                if(m.getIntentBaseDmg()>0){
                    this.addToBot(new ApplyPowerAction(m, abstractPlayer, new StrengthPower(m, -this.magicNumber), -this.magicNumber));
                    if (!m.hasPower("Artifact")) {
                        this.addToBot(new ApplyPowerAction(m, abstractPlayer, new GainStrengthPower(m, this.magicNumber), this.magicNumber));
                    }
                }
                else {
                    this.addToBot(new ApplyPowerAction(m,abstractPlayer,new VulnerablePower(m,1,false),1));
                }
            }
        }
        if(upgraded){
            addToBot(new DrawCardAction(amt));
        }
        if(isAmbushCard()){
            addToBot(new MakeTempCardInDrawPileAction(this,1,false,true));
            ambushOnce();
        }
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            initializeDescription();
            selfRetain = true;
        }
    }
}



