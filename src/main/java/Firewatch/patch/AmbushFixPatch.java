package Firewatch.patch;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.ui.AmbushPanel;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.screens.select.HandCardSelectScreen;

public class AmbushFixPatch {
    //第一回合开始后才可以
    public static boolean canSetRoundEnd = false;
    
    //本Patch是用来使得游击牌进入其他牌堆后失去属性并重置耗能

    private static void resetAmbush(CardGroup _inst, AbstractCard c) {
        if(_inst.type == OtherEnum.HAND_AMBUSH){
            AmbushPatch.CardField.inAmbush.set(c,true);
            AmbushPatch.ambushArea.onCardBecomeAmbush(c);
            return;
        }
        if(!AmbushPatch.CardField.inAmbush.get(c))
            return;
        if(_inst.type!= CardGroup.CardGroupType.DRAW_PILE&&_inst.type!= CardGroup.CardGroupType.HAND&&_inst.type!= CardGroup.CardGroupType.DISCARD_PILE&&_inst.type!= CardGroup.CardGroupType.EXHAUST_PILE)
            return;
        AmbushPatch.CardField.inAmbush.set(c,false);
        c.costForTurn = c.cost;
        if(c instanceof AbstractFirewatchCard){
            ((AbstractFirewatchCard) c).onLeaveAmbush();
        }
        AmbushPatch.ambushArea.onCardLeaveAmbush(c);
    }

    @SpirePatch(clz = CardGroup.class,method = "addToTop")
    public static class CGAddToTopPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst, AbstractCard c){
            resetAmbush(_inst, c);
        }
    }

    @SpirePatch(clz = CardGroup.class,method = "addToBottom")
    public static class CGAddToBottomPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst,AbstractCard c){
            resetAmbush(_inst, c);
        }
    }

    @SpirePatch(clz = CardGroup.class,method = "addToRandomSpot")
    public static class CGAddToRandomSpotPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst,AbstractCard c){
            resetAmbush(_inst, c);
        }
    }

    @SpirePatch(clz = CardGroup.class,method = "addToHand")
    public static class CGAddToHandPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst,AbstractCard c){
            if(AmbushPatch.CardField.inAmbush.get(c)){
                AmbushPatch.CardField.inAmbush.set(c,false);
                c.costForTurn = c.cost;
            }
        }
    }

    //打开handSelect时默认reset一次AmbushPanel
    @SpirePatch(clz = HandCardSelectScreen.class,method = "prep")
    public static class CGPrepPatch{
        @SpirePrefixPatch
        public static void Prefix(HandCardSelectScreen _inst){
            if(AmbushPanel.ambushed()){
                AmbushPanel.reset();
            }
        }
    }
}
