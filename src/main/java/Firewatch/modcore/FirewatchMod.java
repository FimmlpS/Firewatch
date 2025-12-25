package Firewatch.modcore;

import Firewatch.character.Firewatch;
import Firewatch.helper.RegisterHelper;
import Firewatch.patch.ClassEnum;
import Firewatch.patch.ColorEnum;
import basemod.BaseMod;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

@SpireInitializer
public class FirewatchMod implements PreStartGameSubscriber, StartGameSubscriber,OnPlayerTurnStartPostDrawSubscriber,PostBattleSubscriber,OnPlayerTurnStartSubscriber,OnStartBattleSubscriber,PostInitializeSubscriber,EditStringsSubscriber, EditKeywordsSubscriber, EditCardsSubscriber, EditCharactersSubscriber, EditRelicsSubscriber {
    private static final Logger logger = LogManager.getLogger(FirewatchMod.class);

    public static Color tColor = new Color(234f/255f,221f/255f,212f/255f,1);

    public static final String cardBg512 = "FirewatchResources/img/512/thoridal.png";
    public static final String energy512 = "FirewatchResources/img/512/energybg.png";
    public static final String cardBg1024 = "FirewatchResources/img/1024/thoridal.png";
    public static final String energy1024 = "FirewatchResources/img/1024/energybg.png";


    public static void initialize(){
        new FirewatchMod();
    }

    public static void logSomething(String message){
        logger.info(message);
    }

    public static int DefaultTypeIndex = 0;
    public static boolean ShouldResetUnlock = false;

    public FirewatchMod(){
        BaseMod.subscribe(this);
        BaseMod.addColor(ColorEnum.Firewatch_COLOR,tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),tColor.cpy(),cardBg512,cardBg512,cardBg512,energy512,cardBg1024,cardBg1024,cardBg1024,energy1024,"FirewatchResources/img/orbs/EnergyOrb.png");
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(
                new Firewatch("Firewatch"),
                "FirewatchResources/img/charSelect/FirewatchButton.png",
                "FirewatchResources/img/charSelect/FirewatchBG.png",
                ClassEnum.Firewatch_CLASS
        );
    }

    @Override
    public void receiveEditStrings() {
        String relic = "relics.json",
                card = "cards.json",
                power= "powers.json",
                potion="potions.json",
                event="events.json",
                character="characters.json",
                ui="uis.json",
                monster="monsters.json",
                score ="scores.json",
                stance ="stances.json";
        String fore = "FirewatchResources/localizations/";
        String lang;
        if(Settings.language == Settings.GameLanguage.ZHS||Settings.language == Settings.GameLanguage.ZHT){
            lang = "zh";
        }
        else{
            lang = "en";
        }
        relic = fore + lang + "/" + relic;
        card = fore + lang + "/" + card;
        power = fore + lang + "/" + power;
        potion = fore + lang + "/" + potion;
        event = fore + lang + "/" + event;
        character = fore + lang + "/" + character;
        ui = fore + lang + "/" + ui;
        monster = fore + lang + "/" + monster;
        score = fore + lang + "/" + score;
        stance = fore + lang + "/" + stance;

        String relicStrings = Gdx.files.internal(relic).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        String cardStrings = Gdx.files.internal(card).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
        String powerStrings = Gdx.files.internal(power).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
        //String eventStrings = Gdx.files.internal(event).readString(String.valueOf(StandardCharsets.UTF_8));
        //BaseMod.loadCustomStrings(EventStrings.class,eventStrings);
        String characterStrings = Gdx.files.internal(character).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CharacterStrings.class,characterStrings);
        String uiStrings = Gdx.files.internal(ui).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(UIStrings.class,uiStrings);
        String monsterStrings = Gdx.files.internal(monster).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(MonsterStrings.class,monsterStrings);
        String scoreStrings = Gdx.files.internal(score).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(ScoreBonusStrings.class,scoreStrings);
        String stanceStrings = Gdx.files.internal(stance).readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(StanceStrings.class,stanceStrings);
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "en";
        if (Settings.language == Settings.GameLanguage.ZHS || Settings.language == Settings.GameLanguage.ZHT) {
            lang = "zh";
        }
        else {
            lang = "en";
        }

        String json = Gdx.files.internal("FirewatchResources/localizations/"+lang+"/keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = (Keyword[])gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            Keyword[] var5 = keywords;
            int var6 = keywords.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                Keyword keyword = var5[var7];
                BaseMod.addKeyword("firewatch", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    @Override
    public void receiveEditCards() {
        for (AbstractCard c : RegisterHelper.getCardsToAdd()) {
            BaseMod.addCard(c);
        }
    }

    @Override
    public void receiveEditRelics() {
        for(AbstractRelic r: RegisterHelper.getRelicsToAdd(true)){
            BaseMod.addRelicToCustomPool(r,ColorEnum.Firewatch_COLOR);
        }

        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(false)){
            BaseMod.addRelic(r, RelicType.SHARED);
        }
    }

    @Override
    public void receiveStartGame() {

    }

    @Override
    public void receivePreStartGame() {

    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {

    }

    @Override
    public void receiveOnPlayerTurnStart() {

    }

    @Override
    public void receiveOnPlayerTurnStartPostDraw() {

    }

    @Override
    public void receivePostInitialize() {
        unlockCards();
        unlockRelics();

    }

    private static void unlockCards(){
        ArrayList<AbstractCard> list = RegisterHelper.getCardsToAdd();

        Iterator<AbstractCard> var1 = list.iterator();
        while (var1.hasNext()){
            AbstractCard c = var1.next();
            String key = c.cardID;
            AbstractCard tmp = CardLibrary.getCard(key);
            if (tmp != null && !CardLibrary.getCard(key).isSeen) {
                tmp.isSeen = true;
                tmp.unlock();
                UnlockTracker.seenPref.putInteger(key, 1);
            }
        }
        UnlockTracker.seenPref.flush();
    }

    private static void unlockRelics(){
        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(true)){
            UnlockTracker.markRelicAsSeen(r.relicId);
        }

        for(AbstractRelic r:RegisterHelper.getRelicsToAdd(false)){
            UnlockTracker.markRelicAsSeen(r.relicId);
        }
    }
}
