package xm.rmi;

import org.junit.Test;

import java.rmi.RemoteException;

/**
 * @author xuming
 */
public class RmiSampleImplTest {
    @Test
    public void sum() throws Exception {

    }

    @Test
    public void say() throws Exception {
        try {
            RmiSampleImpl rmi = new RmiSampleImpl();
            System.out.printf(rmi.say("ds"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}