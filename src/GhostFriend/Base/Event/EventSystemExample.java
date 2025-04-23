package GhostFriend.Base.Event;

import GhostFriend.Base.Player.Player;
import GhostFriend.Utils.Log;

public class EventSystemExample {
    private final GameEventBus eventBus;
    private final PlayerEventListener playerEventListener;
    private final GameStateEventListener gameStateEventListener;

    public EventSystemExample() {
        // 이벤트 버스 초기화
        eventBus = new GameEventBus();
        
        // 리스너 초기화
        playerEventListener = new PlayerEventListener();
        gameStateEventListener = new GameStateEventListener();
        
        // 리스너 등록
        eventBus.subscribe(playerEventListener);
        eventBus.subscribe(gameStateEventListener);
    }

    public void startGame() {
        try {
            // 게임 시작 이벤트 발행
            eventBus.publish(new GameEvents.GamePhaseChangedEvent(EventType.JOIN_GAME.getValue()));
            
            // 플레이어 참가 이벤트 발행 예시
            Player player = new Player("Player1");
            eventBus.publish(new GameEvents.PlayerJoinedEvent(player));
            
            // 카드 제출 이벤트 발행 예시
            eventBus.publish(new GameEvents.CardSubmittedEvent(player, "SPADE|ACE"));
            
            // 계약 선언 이벤트 발행 예시
            eventBus.publish(new GameEvents.ContractDeclaredEvent(player, "SPADE|13"));
            
            // 게임 단계 변경 이벤트 발행
            eventBus.publish(new GameEvents.GamePhaseChangedEvent(EventType.PLAY_GAME.getValue()));
            
        } catch (Exception e) {
            Log.printText("Error in game flow: " + e.getMessage());
        }
    }

    public void shutdown() {
        eventBus.shutdown();
    }
} 