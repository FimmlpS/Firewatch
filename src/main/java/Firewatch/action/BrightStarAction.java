package Firewatch.action;

import Firewatch.card.skill.BrightStar;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;

public class BrightStarAction extends AbstractGameAction {
    public BrightStarAction(BrightStar star, boolean increaseCost) {
        this.star = star;
        this.increaseCost = increaseCost;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> cards = new ArrayList<>(AbstractDungeon.player.masterDeck.group);
        for (AbstractCard c : cards) {
            if(c instanceof BrightStar && c.uuid == star.uuid) {
                if(increaseCost){
                    c.cost++;
                    c.costForTurn = c.cost;
                }
                if(EnergyPanel.getCurrentEnergy()==0){
                    AbstractDungeon.player.masterDeck.removeCard(c);
                }
                break;
            }
        }

        this.isDone = true;
    }

    BrightStar star;
    boolean increaseCost;
}
