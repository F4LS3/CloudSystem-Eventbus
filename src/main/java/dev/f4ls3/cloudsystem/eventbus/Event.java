package dev.f4ls3.cloudsystem.eventbus;

public class Event {

    private final EventType type;

    private boolean isCanceled;

    protected Event(EventType type) {
        this.type = type;
    }

    public void setCanceled(boolean canceled) {
        this.isCanceled = canceled;
    }

    public boolean isCanceled() {
        return isCanceled;
    }

    public EventType getType() {
        return this.type;
    }
}
