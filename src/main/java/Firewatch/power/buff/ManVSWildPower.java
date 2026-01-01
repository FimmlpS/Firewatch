package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import Firewatch.power.debuff.NoTerraPower;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ManVSWildPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:ManVSWildPower";

    public boolean triggered = false;

    public ManVSWildPower(AbstractCreature owner) {
        super(POWER_ID, owner);
        this.amount = 1;
    }

    @Override
    public void atStartOfTurn() {
        if(triggered){
            this.flashWithoutSound();
            if(owner.maxHealth>1){
                owner.decreaseMaxHealth(1);
            }
        }
    }

    @Override
    public void onSpecificTrigger() {
        this.flash();
        this.amount = 0;
        this.triggered = true;
        owner.currentHealth = 0;
        owner.heal((int) (0.2F * owner.maxHealth));
        addToBot(new ApplyPowerAction(owner,owner,new NoTerraPower(owner,99),99));
    }

    //小patch一下
    @SpirePatch(clz = AbstractPlayer.class, method = "damage")
    public static class DamagePatch {
        @SpireInsertPatch(rloc = 128)
        public static SpireReturn<Void> Insert(AbstractPlayer _inst, DamageInfo info) {
            AbstractPower hj = _inst.getPower(ManVSWildPower.POWER_ID);
            if (hj instanceof ManVSWildPower && !((ManVSWildPower) hj).triggered) {
                hj.onSpecificTrigger();
                return SpireReturn.Return();
            }
            return SpireReturn.Continue();
        }
    }
}

