package GhostFriend.Base.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GameEventBus {
    private static final int THREAD_POOL_SIZE = 4;
    private final List<GameEventListener> listeners;
    private final ExecutorService executorService;
    private final ReentrantReadWriteLock lock;

    public GameEventBus() {
        this.listeners = new ArrayList<>();
        this.executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        this.lock = new ReentrantReadWriteLock();
    }

    public void subscribe(GameEventListener listener) {
        lock.writeLock().lock();
        try {
            listeners.add(listener);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void unsubscribe(GameEventListener listener) {
        lock.writeLock().lock();
        try {
            listeners.remove(listener);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void publish(GameEvent event) {
        lock.readLock().lock();
        try {
            for (GameEventListener listener : listeners) {
                if (listener.supportsEventType(event.getEventType())) {
                    executorService.execute(() -> listener.onEvent(event));
                }
            }
        } finally {
            lock.readLock().unlock();
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
} 