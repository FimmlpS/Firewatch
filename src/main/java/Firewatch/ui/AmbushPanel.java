package Firewatch.ui;

import Firewatch.character.Firewatch;
import Firewatch.patch.AmbushPatch;
import Firewatch.patch.InputPatch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;

public class AmbushPanel extends AbstractPanel {
    private static final UIStrings uiStrings;
    private Hitbox tipHitbox;
    private Texture gainEnergyImg;
    private float energyVfxAngle;
    private float energyVfxScale;
    private Color energyVfxColor;
    public static float fontScale;
    public static float energyVfxTimer;
    private static final Color ENERGY_TEXT_COLOR;
    private static final Color CURRENT_AREA_COLOR;
    public static int totalCount;
    public AmbushOrb ambushOrb;

    private static boolean isAmbush = false;

    public static final float CEX = 218F* Settings.xScale;
    public static final float CEY = 390F*Settings.yScale;

    public static boolean ambushed(){
        if(!(AbstractDungeon.player instanceof Firewatch)){
            return false;
        }
        return isAmbush;
    }

    public static void reset(){
        if(isAmbush){
            isAmbush = false;
            if(AbstractDungeon.player!=null && AbstractDungeon.getCurrMapNode() != null && AbstractDungeon.getCurrRoom() != null){
                AbstractDungeon.player.hand.refreshHandLayout();
            }
        }
    }

    public AmbushPanel(){
        super(CEX,CEY,-460.0F * Settings.scale, 400.0F * Settings.yScale,12.0F * Settings.scale, -12.0F * Settings.scale, (Texture)null, true);
        //0.9 F
        this.tipHitbox = new Hitbox(0F,0F,108F*Settings.scale,108F*Settings.scale);
        this.energyVfxAngle = 0.0F;
        this.energyVfxScale = Settings.scale;
        this.energyVfxColor = Color.WHITE.cpy();
        this.gainEnergyImg = AbstractDungeon.player.getEnergyImage();
        //todo 图标待替换
        this.ambushOrb = new AmbushOrb(Firewatch.orbTexturesFirewatch,Firewatch.VFX,Firewatch.LAYER_SPEEDFirewatch);
    }

    public void update(){
        totalCount = AmbushPatch.ambushGroup.size();
        ambushOrb.updateOrb(totalCount);
        this.updateVfx();
        if(fontScale!=1f){
            fontScale = MathHelper.scaleLerpSnap(fontScale,1F);
        }
        this.tipHitbox.update();
        boolean shouldChange = false;
        if(this.tipHitbox.hovered&&!AbstractDungeon.isScreenUp){
            AbstractDungeon.overlayMenu.hoveredTip = true;

            if(InputHelper.justClickedLeft){
                tipHitbox.clickStarted = true;
            }
            else if(this.tipHitbox.clicked){
                this.tipHitbox.clicked = false;
                shouldChange = true;
            }
        }
        if(InputPatch.changeArea.isJustPressed()){
            shouldChange = true;
        }
        if(shouldChange){
            changeArea();
        }
    }

    private void changeArea(){
        isAmbush = !isAmbush;
        fontScale = 2.0F;
        energyVfxTimer = 2.0F;
        if(AbstractDungeon.player!=null){
            AbstractDungeon.player.hand.refreshHandLayout();
        }
    }

    private void updateVfx(){
        if (energyVfxTimer != 0.0F) {
            this.energyVfxColor.a = Interpolation.exp10In.apply(0.5F, 0.0F, 1.0F - energyVfxTimer / 2.0F);
            this.energyVfxAngle += Gdx.graphics.getDeltaTime() * -30.0F;
            this.energyVfxScale = Settings.scale * Interpolation.exp10In.apply(1.0F, 0.1F, 1.0F - energyVfxTimer / 2.0F);
            energyVfxTimer -= Gdx.graphics.getDeltaTime();
            if (energyVfxTimer < 0.0F) {
                energyVfxTimer = 0.0F;
                this.energyVfxColor.a = 0.0F;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        this.tipHitbox.move(this.current_x,this.current_y);
        this.renderOrb(sb);
        this.renderVfx(sb);
        String energyMsg = String.valueOf(totalCount);
        String areaMsg = uiStrings.TEXT[3];
        //额外描述用
        int areaIndex = -1;
        if(isAmbush){
            switch (AmbushPatch.ambushType){
                case Forest:
                    areaMsg = uiStrings.TEXT[4];
                    areaIndex = 0;
                    break;
                case Riverside:
                    areaMsg = uiStrings.TEXT[5];
                    areaIndex = 1;
                    break;
                case SnowForest:
                    areaMsg = uiStrings.TEXT[6];
                    areaIndex = 2;
                    break;
                case SnowTown:
                    areaMsg = uiStrings.TEXT[7];
                    areaIndex = 3;
                    break;
                case PlainTown:
                    areaMsg = uiStrings.TEXT[8];
                    areaIndex = 4;
                    break;
            }
        }
        FontHelper.cardTitleFont.getData().setScale(fontScale);
        FontHelper.renderFontCentered(sb,AbstractDungeon.player.getEnergyNumFont(),energyMsg,this.current_x,this.current_y,ENERGY_TEXT_COLOR);
        FontHelper.renderFontCentered(sb,FontHelper.cardTitleFont,areaMsg,this.current_x,this.current_y+80F*Settings.scale,CURRENT_AREA_COLOR);
        this.tipHitbox.render(sb);
        if (this.tipHitbox.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(70.0F * Settings.scale, 580.0F * Settings.scale, uiStrings.TEXT[0] + "("+ InputPatch.changeArea.getKeyString() + ")" , uiStrings.TEXT[1] + (areaIndex>=0?uiStrings.EXTRA_TEXT[areaIndex]:""));
        }
    }

    private void renderOrb(SpriteBatch sb){
        if(totalCount==0){
            ambushOrb.renderOrb(sb,false,this.current_x,this.current_y);
        }
        else {
            ambushOrb.renderOrb(sb,true,this.current_x,this.current_y);
        }
    }

    private void renderVfx(SpriteBatch sb){
        if(energyVfxTimer!=0F){
            sb.setBlendFunction(770, 1);
            sb.setColor(this.energyVfxColor);
            sb.draw(this.gainEnergyImg, this.current_x - 128.0F, this.current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.energyVfxScale, this.energyVfxScale, -this.energyVfxAngle + 50.0F, 0, 0, 256, 256, true, false);
            sb.draw(this.gainEnergyImg, this.current_x - 128.0F, this.current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.energyVfxScale, this.energyVfxScale, this.energyVfxAngle, 0, 0, 256, 256, false, false);
            sb.setBlendFunction(770, 771);
        }
    }

    public static int getCurrentEnergy(){
        return AbstractDungeon.player==null?0:totalCount;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("firewatch:AmbushPanel");
        ENERGY_TEXT_COLOR = Color.GOLD.cpy();
        CURRENT_AREA_COLOR = Color.LIGHT_GRAY.cpy();
        fontScale = 1.0F;
        totalCount = 0;
        energyVfxTimer = 0.0F;
    }
}
