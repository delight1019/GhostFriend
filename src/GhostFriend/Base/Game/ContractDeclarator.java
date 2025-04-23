package GhostFriend.Base.Game;

import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;
import GhostFriend.Base.Event.GameEventBus;
import GhostFriend.Base.Event.GameEvents;
import GhostFriend.Base.Event.EventType;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;
import GhostFriend.Server.GameParams;

import java.util.List;

public class ContractDeclarator {
    private Rule rule;
    private final List<Player> players;
    private Player currentDeclarer;
    private int declaringIndex;
    private int passNum;
    private GameEventBus eventBus;
    private int currentContractScore;
    private CardSuit currentContractSuit;

    private void initialize() {
        declaringIndex = 0;
        passNum = 0;
    }

    private void setNextPlayer() {
        declaringIndex++;

        if (declaringIndex == players.size()) {
            declaringIndex = 0;
        }
    }

    private void increasePassNum() {
        passNum++;

        if (passNum == players.size()) {
            rule.decreaseMinContractScore();
            passNum = 0;
        }
    }

    public Player getDeclaringPlayer() {
        return players.get(declaringIndex);
    }

    public void passDeclaration(Player player) {
        if (player != currentDeclarer) {
            return;
        }

        eventBus.publish(GameEvents.contractPassed(player));
        moveToNextPlayer();
    }

    public boolean declareContract(Player player, CardSuit suit, CardValue value) {
        if (player != currentDeclarer) {
            return false;
        }

        int score = value.getValue();
        if (score < currentContractScore) {
            return false;
        }

        currentContractScore = score;
        currentContractSuit = suit;
        eventBus.publish(GameEvents.contractDeclared(player, suit, value));

        moveToNextPlayer();
        return true;
    }

    public Boolean isFinished() {
        return ((passNum == players.size() - 1) && (currentDeclarer != null));
    }

    public Player getCurrentDeclarer() {
        return currentDeclarer;
    }

    public String getCurrentContract(String delimiter) {
        if (currentDeclarer == null) {
            return GameParams.NO_CONTRACT;
        }
        else {
            return currentDeclarer.getContract().toString(delimiter);
        }
    }

    public Integer getMinContractScore() {
        if (currentDeclarer == null) {
            return rule.getMinContractScore();
        } else {
            return currentDeclarer.getContract().getScore() + 1;
        }
    }

    private void moveToNextPlayer() {
        int currentIndex = players.indexOf(currentDeclarer);
        currentDeclarer = players.get((currentIndex + 1) % players.size());

        if (currentDeclarer == players.get(0)) {
            eventBus.publish(GameEvents.contractDeclarationFinished());
        } else {
            eventBus.publish(GameEvents.nextContractDeclarer(currentDeclarer));
        }
    }

    public ContractDeclarator(Rule rule, List<Player> players, GameEventBus eventBus) {
        this.rule = rule;
        this.players = players;
        this.currentDeclarer = null;
        this.eventBus = eventBus;
        initialize();
    }
}
