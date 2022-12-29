package Menu;

import World.Settings;
import World.WorldHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Panel extends JPanel implements ActionListener {

    public static final int HEIGHT = 800;
    public static final int WIDTH = 800;


    // simulation options
    private JTextField delay;

    // world options
    private JTextField width;
    private JTextField height;
    private JTextField worldType;

    // grass options
    private JTextField startGrassCount;
    private JTextField growingGrassCount;
    private JTextField grassEnergy;
    private JTextField grassType;

    // animal options
    private JTextField animalMaxEnergy;
    private JTextField dailyConsumption;
    private JTextField startAnimalCount;
    private JTextField startAnimalEnergy;
    private JTextField birthEnergyLoss;
    private JTextField animalReadyEnergy;
    private JTextField animalType;

    //
    private JTextField genomeSize;
    private JTextField mutationCoefficient;
    private JTextField mutationType;


    // simulation options
    private JLabel delayLabel;

    // world options
    private JLabel widthLabel;
    private JLabel heightLabel;
    private JLabel worldTypeLabel;


    // grass options
    private JLabel startGrassCountLabel;
    private JLabel growingGrassCountLabel;
    private JLabel grassEnergyLabel;
    private JLabel grassTypeLabel;

    // animal options
    private JLabel animalMaxEnergyLabel;
    private JLabel dailyConsumptionLabel;
    private JLabel startAnimalCountLabel;
    private JLabel startAnimalEnergyLabel;
    private JLabel birthEnergyLossLabel;
    private JLabel animalReadyEnergyLabel;
    private JLabel animalTypeLabel;

    //
    private JLabel genomeSizeLabel;
    private JLabel mutationCoefficientLabel;
    private JLabel mutationTypeLabel;

    //button
    private JButton startButton;

    public Panel(Integer[] defaultMapProperties) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        startButton = new JButton("Start Simulation");
        startButton.addActionListener(this);

        //LABELS
        //sim
        delayLabel = new JLabel("Real refresh time (ms):           ");

        // world
        widthLabel = new JLabel("Width (units):           ");
        heightLabel = new JLabel("Height (units):           ");
        worldTypeLabel = new JLabel("'E' - EARTH, 'P' - PORTAL:           ");

        // grass
        startGrassCountLabel = new JLabel("Staring grass number (int):           ");
        growingGrassCountLabel = new JLabel("Grass growing every day (int):           ");
        grassEnergyLabel = new JLabel("Energy of 1 grass (int):           ");
        grassTypeLabel = new JLabel("'E' - EQUATOR, 'T' - TOXIC FIELDS:           ");

        // animal
        animalMaxEnergyLabel = new JLabel("Max animal energy (int):           ");
        dailyConsumptionLabel = new JLabel("Daily energy loss (int):           ");
        startAnimalCountLabel = new JLabel("Staring animals number (int):           ");
        startAnimalEnergyLabel = new JLabel("Staring animals energy (int):           ");
        birthEnergyLossLabel = new JLabel("Energy loss when giving birth (int):           ");
        animalReadyEnergyLabel = new JLabel("Energy required to be ready for birth (int):           ");
        animalTypeLabel = new JLabel("'R' - RANDOM, 'P' - PREDESTINATION:           ");

        // genome
        genomeSizeLabel = new JLabel("Genome size (int):           ");
        mutationCoefficientLabel = new JLabel("Mutation size (int):           ");
        mutationTypeLabel = new JLabel("'R' - RANDOM, 'C' - CORRECTION:           ");

        int SIZE = 18;
        delay = new JTextField();
        delay.setColumns(SIZE);
//        delay.setText(defaultMapProperties[0].toString());

        width = new JTextField();
        width.setColumns(SIZE);
//        width.setText(defaultMapProperties[1].toString());

        height = new JTextField();
        height.setColumns(SIZE);
//        height.setText(defaultMapProperties[2].toString());

        worldType = new JTextField();
        worldType.setColumns(SIZE);
//        worldType.setText(defaultMapProperties[3].toString());

        startGrassCount = new JTextField();
        startGrassCount.setColumns(SIZE);
//        startGrassCount.setText(defaultMapProperties[4].toString());

        growingGrassCount = new JTextField();
        growingGrassCount.setColumns(SIZE);
//        growingGrassCount.setText(defaultMapProperties[5].toString());

        grassEnergy = new JTextField();
        grassEnergy.setColumns(SIZE);
//        grassEnergy.setText(defaultMapProperties[6].toString());

        grassType = new JTextField();
        grassType.setColumns(SIZE);
//        grassType.setText(defaultMapProperties[7].toString());

        animalMaxEnergy = new JTextField();
        animalMaxEnergy.setColumns(SIZE);
//        animalMaxEnergy.setText(defaultMapProperties[8].toString());

        dailyConsumption = new JTextField();
        dailyConsumption.setColumns(SIZE);
//        dailyConsumption.setText(defaultMapProperties[9].toString());

        startAnimalCount = new JTextField();
        startAnimalCount.setColumns(SIZE);
//        startAnimalCount.setText(defaultMapProperties[10].toString());

        startAnimalEnergy = new JTextField();
        startAnimalEnergy.setColumns(SIZE);
//        startAnimalEnergy.setText(defaultMapProperties[11].toString());

        birthEnergyLoss = new JTextField();
        birthEnergyLoss.setColumns(SIZE);
//        birthEnergyLoss.setText(defaultMapProperties[12].toString());

        animalReadyEnergy = new JTextField();
        animalReadyEnergy.setColumns(SIZE);
//        animalReadyEnergy.setText(defaultMapProperties[13].toString());

        animalType = new JTextField();
        animalType.setColumns(SIZE);
//        animalType.setText(defaultMapProperties[14].toString());

        genomeSize = new JTextField();
        genomeSize.setColumns(SIZE);
//        genomeSize.setText(defaultMapProperties[15].toString());

        mutationCoefficient = new JTextField();
        mutationCoefficient.setColumns(SIZE);
//        mutationCoefficient.setText(defaultMapProperties[16].toString());

        mutationType = new JTextField();
        mutationType.setColumns(SIZE);
//        mutationType.setText(defaultMapProperties[17].toString());

        //Labels to text fields
        delayLabel.setLabelFor(delay);

        widthLabel.setLabelFor(width);
        heightLabel.setLabelFor(height);
        worldTypeLabel.setLabelFor(worldType);

        startGrassCountLabel.setLabelFor(startGrassCount);
        growingGrassCountLabel.setLabelFor(growingGrassCount);
        grassEnergyLabel.setLabelFor(grassEnergy);
        grassTypeLabel.setLabelFor(grassType);

        animalMaxEnergyLabel.setLabelFor(animalMaxEnergy);
        dailyConsumptionLabel.setLabelFor(dailyConsumption);
        startAnimalCountLabel.setLabelFor(startAnimalCount);
        startAnimalEnergyLabel.setLabelFor(startAnimalEnergy);
        birthEnergyLossLabel.setLabelFor(birthEnergyLoss);
        animalReadyEnergyLabel.setLabelFor(animalReadyEnergy);
        animalTypeLabel.setLabelFor(animalType);

        genomeSizeLabel.setLabelFor(genomeSize);
        mutationCoefficientLabel.setLabelFor(mutationCoefficient);
        mutationTypeLabel.setLabelFor(mutationType);

        JPanel l1 = new JPanel();
        JPanel l2 = new JPanel();
        JPanel l3 = new JPanel();
        JPanel l4 = new JPanel();
        JPanel l5 = new JPanel();
        JPanel l6 = new JPanel();
        JPanel l7 = new JPanel();
        JPanel l8 = new JPanel();
        JPanel l9 = new JPanel();
        JPanel l10 = new JPanel();
        JPanel l11 = new JPanel();
        JPanel l12 = new JPanel();
        JPanel l13 = new JPanel();
        JPanel l14 = new JPanel();
        JPanel l15 = new JPanel();
        JPanel l16 = new JPanel();
        JPanel l17 = new JPanel();
        JPanel l18 = new JPanel();


        l1.add(delayLabel);
        l2.add(widthLabel);
        l3.add(heightLabel);
        l4.add(worldTypeLabel);
        l5.add(startGrassCountLabel);
        l6.add(growingGrassCountLabel);
        l7.add(grassEnergyLabel);
        l8.add(grassTypeLabel);
        l9.add(animalMaxEnergyLabel);
        l10.add(dailyConsumptionLabel);
        l11.add(startAnimalCountLabel);
        l12.add(startAnimalEnergyLabel);
        l13.add(birthEnergyLossLabel);
        l14.add(animalReadyEnergyLabel);
        l15.add(animalTypeLabel);
        l16.add(genomeSizeLabel);
        l17.add(mutationCoefficientLabel);
        l18.add(mutationTypeLabel);

        l1.add(delay);
        l2.add(width);
        l3.add(height);
        l4.add(worldType);
        l5.add(startGrassCount);
        l6.add(growingGrassCount);
        l7.add(grassEnergy);
        l8.add(grassType);
        l9.add(animalMaxEnergy);
        l10.add(dailyConsumption);
        l11.add(startAnimalCount);
        l12.add(startAnimalEnergy);
        l13.add(birthEnergyLoss);
        l14.add(animalReadyEnergy);
        l15.add(animalType);
        l16.add(genomeSize);
        l17.add(mutationCoefficient);
        l18.add(mutationType);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);

        add(new JLabel("Simulation settings"));
        add(l1);

        add(new JLabel("Map settings"));
        add(l2);
        add(l3);
        add(l4);

        add(new JLabel("Grass settings"));
        add(l5);
        add(l6);
        add(l7);
        add(l8);

        add(new JLabel("Animal settings"));
        add(l9);
        add(l10);
        add(l11);
        add(l12);
        add(l13);
        add(l14);
        add(l15);

        add(new JLabel("Mutation settings"));
        add(l16);
        add(l17);
        add(l18);

        add(buttonPanel);

    }

    public Settings.WorldType getWorldType(String str){
        if(str.equals("E"))
            return  Settings.WorldType.EARTH;
        else if (str.equals("P"))
            return  Settings.WorldType.PORTAL;
        throw new IllegalArgumentException(String.join(" ", "World type wrong argument:", str, ". Can only be E and P."));
    }

    public Settings.GrassType getGrassType(String str){
        if(str.equals("E"))
            return  Settings.GrassType.EQUATOR;
        else if (str.equals("T"))
            return  Settings.GrassType.TOXIC;
        throw new IllegalArgumentException(String.join(" ", "Grass type wrong argument:", str, ". Can only be E and T."));
    }

    public Settings.AnimalType getAnimalType(String str){
        if(str.equals("R"))
            return  Settings.AnimalType.RANDOM;
        else if (str.equals("P"))
            return  Settings.AnimalType.PREDESTINATION;
        throw new IllegalArgumentException(String.join(" ", "Animal type wrong argument:", str, ". Can only be P and R."));
    }

    public Settings.MutationType getMutationType(String str){
        if(str.equals("R"))
            return  Settings.MutationType.RANDOM;
        else if (str.equals("C"))
            return  Settings.MutationType.CORRECTION;
        throw new IllegalArgumentException(String.join(" ", "Mutation type wrong argument:", str, ". Can only be C and R."));
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        WorldHandler map = new WorldHandler(
                Integer.parseInt(width.getText()),
                Integer.parseInt(height.getText()),
                getWorldType(worldType.getText()),

                Integer.parseInt(startGrassCount.getText()),
                Integer.parseInt(growingGrassCount.getText()),
                Integer.parseInt(grassEnergy.getText()),
                getGrassType(grassType.getText()),

                Integer.parseInt(animalMaxEnergy.getText()),
                Integer.parseInt(startAnimalCount.getText()),
                Integer.parseInt(startAnimalEnergy.getText()),
                Integer.parseInt(animalReadyEnergy.getText()),
                Integer.parseInt(birthEnergyLoss.getText()),
                Integer.parseInt(dailyConsumption.getText()),
                getAnimalType(animalType.getText()),

                Integer.parseInt(genomeSize.getText()),
                Integer.parseInt(mutationCoefficient.getText()),
                getMutationType(mutationType.getText())
        );

        MapSimulation simulation = new MapSimulation(
                map, Integer.parseInt(delay.getText()),
                Integer.parseInt(numOfSpawnedAnimals.getText()),
                Integer.parseInt(grassSpawnedInEachDay.getText()));
        simulation.startSimulation();

    }
}
