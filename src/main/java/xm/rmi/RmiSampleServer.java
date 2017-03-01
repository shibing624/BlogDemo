package xm.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author xuming
 */
public class RmiSampleServer {
    public static void main(String[] args) {
        RmiSampleServer server =  new RmiSampleServer();
//        server.startServer();
        server.startOtherServer();
    }

    public boolean startServer(){
        try {
            LocateRegistry.createRegistry(8080);
            RmiSampleImpl server = new RmiSampleImpl();
            Naming.rebind("//localhost:8080/SAMPLE-SERVER",server);
            System.out.println("server start success.");
            return true;
        }catch (RemoteException e){
            System.err.println(e);
        } catch (MalformedURLException e) {
            System.err.println(e);
        }
        return false;
    }

    public boolean startOtherServer(){
        try{
            Registry registry = LocateRegistry.createRegistry(5858);
            System.setProperty("hostname","localhost");
            RmiSample rmiSample = new RmiSampleImpl();
            registry.rebind("rmiserver",rmiSample);
            System.out.println("server is ready.");
        } catch (RemoteException e) {
            System.out.println("RemoteException:"+e);
        }
        return false;
    }

}
