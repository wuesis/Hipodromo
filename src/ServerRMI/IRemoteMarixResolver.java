package ServerRMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRemoteMarixResolver extends Remote {

    int [][] matrixResolver(int [][] matrixA,int [][] matrixB, int starIndex, int endIndex) throws RemoteException;
}
