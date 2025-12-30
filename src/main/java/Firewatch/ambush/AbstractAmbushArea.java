package Firewatch.ambush;

import Firewatch.patch.AmbushPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public abstract class AbstractAmbushArea {
    public AmbushPatch.AmbushType ambushType;

    public AbstractAmbushArea() {

    }

    public int getTopLimit(){
        return 0;
    }


    //替换策略，即当游击区已满时，若有牌预进入，会先调用此函数，返回false表示不允许该牌进入
    public boolean replaceStrategy(CardGroup ambushGroup, AbstractCard card){
        return false;
    }

    public void onPlayCard(AbstractCard card){

    }

    public void onCardBecomeAmbush(AbstractCard card){

    }

    public void onCardLeaveAmbush(AbstractCard card){

    }

    public void onEnterArea(){

    }

    public void onExitArea(){

    }
}
