package Firewatch.card;

import Firewatch.action.FirewatchAttackAction;
import Firewatch.helper.StringHelper;
import Firewatch.patch.ColorEnum;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractFirewatchCard extends CustomCard {
    public static AbstractGameAction.AttackEffect attackEffect = AbstractGameAction.AttackEffect.SLASH_HORIZONTAL;
    public static AbstractGameAction.AttackEffect skillEffect = AbstractGameAction.AttackEffect.FIRE;

    public AbstractFirewatchCard(String id, String name, int cost, String rawDescription, AbstractCard.CardType type, CardRarity rarity, CardTarget target) {
        super(id, name, StringHelper.getCardIMGPath(id, type), cost, rawDescription, type, ColorEnum.Firewatch_COLOR, rarity, target);
    }

    public AbstractFirewatchCard(String id, String name, int cost, String rawDescription, AbstractCard.CardType type, CardColor color, CardRarity rarity, CardTarget target) {
        super(id, name, StringHelper.getCardIMGPath(id, type), cost, rawDescription, type, color, rarity, target);
    }

    protected void attackOnce(){
        addToBot(new FirewatchAttackAction(false));
    }
}

