package dev.f4ls3.cloudsystem.eventbus;

public abstract class EventSubscriber {

    abstract boolean onEvent(Event event);
}
