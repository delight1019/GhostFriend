package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.CardSuit;

public class Contract {
    private CardSuit giru;
    private Integer score;
    private Boolean isDeclared;

    public void set(CardSuit suit, Integer score) {
        this.giru = suit;
        this.score = score;
        this.isDeclared = true;
    }

    @Override
    public String toString() {
        if (this.isDeclared) {
            return this.giru.toString() + " " + this.score.toString();
        } else {
            return "선언되지 않았습니다.";
        }
    }

    public Contract() {
        this.giru = CardSuit.JOKER;
        this.score = -1;
        this.isDeclared = false;
    }
}
