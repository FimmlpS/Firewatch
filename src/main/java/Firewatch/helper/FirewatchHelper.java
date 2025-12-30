package Firewatch.helper;

import Firewatch.patch.AmbushPatch;
import Firewatch.relic.TacticalRadio;
import Firewatch.relic.UpgradeTacticalRadio;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class FirewatchHelper {
    //这里会记录你上一回合打出过的牌
    public static ArrayList<AbstractCard> cardsLastTurn = new ArrayList<>();
    public static ArrayList<AbstractCard> cardsThisTurn = new ArrayList<>();
    public static boolean playedColorless = false;

    public static void reset(){
        cardsLastTurn.clear();
        cardsThisTurn.clear();
        playedColorless = false;
    }

    public static void atTurnStart(){
        cardsLastTurn.clear();
        cardsLastTurn.addAll(cardsThisTurn);
        cardsThisTurn.clear();
    }

    public static void onUseCard(AbstractCard c){
        cardsThisTurn.add(c);
        if(c.color == AbstractCard.CardColor.COLORLESS){
            playedColorless = true;
        }
        AmbushPatch.ambushArea.onPlayCard(c);
    }

    public static void increaseRadio(int amount, boolean checkTrigger){
        AbstractRelic radio = AbstractDungeon.player.getRelic(TacticalRadio.ID);
        if(radio instanceof TacticalRadio){
            ((TacticalRadio) radio).increaseCounter(amount);
            if(checkTrigger){
                ((TacticalRadio) radio).checkRadioTrigger(false);
            }
        }
        AbstractRelic uRadio = AbstractDungeon.player.getRelic(UpgradeTacticalRadio.ID);
        if(uRadio instanceof UpgradeTacticalRadio){
            ((UpgradeTacticalRadio) uRadio).increaseCounter(amount);
            if(checkTrigger){
                ((UpgradeTacticalRadio) uRadio).checkRadioTrigger(false);
            }
        }
    }
}
