package Firewatch.helper;

import Firewatch.card.attack.Strike;
import Firewatch.card.skill.Defend;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class RegisterHelper {
    public static ArrayList<String> startDeck = new ArrayList<>();
    public static ArrayList<String> startRelic = new ArrayList<>();

    public static ArrayList<AbstractCard> getCardsToAdd(){
        ArrayList<AbstractCard> list = new ArrayList<>();

        //BASIC
        list.add(new Strike());
        list.add(new Defend());

        //COMMON

        //UNCOMMON

        //RARE
        return list;
    }

    public static ArrayList<AbstractRelic> getRelicsToAdd(boolean onlyFirewatch){
        ArrayList<AbstractRelic> list = new ArrayList<>();
        if(onlyFirewatch){

        }
        else {

        }
        return list;
    }

    private static void initializeStart(){
        for(int i =0;i<5;i++){
            startDeck.add(Strike.ID);
        }
        for(int i =0;i<5;i++){
            startDeck.add(Defend.ID);
        }

        //
    }

    static {
        initializeStart();
    }
}
