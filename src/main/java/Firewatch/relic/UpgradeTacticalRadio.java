package Firewatch.relic;

import Firewatch.action.RadioAction;
import Firewatch.action.TacticalAction;
import Firewatch.ambush.AmbushPlainTown;
import Firewatch.helper.RightHitbox;
import Firewatch.helper.StringHelper;
import Firewatch.patch.AmbushPatch;
import Firewatch.power.buff.GoodEquipmentPower;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.HitboxListener;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.lang.reflect.Type;

public class UpgradeTacticalRadio extends CustomRelic implements CustomSavable<Integer>, HitboxListener {
    public static final String ID = "firewatch:UpgradeTacticalRadio";

    public int maxCounter = 8;

    public UpgradeTacticalRadio() {
        super(ID, ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,false)),ImageMaster.loadImage(StringHelper.getRelicIMGPATH(ID,true)), RelicTier.BOSS, LandingSound.SOLID);
        RightHitbox rhb =  new RightHitbox(-10000F,-10000F,PAD_X, PAD_X);
        rhb.setListener(this);
        this.hb = rhb;
        this.counter = 0;
    }

    @Override
    public void onShuffle() {
        if(AmbushPatch.ambushArea instanceof AmbushPlainTown){
            ((AmbushPlainTown) AmbushPatch.ambushArea).shuffleIncrease();
        }
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        if(AmbushPatch.ambushArea instanceof AmbushPlainTown){
            ((AmbushPlainTown) AmbushPatch.ambushArea).monsterDie();
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(info.type == DamageInfo.DamageType.NORMAL){
            addToTop(new TacticalAction(this,target,1));
        }
    }

    public void increaseCounter(int amount){
        this.flash();
        this.counter+=amount;
        if(counter>maxCounter){
            counter = maxCounter;
        }
    }

    @Override
    public void onVictory() {
        this.flash();
        if(maxCounter>8){
            this.maxCounter -= 3;
            if(maxCounter<8){
                maxCounter = 8;
            }
        }

        this.counter -= 3;
        if(counter<0)
            counter = 0;
    }

    public void checkRadioTrigger(boolean increaseMax){
        if(counter>=maxCounter){
            counter = 0;
            if(increaseMax)
                maxCounter += 4;
            if(maxCounter>16){
                maxCounter = 16;
            }
            this.flash();
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player,this));
            addToBot(new RadioAction(6,3));
        }
    }

    @Override
    public boolean canSpawn() {
        return AbstractDungeon.player!=null && AbstractDungeon.player.hasRelic(TacticalRadio.ID);
    }

    @Override
    public void obtain() {
        updateDescription(AbstractDungeon.player.chosenClass);
        if(AbstractDungeon.player.hasRelic(TacticalRadio.ID)){
            for(int i =0;i<AbstractDungeon.player.relics.size();i++){
                AbstractRelic tmp = AbstractDungeon.player.relics.get(i);
                if(tmp instanceof TacticalRadio){
                    this.counter = tmp.counter;
                    this.instantObtain(AbstractDungeon.player,i,true);
                    break;
                }
            }
        }
        else{
            this.counter = 0;
            super.obtain();
        }
    }

    @Override
    public void renderCounter(SpriteBatch sb, boolean inTopPanel) {
        super.renderCounter(sb, inTopPanel);
        if (inTopPanel) {
            float offsetX = ReflectionHacks.getPrivateStatic(AbstractRelic.class, "offsetX");
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.maxCounter), offsetX + this.currentX + 30.0F * Settings.scale, this.currentY + 17.0F * Settings.scale, Color.GREEN);
        } else {
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(this.maxCounter), this.currentX + 30.0F * Settings.scale, this.currentY + 17.0F * Settings.scale, Color.GREEN);
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onLoad(Integer integer) {
        this.maxCounter = integer;
    }

    @Override
    public Integer onSave() {
        return maxCounter;
    }

    @Override
    public Type savedType() {
        return Integer.class;
    }

    @Override
    public void hoverStarted(Hitbox hitbox) {

    }

    @Override
    public void startClicking(Hitbox hitbox) {

    }

    @Override
    public void clicked(Hitbox hitbox) {
        if(hitbox instanceof RightHitbox) {
            RightHitbox rhb = (RightHitbox) hitbox;
            if(rhb.rightClicked){
                checkRadioTrigger(true);
            }
        }
    }
}

