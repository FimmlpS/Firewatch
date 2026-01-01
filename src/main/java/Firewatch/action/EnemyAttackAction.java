package Firewatch.action;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EnemyAttackAction extends AbstractGameAction {
    public EnemyAttackAction(AbstractMonster source){
        this.source = source;
    }

    @Override
    public void update() {
        if(!(source instanceof AbstractMonster)){
            this.isDone = true;
            return;
        }
        int baseDmg = ((AbstractMonster)source).getIntentBaseDmg();
        boolean multi = ReflectionHacks.getPrivate(source,AbstractMonster.class,"isMultiDmg");
        int times = 1;
        if(multi){
            times = ReflectionHacks.getPrivate(source,AbstractMonster.class,"intentMultiAmt");
        }
        DamageInfo info = new DamageInfo(source, baseDmg, DamageInfo.DamageType.NORMAL);
        info.applyPowers(source, AbstractDungeon.player);
        source.useFastAttackAnimation();
        for(int i =0; i<times; i++)
            addToTop(new DamageAction(AbstractDungeon.player,info));

        this.isDone = true;
    }
}
