package GhostFriend.Base.Event;

public interface GameEventListener {
    void onEvent(GameEvent event);
    boolean supportsEventType(String eventType);
} 