package Firewatch.helper;

import Firewatch.card.attack.*;
import Firewatch.card.power.Assassin;
import Firewatch.card.power.GoodEquipment;
import Firewatch.card.power.SoHard;
import Firewatch.card.skill.*;
import Firewatch.relic.TacticalRadio;
import Firewatch.relic.UpgradeTacticalRadio;
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
        list.add(new EnvironmentDisguise());
        list.add(new Alert());

        //COMMON
        list.add(new CoverStrike());
        list.add(new OverRiver());
        list.add(new SetHigh());
        list.add(new HardToSay());
        list.add(new Detect());

        list.add(new IcelandTrace());
        list.add(new WeakKick());
        list.add(new BladeOut());
        list.add(new HappyToCry());
        list.add(new BrightStar());
        list.add(new CanBoolDone());

        //UNCOMMON
        list.add(new GoodEquipment());
        list.add(new MultiAngry());

        list.add(new SoHard());
        list.add(new Abandoned());

        list.add(new FireNow());

        //RARE
        list.add(new AmbushAttack());

        list.add(new Assassin());

        list.add(new MoonTalk());

        //SPECIAL
        list.add(new AlertCounter());

        return list;
    }

    public static ArrayList<AbstractRelic> getRelicsToAdd(boolean onlyFirewatch){
        ArrayList<AbstractRelic> list = new ArrayList<>();
        if(onlyFirewatch){
            //STARTER
            list.add(new TacticalRadio());


            //BOSS
            list.add(new UpgradeTacticalRadio());
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
        startDeck.add(EnvironmentDisguise.ID);
        startDeck.add(Alert.ID);

        //
        startRelic.add(TacticalRadio.ID);
    }

    static {
        initializeStart();
    }
}
