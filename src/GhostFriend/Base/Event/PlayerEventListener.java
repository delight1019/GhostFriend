package GhostFriend.Base.Event;

import GhostFriend.Base.Player.Player;
import GhostFriend.Utils.Log;

import java.io.IOException;

public class PlayerEventListener implements GameEventListener {
    @Override
    public void onEvent(GameEvent event) {
        try {
            if (event.getEventType().equals(EventType.PLAYER_JOINED.getValue())) {
                handlePlayerJoined((GameEvents.PlayerJoinedEvent) event);
            } else if (event.getEventType().equals(EventType.CARD_SUBMITTED.getValue())) {
                handleCardSubmitted((GameEvents.CardSubmittedEvent) event);
            } else if (event.getEventType().equals(EventType.CONTRACT_DECLARED.getValue())) {
                handleContractDeclared((GameEvents.ContractDeclaredEvent) event);
            }
        } catch (Exception e) {
            Log.printText("Error handling player event: " + e.getMessage());
        }
    }

    @Override
    public boolean supportsEventType(String eventType) {
        return eventType.equals(EventType.PLAYER_JOINED.getValue()) ||
               eventType.equals(EventType.CARD_SUBMITTED.getValue()) ||
               eventType.equals(EventType.CONTRACT_DECLARED.getValue());
    }

    private void handlePlayerJoined(GameEvents.PlayerJoinedEvent event) throws IOException {
        Player player = event.getPlayer();
        Log.printText("Player joined: " + player.getName());
        // 플레이어 참가 후 처리 로직
    }

    private void handleCardSubmitted(GameEvents.CardSubmittedEvent event) throws IOException {
        Player player = event.getPlayer();
        String cardData = event.getCardData();
        Log.printText("Card submitted by " + player.getName() + ": " + cardData);
        // 카드 제출 후 처리 로직
    }

    private void handleContractDeclared(GameEvents.ContractDeclaredEvent event) throws IOException {
        Player player = event.getPlayer();
        String contractData = event.getContractData();
        Log.printText("Contract declared by " + player.getName() + ": " + contractData);
        // 계약 선언 후 처리 로직
    }
} 