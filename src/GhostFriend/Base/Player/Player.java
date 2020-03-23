package GhostFriend.Base.Player;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Rule.Contract;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;

    public Contract getContract() {
        return contract;
    }

    private Contract contract;

    public List<Card> getCardList() {
        return cardList;
    }

    public String getCardListInfo(String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();

        for (Card card : cardList) {
            stringBuilder.append(card.toString());
            stringBuilder.append(delimiter);
        }

        return stringBuilder.toString();
    }

    private List<Card> cardList;

    public String getName() {
        return name;
    }

    public void receiveCard(Card card) {
        this.cardList.add(card);
    }

    public void discardCard(Card card) {
        for (Card checkingCard : this.cardList) {
            if (checkingCard.equals(card)) {
                card = checkingCard;
                break;
            }
        }

        this.cardList.remove(card);
    }

    public Boolean hasCard(Card card) {
        for (Card checkingCard : this.cardList) {
            if (checkingCard.equals(card)) {
                return true;
            }
        }

        return false;
    }

    public void declareContract(CardSuit suit, Integer score) {
        this.contract.declare(suit, score);
    }

    public void declareContract(Contract contract) {
        this.contract.declare(contract);
    }

    public Player(String name) {
        this.name = name;
        this.cardList = new ArrayList<>();
        this.contract = new Contract();
    }

}
