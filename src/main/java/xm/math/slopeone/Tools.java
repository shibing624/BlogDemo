package xm.math.slopeone;

/**
 * Created by xuming on 2016/6/20.
 */
public class Tools {
    public static String GetKey(int Item1Id, int Item2Id) {
        return (Item1Id < Item2Id) ? Item1Id + "->" + Item2Id : Item2Id + "->" + Item1Id;
    }
}
