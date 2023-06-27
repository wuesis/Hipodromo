package ServerRMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class MatrixResolverEntity extends UnicastRemoteObject implements IRemoteMarixResolver {
    int[][] matrixA, matrixB;
    int matrizSize;
    protected MatrixResolverEntity() throws RemoteException{

    }

    @Override
    public int[][] matrixResolver(int[][] matrizA, int[][] matrizB) throws RemoteException {
        this.matrixA = matrizA;
        this.matrixB = matrizB;
        matrizSize= matrizA.length;
        long startTime = System.currentTimeMillis();
        float posicion = 0;
        int[][] temporalMatriz = new int[matrizSize][matrizSize];
        int m = matrizSize;
        int n = matrizSize;
        int o = matrizSize;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    temporalMatriz[i][j] += matrixA[i][k] * matrixB[k][j];

                }
            }
        }
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("El proceso secuencial tuvo una durcion de: " + elapsedTime + " milisegundos.");
        return temporalMatriz;
    }
}
