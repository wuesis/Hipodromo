import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.TreeMap;


public class GUI extends JFrame implements Runnable {

    public static TreeMap<String, Integer> riders = new TreeMap<>();
    public static boolean isRunning = false;
    private BufferedImage runwayBuffer, donkeyBuffer, horseBuffer, unicornBuffer;
    private short yAxisHorse = 20;
    String currentRelativePath;
    private JPanel matrizSizePanel, optionPanle, optionBodyPanel;

    public static JLabel matrizSizeLabel, secuentialTimeLabel, concurrentTimeLabel;

    public static JTextField matrizSizeTextField, secuentialTimeField, concurrentTimeField;
    public static int[][] matrizA, matrizB;

    private Random random;

    Executor executor;

    public GUI() {
        super("Hippodrome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));
        setSize(400, 400);
        setVisible(true);
        setResizable(false);

        currentRelativePath = Paths.get("").toAbsolutePath().toString();
        random = new Random();

        matrizSizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        matrizSizeTextField = new JTextField("0", 15);
        matrizSizeLabel = new JLabel("Matriz size");
        matrizSizePanel.add(matrizSizeLabel);
        matrizSizePanel.add(matrizSizeTextField);

        Button starRideButton = new Button("Start");

        starRideButton.addActionListener((actionEvent) -> {
            isRunning = true;
            fillMatrizes();
            setupGUI();
            paintLocations();
            Thread thread = new Thread(this);
            thread.start();
        });

        add(matrizSizePanel);
        add(starRideButton);
    }

    public void paint(Graphics g) {
        if (isRunning) {
            paintLocations();
        }
    }

    public void paintLocations() {
        optionPanle.repaint();
        optionBodyPanel.getGraphics().drawImage(runwayBuffer, 9, 31, this);
        riders.forEach((key, value) -> {
            switch (key) {
                case "A":
                    optionBodyPanel.getGraphics().drawImage(donkeyBuffer, value, yAxisHorse, this);
                    break;
                case "B":
                    optionBodyPanel.getGraphics().drawImage(horseBuffer, value, yAxisHorse, this);
                    break;
                case "C":
                    optionBodyPanel.getGraphics().drawImage(unicornBuffer, value, yAxisHorse, this);
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

        secuentialTimeLabel = new JLabel("Secuential Time");
        secuentialTimeField = new JTextField("                         ");
        secuentialTimeField.setEditable(false);

        concurrentTimeLabel = new JLabel("Concurrent Time");
        concurrentTimeField = new JTextField("                         ");
        concurrentTimeField.setEditable(false);

        optionPanle.add(secuentialTimeLabel);
        optionPanle.add(secuentialTimeField);
        optionPanle.add(concurrentTimeLabel);
        optionPanle.add(concurrentTimeField);
        add(optionPanle, BorderLayout.NORTH);

        optionBodyPanel = new JPanel();
        optionBodyPanel.setLayout(new FlowLayout());
        add(optionBodyPanel, BorderLayout.CENTER);
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
        int size = Integer.parseInt(matrizSizeTextField.getText().trim());
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
//        Secuencial secuentialProcess = new Secuencial();
//        executor = new Executor();
//        executor.setProcess(secuentialProcess);
//        executor.StartRide();
//
//
//        while (true) {
//            repaint();
//            try {
//                Thread.sleep(5);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }

        Concurrente concurrentProcess = new Concurrente();
        executor = new Executor();
        executor.setProcess(concurrentProcess);
        executor.StartRide();
        isRunning = true;

        while (isRunning) {
            repaint();
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
