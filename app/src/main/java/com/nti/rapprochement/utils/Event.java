package com.nti.rapprochement.utils;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Event<T> {
    private final ArrayList<Consumer<T>> handlers = new ArrayList<>();

    public void add(Consumer<T> handler) {
        handlers.add(handler);
    }

    public void remove(Consumer<T> handler) {
        handlers.remove(handler);
    }

    public void call(T args) {
        handlers.forEach(handler -> handler.accept(args));
    }
}
