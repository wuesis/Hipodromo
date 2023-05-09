import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.TreeMap;


public class GUI extends JFrame {

    public static TreeMap<String, Integer> riders = new TreeMap<>();
    public int sequentialSpinnerValue, concurrentSpinnerValue, parallelSpinnerValue;
    private boolean isRunning = false;
    private BufferedImage runwayBuffer, donkeyBuffer, horseBuffer, unicornBuffer;
    private short yAxisHorse = 20;
    String currentRelativePath;
    private JPanel logPanel, matrizSizePanel;

    private JLabel matrizSizeLabel;

    JTextField matrizSizeTextField;
    public int[][] matrizA, matrizB;

    private Random random;

    public GUI() {
        super("Hippodrome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));
        setSize(400, 400);
        setVisible(true);
        setResizable(false);
        random = new Random();
        currentRelativePath = Paths.get("").toAbsolutePath().toString();

        logPanel = new JPanel();
        matrizSizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        matrizSizeTextField = new JTextField("0", 15);
        matrizSizeLabel = new JLabel("Matriz size");
        matrizSizePanel.add(matrizSizeLabel);
        matrizSizePanel.add(matrizSizeTextField);

        Button starRide = new Button("Start");
        starRide.addActionListener((actionEvent) -> {
            isRunning = true;
            sequentialSpinnerValue = 1;
            concurrentSpinnerValue = 1;
            parallelSpinnerValue = 1;
            fillMatrizes();
            setupGUI();
            paintLocations();
        });

        add(logPanel);
        add(matrizSizePanel);
        add(starRide);
    }


//    public void paint(Graphics g) {
//        if (isRunning) {
//            paintLocations();
//        }
//    }

    public void paintLocations() {
        this.getGraphics().drawImage(runwayBuffer, 9, 31, this);
        riders.forEach((key, value) -> {
            switch (key) {
                case "A1":
                    getGraphics().drawImage(donkeyBuffer, value, yAxisHorse, this);
                    break;
                case "A2":
                    getGraphics().drawImage(donkeyBuffer, value, yAxisHorse, this);
                    break;
                case "A3":
                    getGraphics().drawImage(donkeyBuffer, value, yAxisHorse, this);
                    break;
                case "B1":
                    getGraphics().drawImage(horseBuffer, value, yAxisHorse, this);
                    break;
                case "B2":
                    getGraphics().drawImage(horseBuffer, value, yAxisHorse, this);
                    break;
                case "B3":
                    getGraphics().drawImage(horseBuffer, value, yAxisHorse, this);
                    break;
                case "C1":
                    getGraphics().drawImage(unicornBuffer, value, yAxisHorse, this);
                    break;
                case "C2":
                    getGraphics().drawImage(unicornBuffer, value, yAxisHorse, this);
                    break;
                case "C3":
                    getGraphics().drawImage(unicornBuffer, value, yAxisHorse, this);
                    break;
            }
            yAxisHorse += 100;
        });
        yAxisHorse = 20;
    }

    private void setupGUI() {
        this.removeAll();
        setSize(1200 + 17, (sequentialSpinnerValue + concurrentSpinnerValue + parallelSpinnerValue) * 100 + 39);
        runwayBuffer = new BufferedImage(1200, getHeight(), BufferedImage.TYPE_INT_ARGB);
        donkeyBuffer = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        horseBuffer = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
        unicornBuffer = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);

        fillRidersHasMap();
        setRoads();
        setHorses();
//        JOptionPane.showMessageDialog(null, new JLabel(new ImageIcon(runwayImage)));
    }

    private void setRoads() {
        short currentYAxis = 0;

        for (short paintedHorse = 1; paintedHorse <= sequentialSpinnerValue; paintedHorse++) {
            try {
                runwayBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "\\src\\Utilities\\S" + paintedHorse + ".png")), 0, currentYAxis, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            currentYAxis += 100;
        }

        for (short paintedHorse = 1; paintedHorse <= concurrentSpinnerValue; paintedHorse++) {
            try {
                runwayBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "\\src\\Utilities\\C" + paintedHorse + ".png")), 0, currentYAxis, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            currentYAxis += 100;
        }

        for (short paintedHorse = 1; paintedHorse <= parallelSpinnerValue; paintedHorse++) {
            try {
                runwayBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "\\src\\Utilities\\P" + paintedHorse + ".png")), 0, currentYAxis, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            currentYAxis += 100;
        }
    }

    private void setHorses() {
        try {
            donkeyBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "\\src\\Utilities\\Donkey.png")), 0, 0, this);
            horseBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "\\src\\Utilities\\Horse.png")), 0, 0, this);
            unicornBuffer.getGraphics().drawImage(ImageIO.read(new File(currentRelativePath + "\\src\\Utilities\\Unicorn.png")), 0, 0, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillRidersHasMap() {

        for (int HorsesQty = 1; HorsesQty <= sequentialSpinnerValue; HorsesQty++) {
            riders.put("A" + HorsesQty, 0);
        }

        for (int HorsesQty = 1; HorsesQty <= concurrentSpinnerValue; HorsesQty++) {
            riders.put("B" + HorsesQty, 0);
        }

        for (int HorsesQty = 1; HorsesQty <= parallelSpinnerValue; HorsesQty++) {
            riders.put("C" + HorsesQty, 0);
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

}
