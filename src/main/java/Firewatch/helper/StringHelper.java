package Firewatch.helper;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class StringHelper {
    public static final String MOD_ID = "firewatch:";

    public static String getRelicIMGPATH(String ID,boolean outline){
        //replace
        ID = ID.replace(MOD_ID,"");
        if(outline){
            return "FirewatchResources/img/relics/"+ID+"_O.png";
        }
        return "FirewatchResources/img/relics/"+ID+".png";
    }

    public static String getCardIMGPath(String ID, AbstractCard.CardType type) {
        ID = ID.replace(MOD_ID, "");
        switch (type) {
            case ATTACK: {
                ID += "_attack";
                break;
            }
            case CURSE:
            case STATUS:
            case SKILL: {
                ID += "_skill";
                break;
            }
            case POWER: {
                ID += "_power";
                break;
            }
        }
        String path = "FirewatchResources/img/cards/" + ID + ".png";
        if(Gdx.files.internal(path).exists()) {
            return path;
        }
        return getTempCardIMGPath(type);
    }

    public static String getTempCardIMGPath(AbstractCard.CardType type){
        switch (type) {
            case ATTACK: {
                return "FirewatchResources/img/cards/Strike_attack.png";
            }
            case CURSE:
            case STATUS:
            case SKILL: {
                return "FirewatchResources/img/cards/Defend_skill.png";
            }
            case POWER: {
                return "FirewatchResources/img/cards/Paveon_power.png";
            }
        }
        return "FirewatchResources/img/cards/Defend_skill.png";
    }
}
