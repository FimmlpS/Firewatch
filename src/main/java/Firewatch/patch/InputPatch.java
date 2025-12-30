package Firewatch.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;

public class InputPatch {
    public static InputAction changeArea;
    private static final String CHANGE_AREA_KEY = "CHANGE_AREA";

    @SpirePatch(clz = InputActionSet.class,method = "load")
    public static class InputLoadPatch {
        @SpirePrefixPatch
        public static void Prefix() {
            changeArea = new InputAction(InputActionSet.prefs.getInteger(CHANGE_AREA_KEY,45));
        }
    }

    @SpirePatch(clz = InputActionSet.class,method = "save")
    public static class InputSavePatch {
        @SpirePrefixPatch
        public static void Prefix() {
            InputActionSet.prefs.putInteger(CHANGE_AREA_KEY,changeArea.getKey());
        }
    }

    @SpirePatch(clz = InputActionSet.class,method = "resetToDefaults")
    public static class InputResetToDefaultsPatch {
        @SpirePrefixPatch
        public static void Prefix() {
            changeArea.remap(45);
        }
    }
}
