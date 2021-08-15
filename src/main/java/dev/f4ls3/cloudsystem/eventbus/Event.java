package dev.f4ls3.cloudsystem.eventbus;

public abstract class Event {

    private boolean cancelled;
    private int eventId;

    protected Event(int eventId) {
        this.eventId = eventId;
    }

    /**
     * Cancels the handling of the published event for all following subscribers,
     * that have the ignoreCancelled variable on false
     * */
    public void setCancelled() {
        this.cancelled = true;
    }

    /**
     * @return Whether the event is cancelled or not
     * */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * @return The id of the event
     * */
    public int getEventId() {
        return eventId;
    }
}
