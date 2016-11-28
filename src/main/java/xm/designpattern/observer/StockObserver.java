package xm.designpattern.observer;

/**
 * @author xuming
 */
public class StockObserver extends Observer {
    public StockObserver(String name,Subject sub){
        super(name,sub);
    }
    @Override
    public void update() {
        System.out.println(sub.toString() + name + "do your work.");
    }
}
