package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.CardSuit;

public class Pledge {
    private CardSuit giru;
    private Integer score;

    public Boolean makePledge(CardSuit suit, Integer score) {
        if ((this.score >= score) || (suit == CardSuit.JOKER)) {
            return false;
        }

        this.giru = suit;
        this.score = score;
        return true;
    }

    public Pledge() {
        this.giru = CardSuit.SPADE;
        this.score = 13;
    }
}
