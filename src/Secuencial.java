import java.util.Arrays;
import java.util.Random;

public class Secuencial implements IRider, Runnable {

    int matrizSize = 0;

    public Secuencial() {
        matrizSize = GUI.matrizA.length;
    }

    @Override
    public void Ride() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        float posicion = 0;
        int[][] temporalMatriz = new int[matrizSize][matrizSize];
        int m = GUI.matrizA.length;
        int n = GUI.matrizB[0].length;
        int o = GUI.matrizB.length;

        for (int i = 0; i < m; i++) {
            posicion = (float) (i+1)*1100/matrizSize;
            GUI.riders.replace("A", (int) posicion);
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < o; k++) {
                    temporalMatriz[i][j] += GUI.matrizA[i][k] * GUI.matrizB[k][j];

                }
            }
        }

        GUI.riders.replace("A", (int) posicion);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        GUI.secuentialTimeField.setText(elapsedTime + "ms");
        System.out.println("El proceso secuencial tuvo una durcion de: " + elapsedTime + " milisegundos.");
        GUI.isRunning = false;


        for (int i = 0; i < temporalMatriz.length; i++) {
            for (int j = 0; j < temporalMatriz[i].length; j++) {
                System.out.print(temporalMatriz[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("\n");
        System.out.println("\n");
        System.out.println("\n");
    }


}
