package xm.rmi;

import xm.qa.Question;

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
        return a+b;
    }

    @Override
    public String say(String name) throws RemoteException {
        Question question = new Question();
        name = question.toString();
        return name;
    }



}
