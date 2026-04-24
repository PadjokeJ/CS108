package dev.padjokej.week08;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSubject implements Subject {
    List<Observer> observers;

    @Override
    public void addObserver(Observer obs) {
        if (observers == null) observers = new ArrayList<>();
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    protected void notifyObservers() {
        observers.forEach(o -> o.update(this));
    }
}
