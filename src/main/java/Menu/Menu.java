package Menu;

import javax.swing.*;

public class Menu {
    public JFrame settingsFrame;

    public Menu() {

        settingsFrame = new JFrame("Evolution Simulator (Settings)");
        settingsFrame.setSize(800, 800);
        settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingsFrame.setLocationRelativeTo(null);

    }
    public void startSimulation(Integer[] defaultMapProperties){
        settingsFrame.add(new Panel(defaultMapProperties));
        settingsFrame.setVisible(true);
    }
}
