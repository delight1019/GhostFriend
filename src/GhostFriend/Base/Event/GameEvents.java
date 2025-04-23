package GhostFriend.Base.Event;

import GhostFriend.Base.Player.Player;

public class GameEvents {
    public static class PlayerJoinedEvent extends BaseGameEvent {
        private final Player player;

        public PlayerJoinedEvent(Player player) {
            super(EventType.PLAYER_JOINED.getValue());
            this.player = player;
        }

        public Player getPlayer() {
            return player;
        }
    }

    public static class CardSubmittedEvent extends BaseGameEvent {
        private final Player player;
        private final String cardData;

        public CardSubmittedEvent(Player player, String cardData) {
            super(EventType.CARD_SUBMITTED.getValue());
            this.player = player;
            this.cardData = cardData;
        }

        public Player getPlayer() {
            return player;
        }

        public String getCardData() {
            return cardData;
        }
    }

    public static class ContractDeclaredEvent extends BaseGameEvent {
        private final Player player;
        private final String contractData;

        public ContractDeclaredEvent(Player player, String contractData) {
            super(EventType.CONTRACT_DECLARED.getValue());
            this.player = player;
            this.contractData = contractData;
        }

        public Player getPlayer() {
            return player;
        }

        public String getContractData() {
            return contractData;
        }
    }

    public static class GamePhaseChangedEvent extends BaseGameEvent {
        private final String newPhase;

        public GamePhaseChangedEvent(String newPhase) {
            super(EventType.GAME_PHASE_CHANGED.getValue());
            this.newPhase = newPhase;
        }

        public String getNewPhase() {
            return newPhase;
        }
    }
} 