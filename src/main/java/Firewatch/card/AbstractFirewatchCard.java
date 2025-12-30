package Firewatch.card;

import Firewatch.action.FirewatchAttackAction;
import Firewatch.helper.StringHelper;
import Firewatch.patch.AmbushPatch;
import Firewatch.patch.ColorEnum;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public abstract class AbstractFirewatchCard extends CustomCard {
    public static AbstractGameAction.AttackEffect attackEffect = AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
    public static AbstractGameAction.AttackEffect skillEffect = AbstractGameAction.AttackEffect.FIRE;
    boolean colored = false;

    private void setColor(){
        colored = true;
        ReflectionHacks.setPrivate(this,AbstractCard.class,"textColor", DARK_CREAM_TEXT_COLOR.cpy());
        ReflectionHacks.setPrivate(this,AbstractCard.class,"goldColor", DARK_GREEN_TEXT_COLOR.cpy());

    }

    public AbstractFirewatchCard(String id, String name, int cost, String rawDescription, AbstractCard.CardType type, CardRarity rarity, CardTarget target) {
        super(id, name, StringHelper.getCardIMGPath(id, type), cost, rawDescription, type, ColorEnum.Firewatch_COLOR, rarity, target);
        setColor();
    }

    public AbstractFirewatchCard(String id, String name, int cost, String rawDescription, AbstractCard.CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, StringHelper.getCardIMGPath(id, type), cost, rawDescription, type, color, rarity, target);
        if(color == ColorEnum.Firewatch_COLOR){
            setColor();
        }
    }

    protected boolean isAmbushCard(){
        return AmbushPatch.CardField.inAmbush.get(this);
    }

    protected boolean isAmbushCard(AmbushPatch.AmbushType type){
        return isAmbushCard() && AmbushPatch.ambushType==type;
    }

    //离开游击区触发
    public void onLeaveAmbush(){

    }

    //触发游击词条时，对所有手牌触发此trigger
    public void onAmbush(AbstractCard triggerCard){

    }

    private static Color GREEN_TEXT_COLOR = Settings.GREEN_TEXT_COLOR.cpy();
    private static Color RED_TEXT_COLOR = Settings.RED_TEXT_COLOR.cpy();
    private static Color CREAM_COLOR = Settings.CREAM_COLOR.cpy();

    private static Color DARK_GREEN_TEXT_COLOR;
    private static Color DARK_RED_TEXT_COLOR;
    private static Color DARK_CREAM_TEXT_COLOR;

    @SpirePatch(clz = AbstractCard.class,method = "renderDynamicVariable")
    public static class RenderDynamicVariablePatch {
        @SpirePrefixPatch
        public static void Prefix(AbstractCard _inst, char key, float start_x, float draw_y, int i, BitmapFont font, SpriteBatch sb, Character end) {
            if(_inst instanceof AbstractFirewatchCard && ((AbstractFirewatchCard) _inst).colored){
                Settings.GREEN_TEXT_COLOR.r = DARK_GREEN_TEXT_COLOR.r;
                Settings.GREEN_TEXT_COLOR.g = DARK_GREEN_TEXT_COLOR.g;
                Settings.GREEN_TEXT_COLOR.b = DARK_GREEN_TEXT_COLOR.b;
                Settings.RED_TEXT_COLOR.r = DARK_RED_TEXT_COLOR.r;
                Settings.RED_TEXT_COLOR.g = DARK_RED_TEXT_COLOR.g;
                Settings.RED_TEXT_COLOR.b = DARK_RED_TEXT_COLOR.b;
                Settings.CREAM_COLOR.r = DARK_CREAM_TEXT_COLOR.r;
                Settings.CREAM_COLOR.g = DARK_CREAM_TEXT_COLOR.g;
                Settings.CREAM_COLOR.b = DARK_CREAM_TEXT_COLOR.b;
            }
        }

        @SpirePostfixPatch
        public static float Postfix(float _ret, AbstractCard _inst,char key, float start_x, float draw_y, int i, BitmapFont font, SpriteBatch sb, Character end) {
            if(_inst instanceof AbstractFirewatchCard && ((AbstractFirewatchCard) _inst).colored){
                Settings.GREEN_TEXT_COLOR.r = GREEN_TEXT_COLOR.r;
                Settings.GREEN_TEXT_COLOR.g = GREEN_TEXT_COLOR.g;
                Settings.GREEN_TEXT_COLOR.b = GREEN_TEXT_COLOR.b;
                Settings.RED_TEXT_COLOR.r = RED_TEXT_COLOR.r;
                Settings.RED_TEXT_COLOR.g = RED_TEXT_COLOR.g;
                Settings.RED_TEXT_COLOR.b = RED_TEXT_COLOR.b;
                Settings.CREAM_COLOR.r = CREAM_COLOR.r;
                Settings.CREAM_COLOR.g = CREAM_COLOR.g;
                Settings.CREAM_COLOR.b = CREAM_COLOR.b;
            }
            return _ret;
        }
    }

    @SpirePatch(clz = AbstractCard.class,method = "getDynamicValue")
    public static class GetDynamicValuePatch {
        @SpirePostfixPatch
        public static String Postfix(String _ret, AbstractCard _inst, char key){
            if(_inst instanceof AbstractFirewatchCard && ((AbstractFirewatchCard) _inst).colored){
                if(_ret.startsWith("[")){
                    _ret = _ret.replaceAll("#7fff00","#7f7f00");
                }
                else {
                    _ret = "[#606060]" + _ret + "[]";
                }
            }

            return _ret;
        }
    }

    protected void attackOnce(){
        addToBot(new FirewatchAttackAction(false));
    }

    //触发一次游击效果
    protected void ambushOnce(){
        AmbushPatch.onTriggerAmbush(this);
    }

    static {
        DARK_GREEN_TEXT_COLOR = Settings.GREEN_TEXT_COLOR.cpy();
        DARK_GREEN_TEXT_COLOR.r *= 0.7F;
        DARK_GREEN_TEXT_COLOR.g *= 0.7F;
        DARK_GREEN_TEXT_COLOR.b *= 0.7F;
        DARK_RED_TEXT_COLOR = Settings.RED_TEXT_COLOR.cpy();
        DARK_RED_TEXT_COLOR.r *= 0.7F;
        DARK_RED_TEXT_COLOR.g *= 0.7F;
        DARK_RED_TEXT_COLOR.b *= 0.7F;
        DARK_CREAM_TEXT_COLOR = Settings.CREAM_COLOR.cpy();
        DARK_CREAM_TEXT_COLOR.r *= 0.5F;
        DARK_CREAM_TEXT_COLOR.g *= 0.5F;
        DARK_CREAM_TEXT_COLOR.b *= 0.5F;
    }
}

