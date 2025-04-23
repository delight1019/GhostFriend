package GhostFriend.Base.Event;

public enum EventType {
    // 플레이어 관련 이벤트
    PLAYER_JOINED("PLAYER_JOINED"),
    CARD_SUBMITTED("CARD_SUBMITTED"),
    CONTRACT_DECLARED("CONTRACT_DECLARED"),
    
    // 게임 상태 관련 이벤트
    GAME_PHASE_CHANGED("GAME_PHASE_CHANGED"),
    
    // 게임 단계 관련 이벤트
    JOIN_GAME("JOIN_GAME"),
    DISTRIBUTE_CARD("DISTRIBUTE_CARD"),
    DECLARE_CONTRACT("DECLARE_CONTRACT"),
    PLAY_GAME("PLAY_GAME"),
    END_GAME("END_GAME");
    
    private final String value;
    
    EventType(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
} 