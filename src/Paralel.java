import ServerRMI.IRemoteMarixResolver;
import ServerRMI.MatrixResolverEntity;

import javax.swing.plaf.TableHeaderUI;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Paralel extends UnicastRemoteObject implements IRider, Runnable {

    private int[][] matrixA, matrixB, resultRMI, resultServer;
    int serverPort;
    String serverIpAdress;
    private volatile int start, end;

    private Thread llamadaRMI;
    IRemoteMarixResolver rmi;

    protected Paralel(int[][] matrizA, int[][] matrizB,String serverIpAdress,int serverPort) throws RemoteException {
        this.matrixA = matrizA;
        this.matrixB = matrizB;
        this.serverIpAdress = serverIpAdress;
        this.serverPort = serverPort;
        llamadaRMI = new Thread(this::run);
    }

    @Override
    public void Ride() {
        long startTime = System.currentTimeMillis();
        try {
            llamadaRMI.start();
            int rowsA = matrixA.length;
            int colsB = matrixB[0].length;
            resultServer = new int[matrixA.length][matrixB.length];
            start = matrixA.length * matrixB.length / 2;
            end = matrixA.length * matrixB.length;

            for (int i = start; i < end; i++) {
                int row = i / colsB;
                int col = i % colsB;
                for (int j = 0; j < matrixB.length; j++) {
                    resultServer[row][col] += matrixA[row][j] * matrixB[j][col];
                }
            }
            GUI.riders.replace("C", 500);

            synchronized (llamadaRMI){
                while (resultRMI == null) {
                    llamadaRMI.wait();
                    GUI.riders.replace("C", 1100);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("El proceso paralelo tuvo una durcion de: " + elapsedTime + " milisegundos.");
        GUI.paralelTimeField.setText(elapsedTime + "ms");

//        int [][] result = new int[matrixA.length][matrixA[0].length];
//
//        for (int i = 0; i < matrixA.length; i++) {
//            for (int j = 0; j < matrixA[0].length; j++) {
//                result[i][j] = resultRMI[i][j];
//            }
//        }
//        for (int i = matrixA.length/2; i < matrixA.length; i++) {
//            for (int j = 0; j < matrixA[0].length; j++) {
//                result[i][j] = resultServer[i][j];
//            }
//        }
//
//
        //Impresion del arreglo resultante
        for (int i = 0; i < resultRMI.length; i++) {
            for (int j = 0; j < resultRMI[i].length; j++) {
                System.out.print(resultRMI[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < resultServer.length; i++) {
            for (int j = 0; j < resultServer[i].length; j++) {
                System.out.print(resultServer[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
    }

    @Override
    public void run() {
        try {

            rmi = (IRemoteMarixResolver) java.rmi.Naming.lookup("//"+serverIpAdress+":"+serverPort+"//RMIServer");
            resultRMI = rmi.matrixResolver(matrixA, matrixB, 0, (matrixA.length * matrixB.length / 2));
            synchronized (this){
                notifyAll();
            }

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Se acabo el Hilo" + resultServer);
    }

}
