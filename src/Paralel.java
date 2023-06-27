import ServerRMI.IRemoteMarixResolver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Paralel extends UnicastRemoteObject implements IRider, Runnable {

    protected Paralel(int[][] matrizA, int[][] matrizB) throws RemoteException {

    }

    @Override
    public void Ride() {

    }

    @Override
    public void run() {

    }

}
