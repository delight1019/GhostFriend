package GhostFriend.Base.Player;

import GhostFriend.Base.Card.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;

    public List<Card> getCardList() {
        return cardList;
    }

    private List<Card> cardList;

    public String getName() {
        return name;
    }

    public void receiveCard(Card card) {
        this.cardList.add(card);
    }

    public Player(String name) {
        this.name = name;
        this.cardList = new ArrayList<>();
    }

}
