package Firewatch.helper;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;

public class RegisterHelper {
    public static ArrayList<String> startDeck = new ArrayList<>();
    public static ArrayList<String> startRelic = new ArrayList<>();

    public static ArrayList<AbstractCard> getCardsToAdd(){
        ArrayList<AbstractCard> list = new ArrayList<>();

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

    }

    static {
        initializeStart();
    }
}
