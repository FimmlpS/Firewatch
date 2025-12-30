package Firewatch.power.buff;

import Firewatch.card.AbstractFirewatchCard;
import Firewatch.helper.SoundHelper;
import Firewatch.patch.AmbushPatch;
import Firewatch.power.AbstractFirewatchPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class SoundPower extends AbstractFirewatchPower {
    public static final String POWER_ID = "firewatch:SoundPower";

    private boolean localBoolean = false;
    private int localAmount = 0;

    public SoundPower(AbstractCreature owner) {
        super(POWER_ID, owner);
    }

    @Override
    public void onInitialApplication() {
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            SoundHelper.sounded(c,false);
        }
        for(AbstractCard c : AmbushPatch.ambushGroup.group) {
            SoundHelper.sounded(c,false);
        }
    }

    @Override
    public void onRemove() {
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            SoundHelper.sounded(c,false);
        }
        for(AbstractCard c : AmbushPatch.ambushGroup.group) {
            SoundHelper.sounded(c,false);
        }
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        if(localBoolean != SoundHelper.triggerSound || localAmount != SoundHelper.soundTypes.size()){
            this.flashWithoutSound();
            localBoolean = SoundHelper.triggerSound;
            localAmount = SoundHelper.soundTypes.size();
            updateDescription();
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(!SoundHelper.triggerSound && SoundHelper.soundTypes.contains(card.type)){
            SoundHelper.triggerSound();
            //协奏
            if(card instanceof AbstractFirewatchCard){
                ((AbstractFirewatchCard) card).onTogetherSound();
            }
            this.flash();
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void updateDescription() {
        StringBuilder builder = new StringBuilder();
        if(SoundHelper.triggerSound){
            builder.append(powerStrings.DESCRIPTIONS[1]);
        }
        else {
            builder.append(powerStrings.DESCRIPTIONS[0]);
        }
        if(SoundHelper.soundTypes.isEmpty()){
            builder.append(powerStrings.DESCRIPTIONS[2]);
        }
        else {
            if(SoundHelper.soundTypes.contains(AbstractCard.CardType.ATTACK))
                builder.append(powerStrings.DESCRIPTIONS[3]);
            if(SoundHelper.soundTypes.contains(AbstractCard.CardType.SKILL))
                builder.append(powerStrings.DESCRIPTIONS[4]);
            if(SoundHelper.soundTypes.contains(AbstractCard.CardType.POWER))
                builder.append(powerStrings.DESCRIPTIONS[5]);
            if(SoundHelper.soundTypes.contains(AbstractCard.CardType.STATUS))
                builder.append(powerStrings.DESCRIPTIONS[6]);
            if(SoundHelper.soundTypes.contains(AbstractCard.CardType.CURSE))
                builder.append(powerStrings.DESCRIPTIONS[7]);
        }
        this.description = builder.toString();
    }
}
