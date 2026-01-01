package Firewatch.patch;

import Firewatch.ambush.*;
import Firewatch.card.AbstractFirewatchCard;
import Firewatch.character.Firewatch;
import Firewatch.power.buff.GrowingPower;
import Firewatch.power.buff.SoHardPower;
import Firewatch.power.buff.WindAcrossRiverNextPower;
import Firewatch.power.buff.WindAcrossRiverPower;
import Firewatch.power.debuff.NoTerraPower;
import Firewatch.ui.AmbushPanel;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.actions.utility.HandCheckAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import java.util.ArrayList;

public class AmbushPatch {
    //游击区相关
    public static int upLimit = 8;
    public static AmbushType ambushType = AmbushType.Forest;
    public static AbstractAmbushArea ambushArea = new AmbushForest();
    public static CardGroup ambushGroup = new CardGroup(OtherEnum.HAND_AMBUSH);
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("firewatch:AmbushPanel");

    public static void createAmbushFullDialog(){
        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, uiStrings.TEXT[2], true));
    }

    public static void atTurnStart(){
        ambushArea.atTurnStart();
    }

    public static void setType(AmbushType type){
        if(ambushType == type)
            return;
        recordType(type);
        //So hard
        if(ambushType == AmbushType.SnowTown || ambushType == AmbushType.PlainTown){
            AbstractPower soHard = AbstractDungeon.player.getPower(SoHardPower.POWER_ID);
            if(soHard!=null){
                soHard.onSpecificTrigger();
            }
        }
        ambushType = type;
        ambushArea.onExitArea();
        switch (ambushType){
            case Forest:
                ambushArea = new AmbushForest();
                break;
            case Riverside:
                ambushArea = new AmbushRiverside();
                break;
            case SnowForest:
                ambushArea = new AmbushSnowForest();
                break;
            case SnowTown:
                ambushArea = new AmbushSnowTown();
                break;
            case PlainTown:
                ambushArea = new AmbushPlainTown();
                break;
            case Hill:
                ambushArea = new AmbushHill();
                break;
            default:
                ambushArea = new AmbushForest();
                break;
        }
        ambushArea.onEnterArea();
        //屯田力量判断
        AbstractPower growing = AbstractDungeon.player.getPower(GrowingPower.POWER_ID);
        if(growing instanceof GrowingPower){
            ((GrowingPower) growing).judgeApply();
        }
    }

    //进入过的区域
    public static ArrayList<AmbushType> typeEnters = new ArrayList<>();
    //进入过的牌数
    public static int cardEnterCount = 0;

    public static void recordType(AmbushPatch.AmbushType type){
        if(!typeEnters.contains(type)){
            typeEnters.add(type);
        }
    }

    public static void resetAmbush(){
        ambushType = AmbushType.Forest;
        ambushArea = new AmbushForest();
        ambushGroup.clear();
        AmbushPanel.reset();
        typeEnters.clear();
        typeEnters.add(AmbushType.Forest);
        cardEnterCount = 0;
    }

    public static void onTriggerAmbush(AbstractCard card){
        //power优先
        AbstractPower windNext = AbstractDungeon.player.getPower(WindAcrossRiverNextPower.POWER_ID);
        boolean increaseTwo = false;
        if(windNext instanceof WindAcrossRiverNextPower){
            increaseTwo = ((WindAcrossRiverNextPower) windNext).onTriggerAmbush(card);
        }
        AbstractPower wind = AbstractDungeon.player.getPower(WindAcrossRiverPower.POWER_ID);
        if(wind!=null){
            if(increaseTwo){
                wind.amount+=2;
                wind.updateDescription();
            }
            wind.onSpecificTrigger();
        }

        for(AbstractCard c:AbstractDungeon.player.hand.group){
            if(c instanceof AbstractFirewatchCard){
                ((AbstractFirewatchCard) c).onAmbush(card);
            }
        }
    }

    public static void glowCheck(){
        ambushGroup.glowCheck();}
    public static void renderHand(SpriteBatch sb, AbstractCard except){
        ambushGroup.renderHand(sb,except);
    }
    public static void update(){
        ambushGroup.update();
        if(!AmbushPanel.ambushed()){
            for(AbstractCard c : ambushGroup.group){
                if(AbstractDungeon.player.hoveredCard == c){
                    AbstractDungeon.player.releaseCard();
                }
                c.unhover();
                c.untip();
            }
        }
    }
    public static void updateLogic(){
        ambushGroup.updateHoverLogic();}
    public static void renderTip(SpriteBatch sb){
        ambushGroup.renderTip(sb);
    }
    public static void applyPowers(){
        ambushGroup.applyPowers();}

    public static void addToTop(AbstractCard c){
        if(c!=null){
            if(AbstractDungeon.player.hoveredCard == c){
                AbstractDungeon.player.releaseCard();
            }
            AbstractCard sc = specialOnAmbush(c);
            sc.untip();
            sc.unhover();
            sc.stopGlowing();
            sc.lighten(true);
            //CardField.inAmbush.set(sc,true);
            ambushGroup.addToTop(sc);
        }
    }

    public static void addToBottom(AbstractCard c){
        if(c!=null){
            if(AbstractDungeon.player.hoveredCard == c){
                AbstractDungeon.player.releaseCard();
            }
            AbstractCard sc = specialOnAmbush(c);
            sc.untip();
            sc.unhover();
            sc.stopGlowing();
            sc.lighten(true);
            //CardField.inAmbush.set(sc,true);
            ambushGroup.addToBottom(c);
        }
    }

    public static void resetBeforeRemove(AbstractCard c){
        ambushGroup.removeCard(c);
        //CardField.inAmbush.set(c,false);
    }

    public static void moveToAmbush(CardGroup from,AbstractCard c){
        if(c!=null){
            if(AbstractDungeon.player.hoveredCard == c){
                AbstractDungeon.player.releaseCard();
            }
            AbstractDungeon.actionManager.removeFromQueue(c);
            if(from!=null)
                from.removeCard(c);
            AbstractCard sc = specialOnAmbush(c);
            CardField.inAmbush.set(sc,true);
            sc.untip();
            sc.unhover();
            sc.stopGlowing();
            sc.lighten(true);
            sc.setAngle(0F);
            sc.drawScale = 0.12F;
            sc.targetDrawScale = 0.75F;
            if(from!=null) {
                if (from.type == CardGroup.CardGroupType.DRAW_PILE) {
                    sc.current_x = CardGroup.DRAW_PILE_X;
                    sc.current_y = CardGroup.DRAW_PILE_Y;
                }
                else if(from.type== CardGroup.CardGroupType.DISCARD_PILE){
                    sc.current_x = CardGroup.DISCARD_PILE_X;
                    sc.current_y = CardGroup.DISCARD_PILE_Y;
                }
            }
            ambushGroup.addToTop(sc);
            refreshAmbushLayout();
            applyPowers();
        }
    }

    public static void moveToAmbush(AbstractCard c){
        moveToAmbush(null,c);
    }

    private static AbstractCard specialOnAmbush(AbstractCard c){
        return c;
    }

    public static void refreshAmbushLayout(){
        ambushGroup.refreshHandLayout();
        if(!AmbushPanel.ambushed()){
            for(AbstractCard c:ambushGroup.group){
                c.target_y = -300F*Settings.scale;
            }
        }
    }

    @SpirePatch(clz = AbstractCard.class,method = SpirePatch.CLASS)
    public static class CardField{
        public static SpireField<Boolean> inAmbush = new SpireField<>(() -> false);
    }

    //卡牌打出后进入游击区
    @SpirePatch(clz = UseCardAction.class,method = "update")
    public static class UseCardPatch{
        @SpireInsertPatch(rloc = 60)
        public static SpireReturn<Void> Insert(UseCardAction _inst){
            if(AbstractDungeon.player instanceof Firewatch){
                AbstractCard targetCard = ReflectionHacks.getPrivate(_inst,UseCardAction.class,"targetCard");
                //从游击区打出则直接进入弃牌堆
                if(CardField.inAmbush.get(targetCard)){
                    return SpireReturn.Continue();
                }
                //拥有土地流失时不能进入
                if(AbstractDungeon.player.hasPower(NoTerraPower.POWER_ID)){
                    return SpireReturn.Continue();
                }
                if(ambushGroup.size()>=ambushArea.getTopLimit()){
                    boolean continueIt = ambushArea.replaceStrategy(ambushGroup,targetCard);
                    if(!continueIt){
                        return SpireReturn.Continue();
                    }
                }
                AbstractDungeon.actionManager.removeFromQueue(targetCard);
                AbstractDungeon.player.hand.removeCard(targetCard);
                targetCard.exhaustOnUseOnce = false;
                targetCard.dontTriggerOnUseCard = false;
                AbstractDungeon.actionManager.addToBottom(new HandCheckAction());
                targetCard.setCostForTurn(targetCard.costForTurn+1);
                addToTop(targetCard);
                ReflectionHacks.privateMethod(AbstractGameAction.class,"tickDuration").invoke(_inst);
                return SpireReturn.Return();
            }

            return SpireReturn.Continue();
        }
    }

    //选择卡牌（修改CardGroup*HAND），统一排在手牌后选中。
    @SpirePatch(clz = CardGroup.class,method = "getHoveredCard")
    public static class HoverAmbushPatch{
        @SpirePostfixPatch
        public static AbstractCard Postfix(AbstractCard ret,CardGroup _inst){
            if(ret==null&&!(AbstractDungeon.screen == AbstractDungeon.CurrentScreen.HAND_SELECT&&AbstractDungeon.isScreenUp)&&_inst == AbstractDungeon.player.hand&&AbstractDungeon.topPanel.potionUi.isHidden&&!AbstractDungeon.topPanel.potionUi.targetMode){
                return ambushGroup.getHoveredCard();
            }
            return ret;
        }
    }

    //更新手牌，统一在手牌后更新。
    @SpirePatch(clz = CardGroup.class,method = "update")
    public static class UpdateAmbushPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst){
            if(AbstractDungeon.player!=null&&_inst == AbstractDungeon.player.hand){
                //若不显示手牌，则不hover
                if(AmbushPanel.ambushed()){
                    for(AbstractCard c:AbstractDungeon.player.hand.group){
                        if(AbstractDungeon.player.hoveredCard == c){
                            AbstractDungeon.player.releaseCard();
                        }
                        c.unhover();
                        c.untip();
                    }
                }
                update();
            }
        }
    }

    //更新手牌LOGIC，统一在手牌后更新。
    @SpirePatch(clz = CardGroup.class,method = "updateHoverLogic")
    public static class UpdateAmbushLogicPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst){
            if(AbstractDungeon.player!=null&&_inst == AbstractDungeon.player.hand){
                updateLogic();
            }
        }
    }

    //GLOW CHECK
    @SpirePatch(clz = CardGroup.class,method = "glowCheck")
    public static class GlowCheckGroup{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst){
            if(AbstractDungeon.player!=null&&_inst == AbstractDungeon.player.hand){
                glowCheck();
            }
        }
    }

    //渲染卡牌
    @SpirePatch(clz = CardGroup.class,method = "renderHand")
    public static class RenderAmbushPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst,SpriteBatch sb, AbstractCard exceptThis){
            if(AbstractDungeon.player!=null&&_inst == AbstractDungeon.player.hand){
                renderHand(sb,exceptThis);
            }
        }
    }

    //渲染卡牌TIP
    @SpirePatch(clz = CardGroup.class,method = "renderTip")
    public static class RenderAmbushTipPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst,SpriteBatch sb){
            if(AbstractDungeon.player!=null&&_inst == AbstractDungeon.player.hand){
                renderTip(sb);
            }
        }
    }

    //重置手牌，用以移动到其他区域时使用
    @SpirePatch(clz = CardGroup.class,method = "resetCardBeforeMoving")
    public static class ResetAmbushPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst, AbstractCard c){
            if(_inst == AbstractDungeon.player.hand){
                resetBeforeRemove(c);
            }
        }
    }

    //设置applyPowers
    @SpirePatch(clz = CardGroup.class,method = "applyPowers")
    public static class ApplyPowersPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst){
            if(_inst == AbstractDungeon.player.hand){
                applyPowers();
            }
        }
    }

    //设置布局
    @SpirePatch(clz = CardGroup.class,method = "refreshHandLayout")
    public static class RefreshAmbushPatch{
        @SpirePostfixPatch
        public static void Postfix(CardGroup _inst){
            if(_inst == AbstractDungeon.player.hand){
                for(AbstractCard c:AbstractDungeon.player.hand.group){
                    if(AmbushPanel.ambushed()){
                        c.target_y = -300F*Settings.scale;
                    }
                }
                refreshAmbushLayout();
            }
        }
    }

    //HoverPush，选中牌后，把其他牌位置挤开
    @SpirePatch(clz = CardGroup.class,method = "hoverCardPush")
    public static class PushAmbushPatch{
        @SpirePrefixPatch
        public static SpireReturn<Void> Prefix(CardGroup _inst,AbstractCard c){
            if(_inst == AbstractDungeon.player.hand){
                if(ambushGroup.group.contains(c)){
                    ambushGroup.hoverCardPush(c);
                    return SpireReturn.Return();
                }
            }
            return SpireReturn.Continue();
        }
    }

    //游击区卡牌的耗能颜色修正
    @SpirePatch(clz = AbstractCard.class,method = "renderEnergy")
    public static class RenderEnergyPatch{
        @SpireInsertPatch(rloc = 31,localvars = {"costColor"})
        public static void Insert(AbstractCard _inst, SpriteBatch sb, @ByRef Color[] costColor){
            if(CardField.inAmbush.get(_inst)){
                if(ambushGroup.contains(_inst)&&!_inst.hasEnoughEnergy()){
                    costColor[0] = new Color(1.0F, 0.3F, 0.3F, 1.0F);
                }
            }
        }
    }

    //这里是RestoreRetainedCardsAction中，额外触发的【保留】部分
    @SpirePatch(clz = RestoreRetainedCardsAction.class,method = "update")
    public static class RetainPatch{
        @SpirePostfixPatch
        public static void Postfix(RestoreRetainedCardsAction _inst){
            //因为它总会return，所里这里会最后执行
            for(AbstractCard c:ambushGroup.group){
                if(c.retain||c.selfRetain){
                    c.onRetained();
                }
            }
        }
    }

    //手牌特殊补充
    @SpirePatch(clz = AbstractPlayer.class,method = "applyStartOfTurnPreDrawCards")
    public static class StartOfTurnPrePatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _inst){
            for(AbstractCard c:ambushGroup.group){
                c.atTurnStartPreDraw();
            }
        }
    }

    //这里是触发回合开始时的补充
    @SpirePatch(clz = AbstractPlayer.class,method = "applyStartOfTurnCards")
    public static class StartOfTurnPatch{
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer _inst){
            for (AbstractCard c : ambushGroup.group) {
                c.atTurnStart();
            }
            AmbushFixPatch.canSetRoundEnd = true;
        }
    }

    //这里是触发游击牌
    @SpirePatch(clz = GameActionManager.class,method = "callEndOfTurnActions")
    public static class EndOfTurnPatch{
        @SpireInsertPatch(rloc = 8)
        public static void Insert(GameActionManager _inst){
            for (AbstractCard c : ambushGroup.group) {
                //回合结束重置耗能
                c.costForTurn = c.cost;
                c.triggerOnEndOfTurnForPlayingCard();
            }
        }
    }

    //切换按钮相关
    @SpirePatch(clz = OverlayMenu.class,method = SpirePatch.CLASS)
    public static class OverlayField{
        public static SpireField<AmbushPanel> ambushPanel = new SpireField<AmbushPanel>(()->new AmbushPanel());
    }

    //按钮更新
    @SpirePatch(clz = OverlayMenu.class,method = "update")
    public static class OverlayUpdatePatch{
        @SpireInsertPatch(rloc = 4)
        public static void Insert(OverlayMenu _inst){
            //目前仅能守林人更新
            if(AbstractDungeon.player.chosenClass!= ClassEnum.Firewatch_CLASS)
                return;
            AmbushPanel panel = OverlayField.ambushPanel.get(_inst);
            panel.updatePositions();
            panel.update();
        }
    }

    //按钮渲染
    @SpirePatch(clz = OverlayMenu.class,method = "render")
    public static class OverlayRenderPatch{
        @SpireInsertPatch(rloc = 6)
        public static void Insert(OverlayMenu _inst, SpriteBatch sb){
            //目前仅能守林人渲染
            if(AbstractDungeon.player.chosenClass!= ClassEnum.Firewatch_CLASS)
                return;
            AmbushPanel panel = OverlayField.ambushPanel.get(_inst);
            panel.render(sb);
        }
    }

    //按钮关闭
    @SpirePatch(clz = OverlayMenu.class,method = "hideCombatPanels")
    public static class OverlayHidePatch{
        @SpirePrefixPatch
        public static void Prefix(OverlayMenu _inst){
            AmbushPanel panel = OverlayField.ambushPanel.get(_inst);
            panel.hide();
        }
    }

    //按钮打开
    @SpirePatch(clz = OverlayMenu.class,method = "showCombatPanels")
    public static class OverlayShowPatch{
        @SpirePrefixPatch
        public static void Prefix(OverlayMenu _inst){
            AmbushPanel panel = OverlayField.ambushPanel.get(_inst);
            panel.show();
        }
    }

    public enum AmbushType{
        Forest,
        Riverside,
        SnowForest,
        SnowTown,
        PlainTown,
        Hill
    }
}
