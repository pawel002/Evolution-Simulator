package Menu;

import Save.Loader;
import Save.Saver;
import Simulation.Simulation;
import World.Settings;
import World.WorldHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Panel extends JPanel implements ActionListener {

    public static final int HEIGHT = 800;
    public static final int WIDTH = 1200;

    private int simulationCount = 1;


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
    private JTextField saveCSV;

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

    // genome
    private JLabel genomeSizeLabel;
    private JLabel mutationCoefficientLabel;
    private JLabel mutationTypeLabel;

    // save
    private JLabel saveLabel;
    private JLabel loadLabel;
    private JLabel saveCSVLabel;

    // button
    private JButton startButton;

    private List<Simulation> simList = new ArrayList<Simulation>();
    private List<WorldHandler> mapList = new ArrayList<WorldHandler>();

    // checkbox
    JCheckBox saveToCSVButton;

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
        startGrassCountLabel = new JLabel("Starting grass number (int):           ");
        growingGrassCountLabel = new JLabel("Grass growing every day (int):           ");
        grassEnergyLabel = new JLabel("Grass energy (int):           ");
        grassTypeLabel = new JLabel("'E' - EQUATOR, 'T' - TOXIC FIELDS:           ");

        // animal
        animalMaxEnergyLabel = new JLabel("Max animal energy (int):           ");
        dailyConsumptionLabel = new JLabel("Daily energy loss (int):           ");
        startAnimalCountLabel = new JLabel("Starting animals number (int):           ");
        startAnimalEnergyLabel = new JLabel("Starting animals energy (int):           ");
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
        saveCSVLabel = new JLabel("Save to CSV (check - yes):  ");

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
        save.setText("Enter save-file name");

        load = new JTextField();
        load.setColumns(SIZE);
        load.setText("Loaded default");

        saveCSV = new JTextField();
        saveCSV.setColumns(SIZE);
        saveCSV.setText("Enter report name");

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
        saveCSVLabel.setLabelFor(saveCSV);

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
        JPanel RightPanel = new JPanel(new GridLayout(14, 1));


        mainPanel.add(LeftPanel);
        mainPanel.add(RightPanel);

        add(mainPanel);

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        JPanel panel6 = new JPanel();
        JPanel panel7 = new JPanel();
        JPanel panel8 = new JPanel();
        JPanel panel9 = new JPanel();
        JPanel panel10 = new JPanel();
        JPanel panel11 = new JPanel();
        JPanel panel12 = new JPanel();
        JPanel panel13 = new JPanel();
        JPanel panel14 = new JPanel();
        JPanel panel15 = new JPanel();
        JPanel panel16 = new JPanel();
        JPanel panel17 = new JPanel();
        JPanel panel18 = new JPanel();
        JPanel panel19 = new JPanel();
        JPanel panel20 = new JPanel();
        JPanel panel21 = new JPanel();


        panel1.add(delayLabel);
        panel2.add(widthLabel);
        panel3.add(heightLabel);
        panel4.add(worldTypeLabel);
        panel5.add(startGrassCountLabel);
        panel6.add(growingGrassCountLabel);
        panel7.add(grassEnergyLabel);
        panel8.add(grassTypeLabel);
        panel9.add(animalMaxEnergyLabel);
        panel10.add(dailyConsumptionLabel);
        panel11.add(startAnimalCountLabel);
        panel12.add(startAnimalEnergyLabel);
        panel13.add(birthEnergyLossLabel);
        panel14.add(animalReadyEnergyLabel);
        panel15.add(animalTypeLabel);
        panel16.add(genomeSizeLabel);
        panel17.add(mutationCoefficientLabel);
        panel18.add(mutationTypeLabel);
        panel19.add(saveLabel);
        panel20.add(loadLabel);
        panel21.add(saveCSVLabel);

        panel1.add(delay);
        panel2.add(width);
        panel3.add(height);
        panel4.add(worldType);
        panel5.add(startGrassCount);
        panel6.add(growingGrassCount);
        panel7.add(grassEnergy);
        panel8.add(grassType);
        panel9.add(animalMaxEnergy);
        panel10.add(dailyConsumption);
        panel11.add(startAnimalCount);
        panel12.add(startAnimalEnergy);
        panel13.add(birthEnergyLoss);
        panel14.add(animalReadyEnergy);
        panel15.add(animalType);
        panel16.add(genomeSize);
        panel17.add(mutationCoefficient);
        panel18.add(mutationType);
        panel19.add(save);
        panel20.add(load);

        saveToCSVButton = new JCheckBox();
        saveToCSVButton.setSelected(false);

        panel21.add(saveToCSVButton);
        panel21.add(saveCSV);

        panel19.add(saveButton);
        panel20.add(loadButton);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);


        JPanel simSettings = new JPanel();
        simSettings.add(new JLabel("Simulation settings"));
        LeftPanel.add(simSettings);
        LeftPanel.add(panel1);

        JPanel mapSettings = new JPanel();
        mapSettings.add(new JLabel("Map settings"));
        LeftPanel.add(mapSettings);
        LeftPanel.add(panel2);
        LeftPanel.add(panel3);
        LeftPanel.add(panel4);

        JPanel grassSettings = new JPanel();
        grassSettings.add(new JLabel("Grass settings"));
        RightPanel.add(grassSettings);
        RightPanel.add(panel5);
        RightPanel.add(panel6);
        RightPanel.add(panel7);
        RightPanel.add(panel8);

        JPanel animalSettings = new JPanel();
        animalSettings.add(new JLabel("Animal settings"));
        LeftPanel.add(animalSettings);
        LeftPanel.add(panel9);
        LeftPanel.add(panel10);
        LeftPanel.add(panel11);
        LeftPanel.add(panel12);
        LeftPanel.add(panel13);
        LeftPanel.add(panel14);
        LeftPanel.add(panel15);

        JPanel mutationSettings = new JPanel();
        mutationSettings.add(new JLabel("Mutation settings"));
        RightPanel.add(mutationSettings);
        RightPanel.add(panel16);
        RightPanel.add(panel17);
        RightPanel.add(panel18);

        JPanel saveSettings = new JPanel();
        saveSettings.add(new JLabel("SAVE / LOAD"));
        RightPanel.add(saveSettings);
        RightPanel.add(panel19);
        RightPanel.add(panel20);
        RightPanel.add(panel21);

        RightPanel.add(buttonPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("start")) {

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

            out.println(String.join(" ", "Started Simulation Number", Integer.toString(simulationCount)));
            Simulation sim = new Simulation(map, Integer.parseInt(delay.getText()), saveToCSVButton.isSelected(), saveCSV.getText(), simulationCount++);
            try {
                sim.run();
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }


        } else if (e.getActionCommand().equals("save")) {
            String filename = save.getText();
            if (filename.equals("default")) {
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

            out.print("Loaded Successfully");
        }
    }
}
