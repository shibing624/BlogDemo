package xm.designpattern.observer;

/**
 * @author xuming
 */
public class NBAObserver extends Observer {

    public NBAObserver(String name, Subject sub) {
        super(name, sub);
    }

    @Override
    public void update() {
        System.out.println( name + " do your work.");
    }
}
