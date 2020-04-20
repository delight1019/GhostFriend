package GhostFriend.Base.Game;

import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;

import java.util.List;

public class ContractDeclarator {
    private Rule rule;
    private final List<Player> players;
    private Player currentDeclarer;
    private int declaringIndex;
    private int passNum;

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
        player.passContractDeclaration();

        increasePassNum();
        setNextPlayer();
    }

    public void declare(Player player, CardSuit giru, Integer score) {
        player.declareContract(giru, score);
        currentDeclarer = player;

        setNextPlayer();
    }

    public Boolean isFinished() {
        return ((passNum == players.size() - 1) && (currentDeclarer != null));
    }

    public ContractDeclarator(Rule rule, List<Player> players) {
        this.rule = rule;
        this.players = players;
        this.currentDeclarer = null;
        initialize();
    }
}
