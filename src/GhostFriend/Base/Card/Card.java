package GhostFriend.Base.Card;

public class Card {
    private CardSuit suit;
    private CardValue value;

    public CardSuit getCardSuit() {
        return this.suit;
    }
    public CardValue getCardValue() {
        return this.value;
    }

    public Boolean IsScoreCard() {
        if ((this.getCardValue() == CardValue.ACE) ||
                (this.getCardValue() == CardValue.TEN) ||
                (this.getCardValue() == CardValue.JACK) ||
                (this.getCardValue() == CardValue.QUEEN) ||
                (this.getCardValue() == CardValue.KING)) {
            return true;
        } else {
            return false;
        }

    }

    public static Boolean IsValidCard(CardSuit suit, CardValue value) {
        if ((suit == CardSuit.JOKER) && (value != CardValue.JOKER)) {
            return false;
        }
        else if ((suit != CardSuit.JOKER) && (value == CardValue.JOKER)) {
            return false;
        }
        else {
            return true;
        }
    }

    public Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }
}
