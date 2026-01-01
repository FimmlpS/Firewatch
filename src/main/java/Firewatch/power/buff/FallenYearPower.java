package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

public class FallenYearPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:FallenYearPower";

    int baseAmount = 2;

    private final Color greenColor = new Color(0.0F, 1.0F, 0.0F, 1.0F);
    private static final float DISTANCE = 17F * Settings.scale;

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (!this.isTurnBased) {
            this.greenColor.a = c.a;
            c = this.greenColor;
        }
        FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.baseAmount), x, y+DISTANCE, this.fontScale, c);
    }

    public FallenYearPower(AbstractCreature owner, int amount) {
        super(POWER_ID,owner,amount);
        this.baseAmount = 2;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        baseAmount+=2;
    }

    @Override
    public void onSpecificTrigger() {
        this.flashWithoutSound();
        addToBot(new DamageRandomEnemyAction(new DamageInfo(owner,baseAmount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.baseAmount += amount;
        this.updateDescription();
    }

    @Override
    public void updateDescription() {
        description = powerStrings.DESCRIPTIONS[0] + baseAmount + powerStrings.DESCRIPTIONS[1] + amount + powerStrings.DESCRIPTIONS[2];
    }
}


