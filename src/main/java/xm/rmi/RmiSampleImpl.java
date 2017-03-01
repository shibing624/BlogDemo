package xm.rmi;

import org.ansj.splitWord.analysis.ToAnalysis;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author xuming
 */
public class RmiSampleImpl extends UnicastRemoteObject implements RmiSample {
    protected RmiSampleImpl() throws RemoteException {
    }

    @Override
    public int sum(int a, int b) throws RemoteException {
        return a + b;
    }

    @Override
    public String say(String str) throws RemoteException {
        return ToAnalysis.parse(str).toString();
    }


}
