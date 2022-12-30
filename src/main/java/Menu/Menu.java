package Menu;

import javax.swing.*;

public class Menu {
    public JFrame settingsFrame;

    public Menu() {

        settingsFrame = new JFrame("Evolution Simulator (Settings)");
        settingsFrame.setSize(1000, 600);
        settingsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settingsFrame.setLocationRelativeTo(null);

    }

    public void openMenu(){
        settingsFrame.add(new Panel());
        settingsFrame.setVisible(true);
    }
}
