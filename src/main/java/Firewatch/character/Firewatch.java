package Firewatch.character;

import Firewatch.helper.RegisterHelper;
import Firewatch.patch.ClassEnum;
import Firewatch.patch.ColorEnum;
import basemod.abstracts.CustomPlayer;
import basemod.animations.G3DJAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;
import java.util.List;

public class Firewatch extends CustomPlayer {
    public static final String ID = "firewatch:Firewatch";

    private static final CharacterStrings characterStrings;

    public static final String[] orbTexturesFirewatch = new String[]{
            "FirewatchResources/img/orbs/m1.png",
            "FirewatchResources/img/orbs/m2.png",
            "FirewatchResources/img/orbs/m3.png",
            "FirewatchResources/img/orbs/m4.png",
            "FirewatchResources/img/orbs/m5.png",
            "FirewatchResources/img/orbs/mBASE.png",
            "FirewatchResources/img/orbs/m1.png",
            "FirewatchResources/img/orbs/m2.png",
            "FirewatchResources/img/orbs/m3.png",
            "FirewatchResources/img/orbs/m4.png",
            "FirewatchResources/img/orbs/m5.png",
    };

    public static final String VFX = "FirewatchResources/img/orbs/vfx.png";
    public static final String IMG_SHOULDER = "FirewatchResources/img/charSelect/char_shoulder.png";

    public static final float[] LAYER_SPEEDFirewatch = new float[] {
            -32.0F,
            0F,
            -16.0F,
            -32F,
            0F,
            -32.0F,
            0F,
            -16.0F,
            -32F,
            0F,
    };

    private static final int ENERGY_PER_TURN =3;
    private static final int STARTING_HP = 48;
    private static final int MAX_HP = 48;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 0;
    private static final int ASCENSION_MAX_HP_LOSS = 4;

    private String idleName = "Idle";
    private String attackName = "Attack";
    private String skillName = "Skill";
    private String dieName = "Die";

    public Firewatch(String name){
        super(name, ClassEnum.Firewatch_CLASS,orbTexturesFirewatch,VFX,LAYER_SPEEDFirewatch,null,null);

        this.dialogX = this.drawX+ 0.0F* Settings.scale;
        this.dialogY = this.drawY+ 240.0F*Settings.scale;
        this.initializeClass(null,IMG_SHOULDER,IMG_SHOULDER,null,this.getLoadout(),0,-5.0F,260.0F,240.0F,new EnergyManager(ENERGY_PER_TURN));
        this.refreshSkin();
    }

    public void refreshSkin(){
        int type = 0;
        String pathName = "FirewatchResources/img/characters/";
        String fileName = "";
        if(type==0){
            fileName = "default/char_158_milu";
        }
        this.loadAnimation(pathName+fileName+".atlas",pathName+fileName+".json",1.75F);

        if(type==0){
            idleName = "Idle";
            attackName = "Attack";
            skillName = "Skill";
            dieName = "Die";
        }
        this.state.setAnimation(0,idleName,true);
    }

    public static void onAttack(boolean skilled){
        AbstractPlayer pl = AbstractDungeon.player;
        if(pl instanceof Firewatch){
            ((Firewatch)pl).attackAnimation(skilled);
        }
    }

    public void attackAnimation(boolean skilled){
        if(!skilled){
            this.state.setAnimation(0,attackName,false);
        }
        else{
            this.state.setAnimation(0,skillName,false);
        }
        this.state.addAnimation(0,idleName,true,0F);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        return RegisterHelper.startDeck;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        return RegisterHelper.startRelic;
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(characterStrings.NAMES[0],characterStrings.TEXT[0], STARTING_HP,MAX_HP,HAND_SIZE,STARTING_GOLD,5,this,getStartingRelics(),getStartingDeck(),false);
    }

    @Override
    public String getTitle(PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return ColorEnum.Firewatch_COLOR;
    }

    @Override
    public Color getCardRenderColor() {
        return FirewatchMod.tColor.cpy();
    }

    //todo
    @Override
    public AbstractCard getStartCardForEvent() {
        return new Strike_Red();
    }

    @Override
    public Color getCardTrailColor() {
        return Color.SKY.cpy();
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return ASCENSION_MAX_HP_LOSS;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
    }

    @Override
    public void updateOrb(int energyCount) {
        this.energyOrb.updateOrb(energyCount);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return ID;
    }

    @Override
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new Firewatch(this.name);
    }

    @Override
    public String getSpireHeartText() {
        return characterStrings.TEXT[2];
    }

    @Override
    public Color getSlashAttackColor() {
        return FirewatchMod.tColor.cpy();
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        return super.getCutscenePanels();
    }

    @Override
    public Texture getCutsceneBg() {
        return super.getCutsceneBg();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.FIRE,AbstractGameAction.AttackEffect.FIRE,AbstractGameAction.AttackEffect.FIRE,AbstractGameAction.AttackEffect.FIRE,AbstractGameAction.AttackEffect.FIRE,AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getVampireText() {
        return characterStrings.TEXT[1];
    }

    @Override
    public void playDeathAnimation() {
        this.state.setAnimation(0,dieName,false);
    }

    @Override
    public void damage(DamageInfo info) {
        int thisHealth = currentHealth;
        super.damage(info);
        int trueDamage = currentHealth - thisHealth;
        if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && trueDamage > 0 && this.isDead) {
            this.playDeathAnimation();
        }
    }

    static {
        characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    }
}
