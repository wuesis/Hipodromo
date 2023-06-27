package ServerRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MatrixResolverEntity extends UnicastRemoteObject implements IRemoteMarixResolver {
    int[][] matrixA, matrixB;
    int matrizSize;
    public MatrixResolverEntity() throws RemoteException{

    }

    @Override
    public int[][] matrixResolver(int[][] matrizA, int[][] matrizB, int startIndex,int endIndex) throws RemoteException {

        int[][] result = new int[matrixA.length][matrixB[0].length];

        for (int i = startIndex; i < endIndex; i++) {
            for (int j = 0; j < matrixB[0].length; j++) {
                for (int k = 0; k < matrixA[0].length; k++) {
                    result[i][j] += matrixA[i][k] * matrixB[k][j];
                }
            }
        }

        return result;
    }
}
