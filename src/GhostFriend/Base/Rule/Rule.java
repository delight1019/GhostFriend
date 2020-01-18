package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;

public class Rule {
    private Card Mighty;
    private Card JokerCall;
    private CardSuit Giru;

    public Card getMighty() {
        return Mighty;
    }

    public void setMighty(Card mighty) {
        Mighty = mighty;
    }

    public Card getJokerCall() {
        return JokerCall;
    }

    public void setJokerCall(Card jokerCall) {
        JokerCall = jokerCall;
    }

    public CardSuit getGiru() {
        return Giru;
    }

    public void setGiru(CardSuit giru) {
        Giru = giru;
    }

    private Integer getScore(Card card) {
        if (card.IsScoreCard()) {
            return 1;
        } else {
            return 0;
        }
    }

    public Rule() {
        this.Mighty = new Card(CardSuit.SPADE, CardValue.ACE);
        this.JokerCall = new Card(CardSuit.CLUB, CardValue.THREE);
        this.Giru = CardSuit.SPADE;
    }

}
