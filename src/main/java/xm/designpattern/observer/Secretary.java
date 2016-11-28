package xm.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xuming
 */
public class Secretary implements Subject {
    private List<Observer> observers = new ArrayList<>();
    private String action;

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public void detach(Observer observer) {
        observers.remove(observer);
    }

    public void notice() {
        for (Observer o : observers) {
            o.update();
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
