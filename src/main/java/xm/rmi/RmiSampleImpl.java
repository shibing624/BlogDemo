package xm.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author xuming
 */
public class RmiSampleImpl extends UnicastRemoteObject implements RmiSample {
    protected RmiSampleImpl() throws RemoteException {
        super();
    }

    @Override
    public int sum(int a, int b) throws RemoteException {
        return a+b;
    }
}
