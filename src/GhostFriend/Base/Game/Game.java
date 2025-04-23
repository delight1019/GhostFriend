package GhostFriend.Base.Game;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;
import GhostFriend.Base.Deck.Deck;
import GhostFriend.Base.Event.GameEventBus;
import GhostFriend.Base.Event.GameEvents;
import GhostFriend.Base.Event.EventType;
import GhostFriend.Base.Player.DealMissStatus;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;
import GhostFriend.Server.GameParams;
import GhostFriend.Server.MainServer;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int PLAYER_NUMBER = 2;
    private static final int DECLARER_ADDITIONAL_CARD_NUM = 3;

    private Rule rule;
    private Deck deck;
    private final List<Player> players;
    private Player declarer;
    private Player friend;
    private int numOfPlayers;
    private ContractDeclarator contractDeclarator;
    private GameController gameController;
    private GameEventBus eventBus;

    public Game() {
        rule = new Rule();
        deck = new Deck();
        players = new ArrayList<>();
        declarer = new Player("Declarer");
        friend = null;
        this.numOfPlayers = 0;
        this.eventBus = new GameEventBus();
    }

    public GameEventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(GameEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public boolean isAllPlayersEntered() {
        synchronized (this) {
            return (players.size() == PLAYER_NUMBER);
        }
    }

    public String getPlayersInfo(String delimiter) {
        StringBuilder playersInfo = new StringBuilder();

        synchronized (players) {
            for (Player player : players) {
                playersInfo.append(player.getName());
                playersInfo.append(delimiter);
            }
        }

        return playersInfo.toString();
    }

    public void clear() {
        deck = new Deck();

        synchronized (players) {
            for (Player player : players) {
                player.clearGameInfo();
            }
        }
    }

    public Boolean isDealMiss(Player player) {
        return rule.isDealMiss(player.getCardList());
    }

    public DealMissStatus isDealMissDeclared() {
        synchronized (players) {
            for (Player player : players) {
                if (player.getDealMissStatus() == DealMissStatus.CHECKING) {
                    return DealMissStatus.CHECKING;
                }
                else if (player.getDealMissStatus() == DealMissStatus.MISS) {
                    return DealMissStatus.MISS;
                }
            }
        }

        return DealMissStatus.OK;
    }

    public void addPlayer(Player player) {
        if (numOfPlayers < PLAYER_NUMBER) {
            players.add(player);
            numOfPlayers++;
            eventBus.publish(GameEvents.playerJoined(player));
        }
    }

    public void removePlayer(Player player) {
        synchronized (players) {
            players.remove(player);
        }
    }

    public void distributeCards() {
        deck.shuffle();
        for (Player player : players) {
            List<Card> playerCards = deck.drawCards(13);
            player.setCards(playerCards);
        }
        eventBus.publish(GameEvents.gamePhaseChanged(EventType.DISTRIBUTE_CARD.getValue()));
    }

    public void startContractDeclaration() {
        contractDeclarator = new ContractDeclarator(rule, players);
        askContractDeclaring();
        eventBus.publish(GameEvents.gamePhaseChanged(EventType.DECLARE_CONTRACT.getValue()));
    }

    public void passContractDeclaration(Player player) {
        contractDeclarator.passDeclaration(player);

        if (contractDeclarator.isFinished()) {
            confirmDeclarer(contractDeclarator.getCurrentDeclarer());
            confirmDeclarerCards();
        } else {
            askContractDeclaring();
        }
    }

    private void askContractDeclaring() {
        synchronized (players) {
            for (Player player : players) {
                if (player == contractDeclarator.getDeclaringPlayer()) {
                    MainServer.getInstance().broadcast(player, GameParams.ASK_CONTRACT,
                                                       contractDeclarator.getCurrentContract(GameParams.DATA_DELIMITER) + GameParams.DATA_DELIMITER + contractDeclarator.getMinContractScore().toString());
                }
                else {
                    MainServer.getInstance().broadcast(player, GameParams.OTHER_PLAYER_ASKING_CONTRACT,
                                                    contractDeclarator.getCurrentContract(GameParams.DATA_DELIMITER) + GameParams.DATA_DELIMITER +
                                                         contractDeclarator.getMinContractScore().toString() + GameParams.DATA_DELIMITER +
                                                         contractDeclarator.getDeclaringPlayer().getName());
                }
            }
        }
    }

    public void declareContract(Player player, CardSuit suit, CardValue value) {
        if (contractDeclarator.declareContract(player, suit, value)) {
            eventBus.publish(GameEvents.contractDeclared(player, suit, value));
            startGamePlay();
            eventBus.publish(GameEvents.gamePhaseChanged(EventType.PLAY_GAME.getValue()));
        }
    }

    private void confirmDeclarer(Player player) {
        this.declarer = player;
        MainServer.getInstance().broadcast(GameParams.CASTER_DECLARED, declarer.getName() + GameParams.DATA_DELIMITER + declarer.getContract().toString(GameParams.DATA_DELIMITER));
    }

    private void confirmDeclarerCards() {
        for (int i = 0; i < DECLARER_ADDITIONAL_CARD_NUM; i++) {
            declarer.receiveCard(deck.draw());
        }

        synchronized (players) {
            for (Player player : players) {
                if (player == declarer) {
                    MainServer.getInstance().broadcast(declarer, GameParams.SELECT_CARDS_TO_DISCARD, declarer.getCardListInfo(GameParams.DATA_DELIMITER));
                }
                else {
                    MainServer.getInstance().broadcast(player, GameParams.START_DECLARER_CARD_SELECTION, "");
                }
            }
        }
    }

    public void discardCard(Player player, String cardData) {
        String[] cardInfo = cardData.split(GameParams.DATA_DELIMITER);

        Card card = new Card(CardSuit.convertString(cardInfo[0]), CardValue.convertString(cardInfo[1]));

        player.discardCard(card);
        deck.returnCard(card);

        if (player.getDiscardedCardNum() < DECLARER_ADDITIONAL_CARD_NUM) {
            MainServer.getInstance().broadcast(declarer, GameParams.SELECT_CARDS_TO_DISCARD, declarer.getCardListInfo(GameParams.DATA_DELIMITER));
        } else {
            MainServer.getInstance().broadcast(declarer, GameParams.UPDATE_CARD_LIST, declarer.getCardListInfo(GameParams.DATA_DELIMITER));
            MainServer.getInstance().broadcast(declarer, GameParams.ASK_GIRU_CHANGE, "");
        }
    }

    public void confirmGiru() {
        rule.setGiru(declarer.getContract().getGiru());
        MainServer.getInstance().broadcast(GameParams.CONFIRM_CONTRACT, declarer.getContract().toString(GameParams.DATA_DELIMITER));

        askFriendCard();
    }

    public void confirmGiru(String giruData) {
        CardSuit giru = CardSuit.convertString(giruData);
        declarer.declareContract(giru, declarer.getContract().getScore() + 2);

        confirmGiru();
    }

    private void askFriendCard() {
        MainServer.getInstance().broadcast(declarer, GameParams.ASK_FRIEND_CARD, rule.getMighty().toString() + GameParams.DATA_DELIMITER + rule.getJokerCall().toString());
    }

    public void determineFriend(String cardData) {
        String[] cardInfo = cardData.split(GameParams.DATA_DELIMITER);

        Card friendCard = new Card(CardSuit.convertString(cardInfo[0]), CardValue.convertString(cardInfo[1]));

        synchronized (players) {
            for (Player player: players) {
                if (player.hasCard(friendCard)) {
                    friend = player;
                }
            }
        }

        synchronized (players) {
            for (Player player: players) {
                if (player == friend) {
                    MainServer.getInstance().broadcast(player, GameParams.NOTIFY_FRIEND, "");
                }

                MainServer.getInstance().broadcast(player, GameParams.CONFIRM_FRIEND, friendCard.toString());
                MainServer.getInstance().broadcast(player, GameParams.START_PLAYING, "");
            }
        }

        startPlaying();
    }

    private void startPlaying() {
        gameController = new GameController(players, rule, declarer, friend);
        MainServer.getInstance().broadcast(gameController.getCurrentPlayer(), GameParams.ASK_CARD, "");
    }

    public void submitCard(Player player, Card card) {
        if (gameController.submitCard(player, card)) {
            eventBus.publish(GameEvents.cardSubmitted(player, card));
            if (gameController.isGameEnd()) {
                eventBus.publish(GameEvents.gamePhaseChanged(EventType.END_GAME.getValue()));
            }
        }
    }

    private void BroadcastCardSubmission(String playerName, Card card) {
        synchronized (players) {
            for (Player player: players) {
                MainServer.getInstance().broadcast(player, GameParams.NOTIFY_CARD_SUBMISSION, playerName + GameParams.DATA_DELIMITER + card.toString());
            }
        }
    }

    private void NotifyGameWinner() {
        if (rule.isWinner(declarer)) {
            MainServer.getInstance().broadcast(GameParams.NOTIFY_GAME_WINNER, GameParams.DECLARER_WIN + GameParams.DATA_DELIMITER + friend.getName());
        }
        else {
            MainServer.getInstance().broadcast(GameParams.NOTIFY_GAME_WINNER, GameParams.DECLARER_LOSE + GameParams.DATA_DELIMITER + friend.getName());
        }
    }

    public void startGame() {
        eventBus.publish(GameEvents.gamePhaseChanged(EventType.JOIN_GAME.getValue()));
        distributeCards();
        eventBus.publish(GameEvents.gamePhaseChanged(EventType.DISTRIBUTE_CARD.getValue()));
        startContractDeclaration();
        eventBus.publish(GameEvents.gamePhaseChanged(EventType.DECLARE_CONTRACT.getValue()));
    }

    private void startGamePlay() {
        // Implementation of startGamePlay method
    }
}
