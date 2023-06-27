import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.nio.file.Paths;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Random;
import java.util.TreeMap;


public class GUI extends JFrame implements Runnable {

    private BufferedImage runwayBuffer, donkeyBuffer, horseBuffer, unicornBuffer;
    private short yAxisHorse = 20;
    private String currentRelativePath = "";
    private int matrizSize = 0, serverPort = 0;
    private JPanel optionPanle, roadPanel;
    private JLabel secuentialTimeLabel, concurrentTimeLabel, paralelTimeLabel;
    private JButton secuetialButton, concurrentButton, paralelButton, resetButton;
    private Random random;
    private Executor executor;

    public static int[][] matrizA, matrizB;
    public static TreeMap<String, Integer> riders = new TreeMap<>();
    public static boolean isRunning = false;
    public static JTextField secuentialTimeField, concurrentTimeField, paralelTimeField;

    public GUI(int metrixSize, int serverPort) {

        super("Hippodrome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        //Initialice variables
        currentRelativePath = Paths.get("").toAbsolutePath().toString();
        random = new Random();
        this.matrizSize = metrixSize;
        this.serverPort = serverPort;
        isRunning = true;
        fillMatrizes();
        setupGUI();
        Thread thread = new Thread(this);
        thread.start();


    }

    public void paint(Graphics g) {
        if (isRunning) {
            paintLocations();
        }
    }

    public void paintLocations() {
        optionPanle.repaint();
        roadPanel.getGraphics().drawImage(runwayBuffer, 0, 31, this);
        riders.forEach((key, value) -> {
            switch (key) {
                case "A":
                    roadPanel.getGraphics().drawImage(donkeyBuffer, value, yAxisHorse, this);
                    break;
                case "B":
                    roadPanel.getGraphics().drawImage(horseBuffer, value, yAxisHorse, this);
                    break;
                case "C":
                    roadPanel.getGraphics().drawImage(unicornBuffer, value, yAxisHorse, this);
                    break;
            }
            yAxisHorse += 90;
        });
        yAxisHorse = 20;
    }

    private void setupGUI() {
        setSize(1200 + 17, 400);
        this.getContentPane().removeAll();
        this.revalidate();
        setLayout(new BorderLayout());

        optionPanle = new JPanel();
        optionPanle.setLayout(new FlowLayout());

        resetButton = new JButton("Reset");

        secuetialButton = new JButton("Start secuential");
        secuentialTimeLabel = new JLabel("Secuential Time");
        secuentialTimeField = new JTextField("                         ");
        secuentialTimeField.setEditable(false);

        concurrentButton = new JButton("Start concurrent");
        concurrentTimeLabel = new JLabel("Concurrent Time");
        concurrentTimeField = new JTextField("                         ");
        concurrentTimeField.setEditable(false);

        paralelButton = new JButton("Start paralel");
        paralelTimeLabel = new JLabel("Paralel Time");
        paralelTimeField = new JTextField("                         ");
        paralelTimeField.setEditable(false);

        optionPanle.add(resetButton);
        optionPanle.add(secuetialButton);
        optionPanle.add(secuentialTimeLabel);
        optionPanle.add(secuentialTimeField);
        optionPanle.add(concurrentButton);
        optionPanle.add(concurrentTimeLabel);
        optionPanle.add(concurrentTimeField);
        optionPanle.add(paralelButton);
        optionPanle.add(paralelTimeLabel);
        optionPanle.add(paralelTimeField);
        add(optionPanle, BorderLayout.NORTH);

        roadPanel = new JPanel();
        roadPanel.setLayout(new FlowLayout());
        add(roadPanel, BorderLayout.CENTER);
        //Setup buffers
        runwayBuffer = new BufferedImage(1200, getHeight(), BufferedImage.TYPE_INT_ARGB);
        donkeyBuffer = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        horseBuffer = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        unicornBuffer = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        //Set initial position and name
        riders.put("A", 0);
        riders.put("B", 0);
        riders.put("C", 0);

        setupRoads();
        setupHorses();
    }

    private void setupRoads() {
        short currentYAxis = 0;

        try {
            runwayBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "/src/Utilities/S1.png")), 0, currentYAxis, this);
            currentYAxis += 100;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            runwayBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "/src/Utilities/C1.png")), 0, currentYAxis, this);
            currentYAxis += 100;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            runwayBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "/src/Utilities/P1.png")), 0, currentYAxis, this);
            currentYAxis += 100;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setupHorses() {
        try {
            donkeyBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "/src/Utilities/Donkey.png")), 0, 0, this);
            horseBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "/src/Utilities/Horse.png")), 0, 0, this);
            unicornBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "/src/Utilities/Unicorn.png")), 0, 0, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillMatrizes() {
        int size = matrizSize;
        matrizA = new int[size][size];
        matrizB = new int[size][size];

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                matrizA[row][column] = random.nextInt(9 - 1) + 1;
            }
        }

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                matrizB[row][column] = random.nextInt(9 - 1) + 1;
            }
        }
    }

    @Override
    public void run() {

        resetButton.addActionListener((actionEvent) -> {
            secuentialTimeField.setText("");
            concurrentTimeField.setText("");
            paralelTimeField.setText("");
            riders.replace("A", 0);
            riders.replace("B", 0);
            riders.replace("C", 0);
            paintLocations();
        });

        secuetialButton.addActionListener((actionEvent) -> {
            try {
                Secuencial secuentialProcess = new Secuencial();
                executor = new Executor();
                executor.setProcess(secuentialProcess);
                executor.StartRide();
            } catch (Exception e) {
                System.out.println(e);
            }

        });

        concurrentButton.addActionListener((actionEvent) -> {
            try {
                Concurrente concurrentProcess = new Concurrente();
                executor = new Executor();
                executor.setProcess(concurrentProcess);
                executor.StartRide();
                isRunning = true;
            } catch (Exception e) {
                System.out.println(e);
            }
        });

        paralelButton.addActionListener((actionEvent) -> {
            try {
                Paralel paralel = new Paralel(matrizA, matrizB);
                executor = new Executor();
                executor.setProcess(paralel);
                executor.StartRide();
                isRunning = true;
            } catch (RemoteException e) {
                System.out.println(e);
            }
            JOptionPane.showMessageDialog(null, "¡Nada implementado!", "Alerta", JOptionPane.WARNING_MESSAGE);
        });


        while (true) {
            repaint();
            try {
                Thread.sleep(5);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
