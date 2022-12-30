package Menu;

import Save.Loader;
import Save.Saver;
import World.Settings;
import World.WorldHandler;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.System.out;

public class Panel extends JPanel implements ActionListener {

    public static final int HEIGHT = 800;
    public static final int WIDTH = 1200;


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

    private JTextField save;
    private JTextField load;


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

    private JLabel saveLabel;
    private JLabel loadLabel;

    //button
    private JButton startButton;

    public Panel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

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

        // SAVING
        saveLabel = new JLabel("Save (filename - string):           ");
        loadLabel = new JLabel("Load (filename - string):           ");

        // LOAD DEFAULT !!!
        Loader defaultLoader = new Loader();
        try {
            defaultLoader.read("default");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int SIZE = 18;
        delay = new JTextField();
        delay.setColumns(SIZE);
        delay.setText(Integer.toString(defaultLoader.getDelay()));

        width = new JTextField();
        width.setColumns(SIZE);
        width.setText(Integer.toString(defaultLoader.getWidth()));

        height = new JTextField();
        height.setColumns(SIZE);
        height.setText(Integer.toString(defaultLoader.getHeight()));

        worldType = new JTextField();
        worldType.setColumns(SIZE);
        worldType.setText(defaultLoader.getWorldType().toString());

        startGrassCount = new JTextField();
        startGrassCount.setColumns(SIZE);
        startGrassCount.setText(Integer.toString(defaultLoader.getStartGrassCount()));

        growingGrassCount = new JTextField();
        growingGrassCount.setColumns(SIZE);
        growingGrassCount.setText(Integer.toString(defaultLoader.getGrowingGrassCount()));

        grassEnergy = new JTextField();
        grassEnergy.setColumns(SIZE);
        grassEnergy.setText(Integer.toString(defaultLoader.getGrassEnergy()));

        grassType = new JTextField();
        grassType.setColumns(SIZE);
        grassType.setText(defaultLoader.getGrassType().toString());

        animalMaxEnergy = new JTextField();
        animalMaxEnergy.setColumns(SIZE);
        animalMaxEnergy.setText(Integer.toString(defaultLoader.getAnimalMaxEnergy()));

        dailyConsumption = new JTextField();
        dailyConsumption.setColumns(SIZE);
        dailyConsumption.setText(Integer.toString(defaultLoader.getDailyConsumption()));

        startAnimalCount = new JTextField();
        startAnimalCount.setColumns(SIZE);
        startAnimalCount.setText(Integer.toString(defaultLoader.getStartAnimalCount()));

        startAnimalEnergy = new JTextField();
        startAnimalEnergy.setColumns(SIZE);
        startAnimalEnergy.setText(Integer.toString(defaultLoader.getStartAnimalEnergy()));

        birthEnergyLoss = new JTextField();
        birthEnergyLoss.setColumns(SIZE);
        birthEnergyLoss.setText(Integer.toString(defaultLoader.getBirthEnergyLoss()));

        animalReadyEnergy = new JTextField();
        animalReadyEnergy.setColumns(SIZE);
        animalReadyEnergy.setText(Integer.toString(defaultLoader.getAnimalReadyEnergy()));

        animalType = new JTextField();
        animalType.setColumns(SIZE);
        animalType.setText(defaultLoader.getAnimalType().toString());

        genomeSize = new JTextField();
        genomeSize.setColumns(SIZE);
        genomeSize.setText(Integer.toString(defaultLoader.getGenomeSize()));

        mutationCoefficient = new JTextField();
        mutationCoefficient.setColumns(SIZE);
        mutationCoefficient.setText(Integer.toString(defaultLoader.getMutationCoefficient()));

        mutationType = new JTextField();
        mutationType.setColumns(SIZE);
        mutationType.setText(defaultLoader.getMutationType().toString());

        save = new JTextField();
        save.setColumns(SIZE);
        save.setText("Enter name to save");

        load = new JTextField();
        load.setColumns(SIZE);
        load.setText("Loaded default");

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

        startButton = new JButton("Start Simulation");

        saveLabel.setLabelFor(save);
        loadLabel.setLabelFor(load);

        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        saveButton.setActionCommand("save");
        loadButton.setActionCommand("load");
        startButton.setActionCommand("start");

        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        startButton.addActionListener(this);

        JPanel mainPanel = new JPanel(new GridLayout(1, 2));

        JPanel LeftPanel = new JPanel(new GridLayout(14, 1));
        LeftPanel.setAlignmentX(CENTER_ALIGNMENT);
        JPanel RightPanel = new JPanel(new GridLayout(13, 1));


        mainPanel.add(LeftPanel);
        mainPanel.add(RightPanel);

        add(mainPanel);

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
        JPanel l19 = new JPanel();
        JPanel l20 = new JPanel();


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
        l19.add(saveLabel);
        l20.add(loadLabel);

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
        l19.add(save);
        l20.add(load);

        l19.add(saveButton);
        l20.add(loadButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);


        JPanel simSettings = new JPanel();
        simSettings.add(new JLabel("Simulation settings"));
        LeftPanel.add(simSettings);
        LeftPanel.add(l1);

        JPanel mapSettings = new JPanel();
        mapSettings.add(new JLabel("Map settings"));
        LeftPanel.add(mapSettings);
        LeftPanel.add(l2);
        LeftPanel.add(l3);
        LeftPanel.add(l4);

        JPanel grassSettings = new JPanel();
        grassSettings.add(new JLabel("Grass settings"));
        RightPanel.add(grassSettings);
        RightPanel.add(l5);
        RightPanel.add(l6);
        RightPanel.add(l7);
        RightPanel.add(l8);

        JPanel animalSettings = new JPanel();
        animalSettings.add(new JLabel("Animal settings"));
        LeftPanel.add(animalSettings);
        LeftPanel.add(l9);
        LeftPanel.add(l10);
        LeftPanel.add(l11);
        LeftPanel.add(l12);
        LeftPanel.add(l13);
        LeftPanel.add(l14);
        LeftPanel.add(l15);

        JPanel mutationSettings = new JPanel();
        mutationSettings.add(new JLabel("Mutation settings"));
        RightPanel.add(mutationSettings);
        RightPanel.add(l16);
        RightPanel.add(l17);
        RightPanel.add(l18);

        JPanel saveSettings = new JPanel();
        saveSettings.add(new JLabel("SAVE / LOAD"));
        RightPanel.add(saveSettings);
        RightPanel.add(l19);
        RightPanel.add(l20);

        RightPanel.add(buttonPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("start")){

            WorldHandler map = new WorldHandler(
                    Integer.parseInt(width.getText()),
                    Integer.parseInt(height.getText()),
                    Settings.getWorldType(worldType.getText()),

                    Integer.parseInt(startGrassCount.getText()),
                    Integer.parseInt(growingGrassCount.getText()),
                    Integer.parseInt(grassEnergy.getText()),
                    Settings.getGrassType(grassType.getText()),

                    Integer.parseInt(animalMaxEnergy.getText()),
                    Integer.parseInt(startAnimalCount.getText()),
                    Integer.parseInt(startAnimalEnergy.getText()),
                    Integer.parseInt(animalReadyEnergy.getText()),
                    Integer.parseInt(birthEnergyLoss.getText()),
                    Integer.parseInt(dailyConsumption.getText()),
                    Settings.getAnimalType(animalType.getText()),

                    Integer.parseInt(genomeSize.getText()),
                    Integer.parseInt(mutationCoefficient.getText()),
                    Settings.getMutationType(mutationType.getText())
            );

//        MapSimulation simulation = new MapSimulation(
//                map, Integer.parseInt(delay.getText()),
//                Integer.parseInt(numOfSpawnedAnimals.getText()),
//                Integer.parseInt(grassSpawnedInEachDay.getText()));
//        simulation.startSimulation();

        } else if (e.getActionCommand().equals("save")) {
            String filename = save.getText();
            if(filename.equals("default")){
                out.print("Cannot overwrite default");
                return;
            }
            Saver customSaver = new Saver(
                    Integer.parseInt(delay.getText()),
                    Integer.parseInt(width.getText()),
                    Integer.parseInt(height.getText()),
                    Settings.getWorldType(worldType.getText()),

                    Integer.parseInt(startGrassCount.getText()),
                    Integer.parseInt(growingGrassCount.getText()),
                    Integer.parseInt(grassEnergy.getText()),
                    Settings.getGrassType(grassType.getText()),

                    Integer.parseInt(animalMaxEnergy.getText()),
                    Integer.parseInt(startAnimalCount.getText()),
                    Integer.parseInt(startAnimalEnergy.getText()),
                    Integer.parseInt(animalReadyEnergy.getText()),
                    Integer.parseInt(birthEnergyLoss.getText()),
                    Integer.parseInt(dailyConsumption.getText()),
                    Settings.getAnimalType(animalType.getText()),

                    Integer.parseInt(genomeSize.getText()),
                    Integer.parseInt(mutationCoefficient.getText()),
                    Settings.getMutationType(mutationType.getText())
            );

            try {
                customSaver.save(filename);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            out.print("Saved Successfully");

        } else {
            // LOAD
            String filename = load.getText();
            Loader customLoader = new Loader();
            try {
                customLoader.read(filename);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }

            delay.setText(Integer.toString(customLoader.getDelay()));
            width.setText(Integer.toString(customLoader.getWidth()));
            height.setText(Integer.toString(customLoader.getHeight()));
            worldType.setText(customLoader.getWorldType().toString());
            startGrassCount.setText(Integer.toString(customLoader.getStartGrassCount()));
            growingGrassCount.setText(Integer.toString(customLoader.getGrowingGrassCount()));
            grassEnergy.setText(Integer.toString(customLoader.getGrassEnergy()));
            grassType.setText(customLoader.getGrassType().toString());
            animalMaxEnergy.setText(Integer.toString(customLoader.getAnimalMaxEnergy()));
            dailyConsumption.setText(Integer.toString(customLoader.getDailyConsumption()));
            startAnimalCount.setText(Integer.toString(customLoader.getStartAnimalCount()));
            startAnimalEnergy.setText(Integer.toString(customLoader.getStartAnimalEnergy()));
            birthEnergyLoss.setText(Integer.toString(customLoader.getBirthEnergyLoss()));
            animalReadyEnergy.setText(Integer.toString(customLoader.getAnimalReadyEnergy()));
            animalType.setText(customLoader.getAnimalType().toString());
            genomeSize.setText(Integer.toString(customLoader.getGenomeSize()));
            mutationCoefficient.setText(Integer.toString(customLoader.getMutationCoefficient()));
            mutationType.setText(customLoader.getMutationType().toString());

        }

    }
}
