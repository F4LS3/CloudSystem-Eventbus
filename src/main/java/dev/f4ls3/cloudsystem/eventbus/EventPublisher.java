package dev.f4ls3.cloudsystem.eventbus;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class EventPublisher {

    private final Map<EventSubscriber, EventType> eventSubscribers;

    public EventPublisher() {
        this.eventSubscribers = new HashMap<>();
    }

    /**
    * @param event The event to be published to all registered EventSubscribers
     * @see Event
     * @see EventSubscriber
    * */
    public void publish(Event event) {
        eventSubscribers
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() == event.getType())
                .collect(Collectors.toList())
                .forEach(entry -> {
                    if(event.isCanceled()) return;
                    entry.getKey().onEvent(event);
                });
    }

    /**
     * @param type The EventType which the EventSubscriber should be registered to
     * @param subscriber The EventSubscriber which holds the onEvent method that is to be executed on publish
     * @see EventType
     * @see EventSubscriber
     * */
    public void registerEventSubscriber(EventType type, EventSubscriber subscriber) {
        this.eventSubscribers.put(subscriber, type);
    }

    /**
     * @param subscriber The EventSubscriber that should be removed from the publishing list
     * @see EventSubscriber
     * */
    public void unregisterEventSubscriber(EventSubscriber subscriber) {
        this.eventSubscribers.remove(subscriber);
    }
}
