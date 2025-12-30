package Firewatch.action;

import Firewatch.ambush.AbstractAmbushArea;
import Firewatch.patch.AmbushPatch;
import Firewatch.power.debuff.NoTerraPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class PlantAction extends AbstractGameAction {
    public static int leaveCardCount = 0;

    public PlantAction(AbstractCard except, AbstractCreature target, int damage, DamageInfo.DamageType type) {
        this.target = target;
        this.amount = damage;
        this.damageType = type;
        this.except = except;
    }

    AbstractCard except;

    @Override
    public void update() {
        if(AbstractDungeon.player.hasPower(NoTerraPower.POWER_ID)){
            this.isDone = true;
            return;
        }

        AbstractPlayer p = AbstractDungeon.player;
        leaveCardCount = 0;
        for(AbstractCard c : new ArrayList<>(p.hand.group)) {
            if(c==except)
                continue;
            if(AmbushPatch.ambushGroup.size()>=AmbushPatch.ambushArea.getTopLimit()){
                boolean continueIt = AmbushPatch.ambushArea.replaceStrategy(AmbushPatch.ambushGroup,c);
                if(!continueIt){
                    continue;
                }
            }
            AbstractDungeon.actionManager.removeFromQueue(c);
            p.hand.removeCard(c);
            AmbushPatch.addToTop(c);
        }

        for(int i =0;i<leaveCardCount;i++){
            addToTop(new DamageAction(target,new DamageInfo(p,amount,damageType),AttackEffect.SLASH_VERTICAL));
        }

        this.isDone = true;
    }
}
