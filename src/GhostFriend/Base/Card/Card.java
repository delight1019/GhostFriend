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

    public Card(CardSuit suit, CardValue value) {
        this.suit = suit;
        this.value = value;
    }
}
