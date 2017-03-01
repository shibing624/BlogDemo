package xm.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * @author xuming
 */
public class RmiSampleClient {
    public static void main(String[] args) {
        outputOther();
    }

    private static void output() {
        try {
            String url = "//localhost:8080/SAMPLE-SERVER";
            RmiSample rmiSample = (RmiSample) Naming.lookup(url);
            System.out.println("client start success.");
            System.out.println("1+2=" + rmiSample.sum(1, 2));
        } catch (RemoteException exc) {
            System.out.println("Error in lookup: " + exc.toString());
        } catch (MalformedURLException exc) {
            System.out.println("Malformed URL: " + exc.toString());
        } catch (java.rmi.NotBoundException exc) {
            System.out.println("NotBound: " + exc.toString());
        }
    }

    private static void outputOther() {
        try {
            System.setProperty("java.security.policy", "AllPermission.policy");
            RmiSample rmiSample = (RmiSample) Naming.lookup("rmi://localhost:5858/rmiserver");
            System.out.println("client start success.");
            System.out.println("1+2=" + rmiSample.sum(1, 2));
            System.out.println("segment:" + rmiSample.say("我爱中国，我喜欢结婚和尚未结婚的lili小姐姐"));
        } catch (RemoteException exc) {
            System.out.println("Error in lookup: " + exc.toString());
        } catch (MalformedURLException exc) {
            System.out.println("Malformed URL: " + exc.toString());
        } catch (java.rmi.NotBoundException exc) {
            System.out.println("NotBound: " + exc.toString());
        }
    }



}
