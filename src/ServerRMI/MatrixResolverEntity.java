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
        this.matrixA = matrizA;
        this.matrixB = matrizB;
        int[][] result = new int[matrixA.length][matrixB[0].length];
        int rowsA = matrixA.length;
        int colsB = matrixB[0].length;

        for (int i = startIndex; i < endIndex; i++) {
            int row = i / colsB;
            int col = i % colsB;
            for (int j = 0; j < matrixB.length; j++) {
                result[row][col] += matrixA[row][j] * matrixB[j][col];
            }
        }

        return result;
    }
}
