import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Paralel extends UnicastRemoteObject implements IRider, Runnable, IRemoteMarixResolver {

    protected Paralel(int[][] matrizA, int[][] matrizB) throws RemoteException {

    }

    @Override
    public void Ride() {

    }

    @Override
    public void run() {

    }

    @Override
    public int[][] matrixResolver(int[][] matrixA, int[][] matrixB) throws RemoteException {
        return new int[0][];
    }
}
