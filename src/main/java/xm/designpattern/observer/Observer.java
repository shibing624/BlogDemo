package xm.designpattern.observer;

/**
 * @author xuming
 */
public abstract class Observer {
    protected String name;
    protected Subject sub;

    public Observer(String name ,Subject sub){
        this.name = name;
        this.sub  = sub;
    }
    public abstract void update();
}
