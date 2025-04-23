package GhostFriend.Base.Event;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseGameEvent implements GameEvent {
    private final String eventId;
    private final LocalDateTime timestamp;
    private final String eventType;

    protected BaseGameEvent(String eventType) {
        this.eventId = UUID.randomUUID().toString();
        this.timestamp = LocalDateTime.now();
        this.eventType = eventType;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String getEventId() {
        return eventId;
    }
} 