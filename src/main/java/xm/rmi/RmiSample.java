package xm.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author xuming
 */
public interface RmiSample extends Remote {
    int sum(int a, int b) throws RemoteException;
    String say(String name)throws RemoteException;
}
