import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class GUI extends JFrame {

    public static HashMap<String, Integer> riders = new HashMap<>();

    private boolean isRuning = false;

    JPanel secuencialPanel, concurrentPanel, parallelPanel;

    JSpinner secuencialSpinner, concurrentSpinner, parallelSpinner;

    public GUI() {
        super("Hipodromo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(0, 1));
        setSize(250, 200);
        setVisible(true);
        setResizable(false);
        secuencialPanel = new JPanel();
        concurrentPanel = new JPanel();
        parallelPanel = new JPanel();

        secuencialPanel.add(new JLabel("Secunecial"));
        JSpinner secuencialSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 3, 1));
        secuencialPanel.add(secuencialSpinner);


        concurrentPanel.add(new JLabel("Concurrent"));
        JSpinner concurrentSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 3, 1));
        concurrentPanel.add(concurrentSpinner);


        parallelPanel.add(new JLabel("Parallel"));
        JSpinner parallelSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 3, 1));
        parallelPanel.add(parallelSpinner);

        Button starRide = new Button("Start");
        starRide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isRuning = true;
                ChangeInitialLayout(secuencialSpinner, concurrentSpinner, parallelSpinner);
            }
        });

        add(secuencialPanel);
        add(concurrentPanel);
        add(parallelPanel);
        add(starRide);
    }

    @Override
    public void paint(Graphics g) {
        if (isRuning) {
            paintBackgroud();
        }

    }

    private void ChangeInitialLayout(JSpinner secuentialSpinner, JSpinner concurrentSpinner, JSpinner parallelSpinner) {
        this.removeAll();

        for (int secuentialHorsesQty = 1; secuentialHorsesQty <= (int) secuentialSpinner.getValue(); secuentialHorsesQty++) {
            riders.put("S" + secuentialHorsesQty, 0);
        }

        for (int secuentialHorsesQty = 1; secuentialHorsesQty <= (int) secuentialSpinner.getValue(); secuentialHorsesQty++) {
            riders.put("C" + secuentialHorsesQty, 0);
        }

        for (int secuentialHorsesQty = 1; secuentialHorsesQty <= (int) secuentialSpinner.getValue(); secuentialHorsesQty++) {
            riders.put("P" + secuentialHorsesQty, 0);
        }


        setSize(1200,  + 20);
        paintBackgroud();


    }

    public void paintBackgroud() {

        BufferedImage runwayImage = new BufferedImage(1200, 100, BufferedImage.TYPE_INT_RGB);
        BufferedImage horseImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);

        int currentYAxis = 20;

        for (int paintedHorse = 1; paintedHorse <= secuentialHorses; paintedHorse++) {
            try {
                runwayImage = ImageIO.read(new File("" +
                        "C:\\Users\\235921\\Documents\\Github\\src\\Utilities\\S" + paintedHorse + ".png"));
                horseImage = ImageIO.read(new File("" +
                        "C:\\Users\\235921\\Documents\\Github\\src\\Utilities\\Donky.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.getGraphics().drawImage(runwayImage, 0, currentYAxis + 1, this);
            this.getGraphics().drawImage(horseImage, 0, currentYAxis + 1, this);

            currentYAxis += 100;
        }

        for (int paintedHorse = 1; paintedHorse <= concurrentHorses; paintedHorse++) {

            try {
                runwayImage = ImageIO.read(new File("" +
                        "C:\\Users\\235921\\Documents\\Github\\src\\Utilities\\C" + paintedHorse + ".png"));
                horseImage = ImageIO.read(new File("" +
                        "C:\\Users\\235921\\Documents\\Github\\src\\Utilities\\Horse.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.getGraphics().drawImage(runwayImage, 0, currentYAxis, this);
            this.getGraphics().drawImage(horseImage, 0, currentYAxis + 1, this);
            currentYAxis += 100;
        }

        for (int paintedHorse = 1; paintedHorse <= parallelHorses; paintedHorse++) {

            try {
                runwayImage = ImageIO.read(new File("" +
                        "C:\\Users\\235921\\Documents\\Github\\src\\Utilities\\P" + paintedHorse + ".png"));
                horseImage = ImageIO.read(new File("" +
                        "C:\\Users\\235921\\Documents\\Github\\src\\Utilities\\Unicorn.png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.getGraphics().drawImage(runwayImage, 0, currentYAxis, this);
            this.getGraphics().drawImage(horseImage, 0, currentYAxis + 1, this);
            currentYAxis += 100;
        }
    }

}
