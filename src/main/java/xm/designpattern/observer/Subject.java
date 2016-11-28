package xm.designpattern.observer;

/**
 * @author xuming
 */
public interface Subject {
    void attach(Observer observer);
    void detach(Observer observer);
    void notice();
    default String getAction(Observer observer){
        return observer.name;
    }

}
