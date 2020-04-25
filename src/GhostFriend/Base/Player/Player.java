package GhostFriend.Base.Player;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Rule.Contract;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private DealMissStatus dealMissStatus;
    private ContractDeclarationStatus contractDeclarationStatus;

    public Contract getContract() {
        return contract;
    }

    private Contract contract;

    public List<Card> getCardList() {
        return cardList;
    }

    public String getCardListInfo(String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < cardList.size(); i++) {
            stringBuilder.append(cardList.get(i).toString());

            if (i != cardList.size() - 1) {
                stringBuilder.append(delimiter);
            }
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
        this.contractDeclarationStatus = ContractDeclarationStatus.DECLARED;
    }

    public void declareContract(Contract contract) {
        this.contract.declare(contract);
        this.contractDeclarationStatus = ContractDeclarationStatus.DECLARED;
    }

    public void passContractDeclaration() {
        this.contract.initialize();
        this.contractDeclarationStatus = ContractDeclarationStatus.PASSED;
    }

    public void checkDealMiss(Boolean isDealMissDeclared) {
        if (isDealMissDeclared) {
            this.dealMissStatus = DealMissStatus.MISS;
        } else {
            this.dealMissStatus = DealMissStatus.OK;
        }
    }

    public void clearGameInfo() {
        this.cardList.clear();
        this.dealMissStatus = DealMissStatus.CHECKING;
    }

    public DealMissStatus getDealMissStatus() {
        return this.dealMissStatus;
    }

    public Player(String name) {
        this.name = name;
        this.dealMissStatus = DealMissStatus.CHECKING;
        this.contractDeclarationStatus = ContractDeclarationStatus.NOT_DETERMINED;
        this.cardList = new ArrayList<>();
        this.contract = new Contract();
    }

}
