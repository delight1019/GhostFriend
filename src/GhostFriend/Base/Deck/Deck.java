package GhostFriend.Base.Deck;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    private List<Card> cardList;

    private List<Card> CreateDeck() {
        List<Card> cardList = new ArrayList<>();

        for (CardSuit suit: CardSuit.values()) {
            for (CardValue value: CardValue.values()) {
                if (Card.IsValidCard(suit, value)) {
                    Card card = new Card(suit, value);
                    cardList.add(card);
                }
            }
        }

        return cardList;
    }

    public Deck() {
        this.cardList = CreateDeck();
    }
}
