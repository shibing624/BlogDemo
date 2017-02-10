package xm.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author xuming
 */
public class RmiSampleServer {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(8080);
            RmiSampleImpl server = new RmiSampleImpl();
            Naming.rebind("//localhost:8080/SAMPLE-SERVER",server);
            System.out.println("server start success.");
        }catch (Exception e){
            System.err.println(e);
        }
    }
}
