package xm.designpattern.observer;

/**
 * @author xuming
 */
public class ObserverDemo {
    public static void main(String[] args) {
        Boss jackboss = new Boss();
        StockObserver tongshi0 = new StockObserver("weizeming",jackboss);
        jackboss.attach(tongshi0);
        jackboss.setAction("i am boss : jackboss. i am back. ");
        jackboss.notice();


        Secretary secretary = new Secretary();
        StockObserver tongshi1 = new StockObserver("lili",secretary);
        StockObserver tongshi2 = new StockObserver("lucy",secretary);
        NBAObserver tongshi3 = new NBAObserver("timo",secretary);
        secretary.attach(tongshi1);
        secretary.attach(tongshi2);
        secretary.attach(tongshi3);
        secretary.setAction("boss is coming.secretary say.");
        secretary.notice();

    }
}
