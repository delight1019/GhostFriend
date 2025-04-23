package GhostFriend.Base.Event;

import java.time.LocalDateTime;

public interface GameEvent {
    String getEventType();
    LocalDateTime getTimestamp();
    String getEventId();
} 