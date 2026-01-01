package Firewatch.helper;

import Firewatch.card.attack.*;
import Firewatch.card.power.*;
import Firewatch.card.skill.*;
import Firewatch.card.status.ShabbyOpen;
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

        //BASIC 4
        list.add(new Strike());
        list.add(new Defend());
        list.add(new EnvironmentDisguise());
        list.add(new Alert());

        //COMMON 17
        list.add(new CoverStrike());
        list.add(new OverRiver());
        list.add(new SetHigh());
        list.add(new HardToSay());
        list.add(new Detect());
        list.add(new MorningBird());
        list.add(new HandWindPiano());
        list.add(new AnDieFreude());
        list.add(new IcelandTrace());
        list.add(new WeakKick());
        list.add(new BladeOut());
        list.add(new HappyToCry());
        list.add(new BrightStar());
        list.add(new CanBoolDone());
        list.add(new JiangWei());

        list.add(new IncreaseSpirit());
        list.add(new Besiege());

        //UNCOMMON 17
        list.add(new GoodEquipment());

        list.add(new SouthHorse());
        list.add(new MultiAngry());
        list.add(new WindAcrossRiver());
        list.add(new MouthPiano());

        list.add(new SoHard());
        list.add(new Abandoned());
        list.add(new Aloud());

        list.add(new Plant());
        list.add(new FireNow());
        list.add(new Growing());
        list.add(new NoTerra());
        list.add(new LightAndShadow());
        list.add(new Protect());
        list.add(new Prepare());
        list.add(new Introduce());
        list.add(new Clock());

        //RARE 10
        list.add(new Battlessless());
        list.add(new AmbushAttack());

        list.add(new Assassin());
        list.add(new ManVSWild());
        list.add(new RevengeForm());

        list.add(new MoonTalk());
        list.add(new UnyieldFire());

        list.add(new FallenYear());
        list.add(new HoldToAttack());
        list.add(new HardPlace());

        //SPECIAL 5
        list.add(new OurHome());
        list.add(new AlertCounter());
        list.add(new SnakeOut());
        list.add(new ShabbyOpen());
        list.add(new YesTerra());

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
