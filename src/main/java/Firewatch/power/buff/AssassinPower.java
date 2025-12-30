package Firewatch.power.buff;

import Firewatch.power.AbstractFirewatchPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AssassinPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:AssassinPower";

    public AssassinPower(AbstractCreature owner) {
        super(POWER_ID,owner);
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        if(type == DamageInfo.DamageType.NORMAL && AssassinPatch.currentMonster != null && AssassinPatch.currentMonster.getIntentBaseDmg()<=0){
            return 1.55F * damage;
        }
        return super.atDamageFinalGive(damage, type);
    }

    @SpirePatch(clz = AbstractCard.class,method = "calculateCardDamage",paramtypez = {AbstractMonster.class})
    public static class AssassinPatch{
        public static AbstractMonster currentMonster = null;
        @SpirePrefixPatch
        public static void Prefix(AbstractCard _inst,AbstractMonster mo){
            currentMonster = mo;
        }
    }
}


