import javax.swing.*;
import java.awt.*;
import java.nio.file.Paths;
import java.util.Random;

public class ConfigGUI extends JFrame {

    public ConfigGUI(){
        super("Configure Hippodrome");
        setPreferredSize(new Dimension(400, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);

        //Initialice variables
        JPanel mainPanel = new JPanel(new BorderLayout());

        //Parte Superior del BorderLayout
        JLabel Configuracion = new JLabel("Configuration");

        //Parte central del borderLayout
        JPanel configurationPanel = new JPanel(new GridLayout(4, 1));

        JLabel matrizSizeLabel = new JLabel("Matriz Size:");
        configurationPanel.add(matrizSizeLabel);

        JTextField matrizSizeTextField = new JTextField(20);
        configurationPanel.add(matrizSizeTextField);

        JLabel serverPortLabel = new JLabel("Server Port:");
        configurationPanel.add(serverPortLabel);

        JTextField serverPortField = new JTextField(20);
        configurationPanel.add(serverPortField);


        //Parte inferior
        JPanel buttonPanel = new JPanel();
        Button starRideButton = new Button("Start");
        buttonPanel.add(starRideButton);


        mainPanel.add(Configuracion, BorderLayout.NORTH);
        mainPanel.add(configurationPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        starRideButton.addActionListener((actionEvent) -> {
            this.setVisible(false);
            new GUI(Integer.parseInt(matrizSizeTextField.getText().trim()),  Integer.parseInt(serverPortField.getText().trim()));
        });

        pack();

    }
}
