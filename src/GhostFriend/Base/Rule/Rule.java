package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;

public class Rule {
    private Card Mighty;
    private Card JokerCall;
    private Contract contract;

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

    private Integer getScore(Card card) {
        if (card.IsScoreCard()) {
            return 1;
        } else {
            return 0;
        }
    }

    public static Integer getNumOfCardsPerPerson() {
        return 10; // To-Do: I'm only considering the case that 5 players come in
    }

    public Rule() {
        this.Mighty = new Card(CardSuit.SPADE, CardValue.ACE);
        this.JokerCall = new Card(CardSuit.CLUB, CardValue.THREE);
        this.contract = new Contract();
    }

}
