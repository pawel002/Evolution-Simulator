package Simulation;

import Objects.Animal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AnimalInfo {

    Animal animal;
    private JPanel mainPanel;

    JLabel isAliveLabel;
    JLabel energyLabel;
    JLabel birthDateLabel;
    JLabel ageLabel;
    JLabel genomeLabel;
    JLabel eatenGrassLabel;
    JLabel numberOfKidsLabel;

    JLabel isAliveData;
    JLabel energyData;
    JLabel birthDateData;
    JLabel ageData;
    JLabel genomeData;
    JLabel eatenGrassData;
    JLabel numberOfKidsData;

    int isActive = 1;

    public AnimalInfo(Animal animal_) {
        animal = animal_;
    }


    public boolean set(int currDay, int simNumber_, int animalNumber){
        if(animal == null){
            return false;
        }

        JFrame mainFrame = new JFrame(String.join("", "ES ", Integer.toString(simNumber_), ", Animal ", Integer.toString(animalNumber)));
        mainFrame.setSize(300, 300);
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                isActive = 0;
            }
        });

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        mainPanel = new JPanel(new GridLayout(7, 1));
        mainFrame.add(mainPanel);

        isAliveLabel = new JLabel("Alive: ");
        energyLabel = new JLabel("Energy: ");
        birthDateLabel = new JLabel("Birth Date: ");
        ageLabel = new JLabel("Age: ");
        genomeLabel = new JLabel("Genome: ");
        eatenGrassLabel = new JLabel("Eaten grass: ");
        numberOfKidsLabel = new JLabel("Number of kids: ");

        if(animal.getDeathDate() == -1){
            isAliveData = new JLabel("YES");
        } else {
            isAliveData = new JLabel("NO");
        }

        energyData = new JLabel(String.join("", Integer.toString(animal.getCurrHealth()), " / ", Integer.toString(animal.getMaxHealth())));
        birthDateData = new JLabel(Integer.toString(animal.getBirthDate()));
        ageData = new JLabel(Integer.toString(animal.getAge(currDay)));
        genomeData = new JLabel(animal.getGenome());
        eatenGrassData = new JLabel(Integer.toString(animal.getGrassCount()));
        numberOfKidsData = new JLabel(Integer.toString(animal.getChildCount()));

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();
        JPanel panel7 = new JPanel();

        panel1.add(isAliveLabel);
        panel2.add(energyLabel);
        panel3.add(birthDateLabel);
        panel4.add(ageLabel);
        panel5.add(genomeLabel);
        panel6.add(eatenGrassLabel);
        panel7.add(numberOfKidsLabel);

        panel1.add(isAliveData);
        panel2.add(energyData);
        panel3.add(birthDateData);
        panel4.add(ageData);
        panel5.add(genomeData);
        panel6.add(eatenGrassData);
        panel7.add(numberOfKidsData);

        mainPanel.add(panel1);
        mainPanel.add(panel2);
        mainPanel.add(panel3);
        mainPanel.add(panel4);
        mainPanel.add(panel5);
        mainPanel.add(panel6);
        mainPanel.add(panel7);

        return true;
    }

    public boolean isInfoActive(){
        return isActive == 1;
    }

    public void refresh(int currDay){

        if(animal.getDeathDate() == -1){
            isAliveData.setText("YES");
        } else {
            isAliveData.setText("NO");
        }

        energyData.setText(String.join("", Integer.toString(animal.getCurrHealth()), " / ", Integer.toString(animal.getMaxHealth())));
        birthDateData.setText(Integer.toString(animal.getBirthDate()));
        ageData.setText(Integer.toString(animal.getAge(currDay)));
        genomeData.setText(animal.getGenome());
        eatenGrassData.setText(Integer.toString(animal.getGrassCount()));
        numberOfKidsData.setText(Integer.toString(animal.getChildCount()));

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
