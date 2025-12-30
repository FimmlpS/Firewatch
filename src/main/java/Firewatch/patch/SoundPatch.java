package Firewatch.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class SoundPatch {

    @SpirePatch(clz = AbstractCard.class,method = SpirePatch.CLASS)
    public static class AbstractCardPatch {
        public static SpireField<Boolean> sounded = new SpireField<>(() -> false);
    }


}
