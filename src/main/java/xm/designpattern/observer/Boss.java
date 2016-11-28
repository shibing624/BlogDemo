package xm.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuming
 */
public class Boss implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String action;

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notice() {
        observers.forEach(Observer::update);
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}