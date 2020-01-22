package GhostFriend.Base.Deck;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public Card drawCard() {
        Random random = new Random();
        int selectedIndex = random.nextInt(cardList.size());
        Card DrawnCard = cardList.get(selectedIndex);
        cardList.remove(selectedIndex);

        return DrawnCard;
    }

    public Deck() {
        this.cardList = CreateDeck();
    }
}
