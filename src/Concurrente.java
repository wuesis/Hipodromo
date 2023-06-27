
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Concurrente implements IRider {

    private int matrizSize = 0, threadQty = 3;
    private ExecutorService executor;
    private int positionsPerThread;
    private volatile int[][] temporalMatriz;
    private volatile int start, end, currentValue;

    public Concurrente() {
        matrizSize = GUI.matrizA.length;
        temporalMatriz = new int[matrizSize][matrizSize];
        executor = Executors.newFixedThreadPool(threadQty);
        positionsPerThread = GUI.matrizA.length * GUI.matrizB[0].length / threadQty;
    }

    @Override
    public void Ride() {
        long startTime = System.currentTimeMillis();
        currentValue = 0;
        for (int i = 0; i < threadQty; i++) {
            start = i * positionsPerThread;
            end = (i + 1) * positionsPerThread;
            executor.execute(new MatrixMultiplicationTask(GUI.matrizA, GUI.matrizB, temporalMatriz, start, end, threadQty));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {

        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        GUI.concurrentTimeField.setText(elapsedTime + "ms");
        System.out.println("El hilo concurrente ha estado en ejecución durante " + elapsedTime + " milisegundos.");
        GUI.isRunning = false;

//        for (int i = 0; i < temporalMatriz.length; i++) {
//            for (int j = 0; j < temporalMatriz[i].length; j++) {
//                System.out.print(temporalMatriz[i][j] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println("\n");
//        System.out.println("\n");
//        System.out.println("\n");
    }

    @Override
    public void run() {

    }
}

class MatrixMultiplicationTask implements Runnable {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] result;
    private int start, end, threadQty;

    public MatrixMultiplicationTask(int[][] matrixA, int[][] matrixB, int[][] result, int start, int end, int threadQty) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.result = result;
        this.start = start;
        this.end = end;
        this.threadQty = threadQty;
    }

    public void run() {
        System.out.println("Hilo levantado para procesar la matriz en el pocision: " + start + " a la " + end);
        int rowsA = matrixA.length;
        int colsB = matrixB[0].length;

        for (int i = start; i < end; i++) {
            int row = i / colsB;
            int col = i % colsB;
            for (int j = 0; j < matrixB.length; j++) {
                result[row][col] += matrixA[row][j] * matrixB[j][col];
            }
        }

        int value = GUI.riders.get("B") + (1100 / threadQty);
        GUI.riders.replace("B", value);
        System.out.println("Fin del hilo levantado para procesar la matriz en el pocision: " + start + " a la " + end);
    }
}



