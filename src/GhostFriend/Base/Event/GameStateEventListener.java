package GhostFriend.Base.Event;

import GhostFriend.Utils.Log;

import java.io.IOException;

public class GameStateEventListener implements GameEventListener {
    @Override
    public void onEvent(GameEvent event) {
        try {
            if (event.getEventType().equals(EventType.GAME_PHASE_CHANGED.getValue())) {
                handleGamePhaseChanged((GameEvents.GamePhaseChangedEvent) event);
            }
        } catch (Exception e) {
            Log.printText("Error handling game state event: " + e.getMessage());
        }
    }

    @Override
    public boolean supportsEventType(String eventType) {
        return eventType.equals(EventType.GAME_PHASE_CHANGED.getValue());
    }

    private void handleGamePhaseChanged(GameEvents.GamePhaseChangedEvent event) throws IOException {
        String newPhase = event.getNewPhase();
        Log.printText("Game phase changed to: " + newPhase);
        
        if (newPhase.equals(EventType.JOIN_GAME.getValue())) {
            handleJoinGamePhase();
        } else if (newPhase.equals(EventType.DISTRIBUTE_CARD.getValue())) {
            handleDistributeCardPhase();
        } else if (newPhase.equals(EventType.DECLARE_CONTRACT.getValue())) {
            handleDeclareContractPhase();
        } else if (newPhase.equals(EventType.PLAY_GAME.getValue())) {
            handlePlayGamePhase();
        } else if (newPhase.equals(EventType.END_GAME.getValue())) {
            handleEndGamePhase();
        }
    }

    private void handleJoinGamePhase() {
        // 게임 참가 단계 처리
        Log.printText("Processing join game phase");
    }

    private void handleDistributeCardPhase() {
        // 카드 분배 단계 처리
        Log.printText("Processing distribute card phase");
    }

    private void handleDeclareContractPhase() {
        // 계약 선언 단계 처리
        Log.printText("Processing declare contract phase");
    }

    private void handlePlayGamePhase() {
        // 게임 진행 단계 처리
        Log.printText("Processing play game phase");
    }

    private void handleEndGamePhase() {
        // 게임 종료 단계 처리
        Log.printText("Processing end game phase");
    }
} 