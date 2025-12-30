package Firewatch.helper;

import Firewatch.modifier.SoundModifier;
import Firewatch.patch.SoundPatch;
import Firewatch.power.buff.SoundPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class SoundHelper {
    public static boolean triggerSound = false;
    public static ArrayList<AbstractCard.CardType> soundTypes = new ArrayList<>();

    public static void reset(){
        triggerSound = false;
        soundTypes.clear();
    }

    public static boolean isSoundCard(AbstractCard card) {
        return CardModifierManager.hasModifier(card, SoundModifier.ID);
    }

    public static void triggerSound() {
        triggerSound = true;
    }

    public static void sounded(AbstractCard c, boolean notHand){
        if(notHand || triggerSound || !soundTypes.contains(c.type)){
            if(SoundPatch.AbstractCardPatch.sounded.get(c)){
                SoundPatch.AbstractCardPatch.sounded.set(c,false);
                c.updateCost(1);
            }
        }
        else {
            if(!SoundPatch.AbstractCardPatch.sounded.get(c)){
                SoundPatch.AbstractCardPatch.sounded.set(c,true);
                c.updateCost(-1);
            }
        }
    }

    public static void markSound(AbstractCard.CardType cardType){
        if(!soundTypes.contains(cardType)){
            soundTypes.add(cardType);
        }
        if(!triggerSound){
            if(AbstractDungeon.player!=null && !AbstractDungeon.player.hasPower(SoundPower.POWER_ID)){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new SoundPower(AbstractDungeon.player)));
            }
        }
    }
}
