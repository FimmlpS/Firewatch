package Firewatch.action;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class ProtectAction extends AbstractGameAction {
    public ProtectAction(AbstractCreature target, DamageInfo info, int maxForever){
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.startDuration = Settings.ACTION_DUR_XFAST;
        this.duration = this.startDuration;
        this.maxForever = maxForever;
    }

    @Override
    public void update() {
        if (shouldCancelAction()) {
            this.isDone = true;
        } else {
            this.tickDuration();
            if (this.isDone) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HEAVY, false));
                this.target.damage(this.info);
                int actualLose = target.lastDamageTaken;
                int actualGain = actualLose - maxForever;
                if(actualGain>0){
                    if (!target.hasPower("Artifact")) {
                        this.addToTop(new ApplyPowerAction(target, info.owner, new GainStrengthPower(target, actualGain), actualGain));
                    }
                }
                if (actualLose > 0) {
                    this.addToTop(new ApplyPowerAction(target, info.owner, new StrengthPower(target, -actualLose), -actualLose));
                }

                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
            }
        }
    }

    private DamageInfo info;
    int maxForever;
}

