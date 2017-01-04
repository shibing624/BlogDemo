package xm.ik.analyzer;

/**
 * @author xuming
 */
public class IKAnalyzer {
    private boolean useSmart;
    public boolean useSmart(){
        return useSmart;
    }
    public void setUseSmart(boolean useSmart){
        this.useSmart = useSmart;
    }
    public IKAnalyzer(){
        this(true);
    }
    public IKAnalyzer(boolean useSmart){
        this.useSmart = useSmart;
    }
}
