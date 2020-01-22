package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.CardSuit;

public class Contract {
    public CardSuit getGiru() {
        return giru;
    }

    private CardSuit giru;

    public Integer getScore() {
        return score;
    }

    private Integer score;

    public Boolean getDeclared() {
        return isDeclared;
    }

    private Boolean isDeclared;

    public void Initialize() {
        this.giru = CardSuit.JOKER;
        this.score = -1;
        this.isDeclared = false;
    }

    public void declare(CardSuit suit, Integer score) {
        this.giru = suit;
        this.score = score;
        this.isDeclared = true;
    }

    public void declare(Contract contract) {
        this.giru = contract.giru;
        this.score = contract.score;
        this.isDeclared = true;
    }

    public static Boolean IsValidGiru(Contract contract) {
        return (contract.giru != CardSuit.JOKER);
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
        Initialize();
    }
}
