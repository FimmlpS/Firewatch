package Firewatch.action;

import Firewatch.power.buff.MoonTalkNextPower;
import Firewatch.power.buff.MoonTalkPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.CollectPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class MoonTalkAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private boolean upgraded;
    private AbstractPlayer p;
    private int energyOnUse;

    public MoonTalkAction(AbstractPlayer p, boolean freeToPlayOnce, int energyOnUse, boolean upgraded) {
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.upgraded = upgraded;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        int trueEffect01 = (upgraded?9:12)*effect-6;
        this.addToBot(new ApplyPowerAction(this.p, this.p, new MoonTalkNextPower(this.p, trueEffect01), trueEffect01));

        if (effect > 0) {
            int trueEffect02 = 6*effect;
            if(upgraded)
                this.addToBot(new ApplyPowerAction(this.p, this.p, new MoonTalkPower(this.p, trueEffect02), trueEffect02));
            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}

