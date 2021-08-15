package dev.f4ls3.cloudsystem.eventbus;

public class EventSubscriberPair {

    private final int eventId;
    private final EventSubscriber subscriber;

    public EventSubscriberPair(int eventId, EventSubscriber subscriber) {
        this.eventId = eventId;
        this.subscriber = subscriber;
    }

    public int getEventId() {
        return eventId;
    }

    public EventSubscriber getSubscriber() {
        return subscriber;
    }
}
