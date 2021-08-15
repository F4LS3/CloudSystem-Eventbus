package dev.f4ls3.cloudsystem.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EventPublisher {

    private final List<EventSubscriberPair> subscribers;
    private final HashMap<Integer, Event> events;

    public EventPublisher() {
        this.subscribers = new ArrayList<>();
        this.events = new HashMap<>();
    }

    /**
     * @param event The event that is to be registered
     * Events need to be registered inorder to be called and used.
     * If an event isn't registered it cannot be used.
     * */
    public void registerEvent(Event event) {
        if(!this.events.containsKey(event.getEventId())) {
            this.events.put(event.getEventId(), event);
        } else {
            System.out.println("EVENT_BUS_ERROR: FATAL: EventId " + event.getEventId() + " is already bound to an event");
        }
    }

    /**
     * @param eventId The ID of the event the subscriber should be listening to
     * @param subscriber The subscriber that is to be called upon publishing of the specified event
     * */
    public void subscribe(int eventId, EventSubscriber subscriber) {
        if(this.events.containsKey(eventId)) {
            this.subscribers.add(new EventSubscriberPair(eventId, subscriber));
        } else {
            System.out.println("EVENT_BUS_ERROR: EventID " + eventId + " isn't bound to any event");
        }
    }

    /**
     * @param event The event that is to be published and parsed to all registered
     * */
    public void publish(Event event) {
        this.subscribers.forEach(subscriberPair -> {
            EventSubscriber subscriber = subscriberPair.getSubscriber();
            if(Objects.equals(subscriberPair.getEventId(), event.getEventId())) {
                for(final Method method : subscriber.getClass().getDeclaredMethods()) {
                    if (method.isAnnotationPresent(EventExecutor.class)) {
                        EventExecutor executor = method.getAnnotation(EventExecutor.class);

                        if (event.isCancelled() && !executor.ignoreCancelled()) return;
                        try {
                            method.invoke(subscriber, event);
                        } catch (IllegalAccessException | InvocationTargetException ex) {
                            ex.printStackTrace();
                        }
                    } else if (Arrays.asList(subscriber.getClass().getDeclaredMethods()).indexOf(method) == subscriber.getClass().getDeclaredMethods().length) {
                        System.out.println("EVENT_BUS_ERROR: FATAL: No method in " + subscriber.getClass().getSimpleName() + " is annotated as EventExecutor");
                    }
                }
            }
        });
    }

    public List<EventSubscriber> getAllSubscribersOfEvent(Event event) {
        if(event == null) {
            System.out.println("EVENT_BUS_ERROR: Given event is null");
            return null;
        }

        List<EventSubscriber> subscribers = new ArrayList<>();

        this.subscribers.forEach(subscriberPair -> {
            if(subscriberPair.getEventId() == event.getEventId()) {
                subscribers.add(subscriberPair.getSubscriber());
            }
        });

        return subscribers;
    }

    public List<EventSubscriber> getAllSubscribersOfEvent(int eventId) {
        return getAllSubscribersOfEvent(this.events.getOrDefault(eventId, null));
    }
}
