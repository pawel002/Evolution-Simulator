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

        JPanel l1 = new JPanel();
        JPanel l2 = new JPanel();
        JPanel l3 = new JPanel();
        JPanel l4 = new JPanel();
        JPanel l5 = new JPanel();
        JPanel l6 = new JPanel();
        JPanel l7 = new JPanel();

        l1.add(isAliveLabel);
        l2.add(energyLabel);
        l3.add(birthDateLabel);
        l4.add(ageLabel);
        l5.add(genomeLabel);
        l6.add(eatenGrassLabel);
        l7.add(numberOfKidsLabel);

        l1.add(isAliveData);
        l2.add(energyData);
        l3.add(birthDateData);
        l4.add(ageData);
        l5.add(genomeData);
        l6.add(eatenGrassData);
        l7.add(numberOfKidsData);

        mainPanel.add(l1);
        mainPanel.add(l2);
        mainPanel.add(l3);
        mainPanel.add(l4);
        mainPanel.add(l5);
        mainPanel.add(l6);
        mainPanel.add(l7);

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
