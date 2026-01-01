package Firewatch.patch;

import Firewatch.card.power.OurHome;
import Firewatch.character.Firewatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;

public class OurHomePatch {

    //get
    @SpirePatch(clz = CombatRewardScreen.class,method = "setupItemReward")
    public static class RewardPatch{
        @SpirePostfixPatch
        public static void Postfix(CombatRewardScreen _inst){
            if(AbstractDungeon.actNum>1)
                return;
            if(!(AbstractDungeon.player instanceof Firewatch))
                return;
            if(AbstractDungeon.player.masterDeck.findCardById(OurHome.ID)!=null)
                return;
            RewardItem item = new RewardItem();
            item.cards.clear();
            item.cards.add(new OurHome());
            _inst.rewards.add(item);
            _inst.positionRewards();
        }
    }
}
