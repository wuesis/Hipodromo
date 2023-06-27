import ServerRMI.IRemoteMarixResolver;
import ServerRMI.MatrixResolverEntity;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Paralel extends UnicastRemoteObject implements IRider, Runnable {

    private int[][] matrixA, matrixB;

    protected Paralel(int[][] matrizA, int[][] matrizB) throws RemoteException {
        this.matrixA = matrizA;
        this.matrixB = matrizB;
    }

    @Override
    public void Ride() {
        try {
            MatrixResolverEntity matrixResolverEntity = (MatrixResolverEntity) java.rmi.Naming.lookup("//192.168.1.241:7411//RMIServer");
            int[][] result = matrixResolverEntity.matrixResolver(matrixA,matrixB,0,(matrixA.length*matrixB.length/2));
            System.out.println(result);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {

    }

}
