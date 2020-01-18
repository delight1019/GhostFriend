package GhostFriend.Base.Player;

import GhostFriend.Base.Card.Card;

import java.util.List;

public class Player {
    private String name;
    private List<Card> cardList;

    public String getName() {
        return name;
    }

    public Player(String name) {
        this.name = name;
    }

}
