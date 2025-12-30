package Firewatch.helper;

import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class AmbushHelper {
    public static boolean isInAmbush(AbstractCard c){
        return AmbushPatch.CardField.inAmbush.get(c);
    }

    public static void setAmbushType(AmbushPatch.AmbushType type) {
        AmbushPatch.setType(type);
    }
}
