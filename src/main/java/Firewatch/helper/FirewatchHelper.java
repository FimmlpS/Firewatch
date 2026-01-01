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

    public static ArrayList<AbstractCard> getAllCardsInCombat(){
        ArrayList<AbstractCard> cards = new ArrayList<>();
        cards.addAll(AbstractDungeon.player.drawPile.group);
        cards.addAll(AbstractDungeon.player.discardPile.group);
        cards.addAll(AbstractDungeon.player.hand.group);
        cards.addAll(AmbushPatch.ambushGroup.group);
        return cards;
    }

    public static boolean isWild(){
        if(AbstractDungeon.player.hasPower("todo") && AmbushPatch.ambushType==AmbushPatch.AmbushType.Forest){
            return false;
        }
        return AmbushPatch.ambushType == AmbushPatch.AmbushType.Forest || AmbushPatch.ambushType == AmbushPatch.AmbushType.Riverside || AmbushPatch.ambushType == AmbushPatch.AmbushType.SnowForest || AmbushPatch.ambushType == AmbushPatch.AmbushType.Hill;
    }

    public static boolean isNeighbor(){
        if(AbstractDungeon.player.hasPower("todo") && AmbushPatch.ambushType==AmbushPatch.AmbushType.Forest){
            return true;
        }
        return AmbushPatch.ambushType== AmbushPatch.AmbushType.SnowTown || AmbushPatch.ambushType== AmbushPatch.AmbushType.PlainTown;
    }

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
