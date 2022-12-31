package Simulation;

import Simulation.AnimalInfo;

import World.WorldHandler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import Objects.Animal;
import Objects.Grass;
import Objects.Vector2d;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static java.lang.String.valueOf;
import static java.lang.System.out;
import static java.lang.Thread.sleep;

public class Simulation implements ActionListener {

    public final WorldHandler map;
    public boolean writeToCSV;
    public int delay;
    public String filename;
    JPanel panel;
    public int simNumber;
    public int animalInfoCount = 1;

    public java.util.List<AnimalInfo> animalInfoList = new ArrayList<>();

    private int dayCount=1;

    public int WIDTH = 1200;
    public final int HEIGHT = 1000;
    private final int  CELL = 15;
    private JLabel daLabel = new JLabel();
    private JPanel simGrid = new JPanel();
    BufferedImage animalPic = null;
    BufferedImage grassPic = null;
    Timer timer;


    public Simulation(WorldHandler map_, int delay_, boolean writeToCSV_, String filename_, int SimulationNumber){
        map = map_;
        delay = delay_;
        writeToCSV = writeToCSV_;
        filename = filename_;
        simNumber = SimulationNumber;

        if(writeToCSV){
            try {
                FileWriter clearer = new FileWriter(String.join("", ".\\reports\\", filename));
                clearer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void run() throws InterruptedException {
        JFrame settingsFrame = new JFrame(String.join(" ", "Evolution Simulator", Integer.toString(simNumber)));
        settingsFrame.setSize(1200, 1000);

        timer = new Timer(delay, this);
        timer.setActionCommand("update");
        timer.start();

        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                timer.stop();
            }
        });

        settingsFrame.setLocationRelativeTo(null);
        settingsFrame.setVisible(true);
        panel = new JPanel();
        settingsFrame.add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        try {
            animalPic = ImageIO.read(new File("src/main/resources/animal.png"));
            grassPic = ImageIO.read(new File("src/main/resources/grass.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        animalPic = this.resize(animalPic, CELL);
        grassPic = this.resize(grassPic, CELL);

        Border blackline = BorderFactory.createLineBorder(Color.black);

        JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainPanel.setResizeWeight(0.7);

        JPanel LeftPanel = new JPanel(new GridLayout(5, 1));
        LeftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        LeftPanel.setBorder(blackline);

        JSplitPane RightPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        RightPanel.setResizeWeight(1);

        mainPanel.add(LeftPanel);
        mainPanel.add(RightPanel);
        panel.add(mainPanel);

        JPanel daL = new JPanel();
        daL.setAlignmentX(Component.LEFT_ALIGNMENT);
        daL.add(daLabel);
        LeftPanel.add(daL);

        simGrid.setPreferredSize(new Dimension(15*map.getWidth(), 15*map.getHeight()));
        simGrid.setLayout(new GridLayout(map.getWidth(), map.getHeight()));
        simGrid.setBorder(blackline);
//        simGrid.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        RightPanel.add(simGrid);

        JPanel RightLowerPanel = new JPanel();
        RightLowerPanel.setLayout(new BoxLayout(RightLowerPanel, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        JButton pauseButton = new JButton("Pause");
        JButton unpauseButton = new JButton("Play");

        pauseButton.addActionListener(this);
        unpauseButton.addActionListener(this);

        pauseButton.setActionCommand("pause");
        unpauseButton.setActionCommand("play");

        pauseButton.setPreferredSize(new Dimension(150, 50));
        unpauseButton.setPreferredSize(new Dimension(150, 50));

        buttonPanel.add(pauseButton);
        buttonPanel.add(unpauseButton);

        RightLowerPanel.add(buttonPanel);

        JPanel legend = new JPanel(new GridLayout(2, 2));
        legend.setPreferredSize(new Dimension(100, 100));

        JPanel animal = new JPanel();
        JPanel grass = new JPanel();
        JPanel jungle = new JPanel();
        JPanel dune = new JPanel();

        BufferedImage myPicture = animalPic;
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        animal.add(picLabel);

        myPicture = grassPic;
        picLabel = new JLabel(new ImageIcon(myPicture));
        grass.add(picLabel);

        JPanel jungleSquare = new JPanel();
        JPanel duneSquare = new JPanel();

        jungleSquare.setPreferredSize(new Dimension(20, 20));
        duneSquare.setPreferredSize(new Dimension(20, 20));

        jungleSquare.setBackground(Color.orange);
        duneSquare.setBackground(Color.blue);

        jungle.add(jungleSquare);
        dune.add(duneSquare);

        jungle.add(new JLabel(" - Jungle Square"));
        dune.add(new JLabel(" - Dune Square"));
        animal.add(new JLabel(" - Animal"));
        grass.add(new JLabel(" - Grass"));

        legend.add(jungle);
        legend.add(dune);
        legend.add(animal);
        legend.add(grass);

        RightLowerPanel.add(legend);
        RightPanel.add(RightLowerPanel);

        // add graphs to leftpanel
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("update")) {

            panel.revalidate();
            panel.repaint();

            map.removeDead(dayCount);
            map.moveAnimals();
            map.eatGrass();
            map.animalKids(dayCount);
            map.growGrass();

            if(writeToCSV)
                map.writeToCSV(filename, dayCount);

            this.daLabel.setText("Dead Animals: " + valueOf(map.getDeadAnimalsCount()));

            simGrid.removeAll();
            for(int row = 0; row < map.getHeight(); row++) {
                for(int col = 0; col < map.getWidth(); col++) {
                    Vector2d pos = new Vector2d(col, row);
                    JPanel temp = new JPanel();
                    temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    temp.setPreferredSize(new Dimension(CELL,CELL));
                    if(map.isJungle(pos)) {
                        temp.setBackground(Color.ORANGE);
                    }
                    else {
                        temp.setBackground(Color.BLUE);
                    }
                    int id = map.isOccupied(pos);
                    if(id == 1){
                        BufferedImage myPicture = animalPic;
                        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                        temp.add(picLabel);
                    }
                    else if(id == 2) {
                        BufferedImage myPicture = grassPic;
                        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                        temp.add(picLabel);
                    }
                    simGrid.add(temp);
                }
            }

            // refresh animal infos
            for(AnimalInfo AI : animalInfoList){
                if(AI.isInfoActive()){
                    AI.refresh(dayCount);
                }
            }

            dayCount++;

        } else if (e.getActionCommand().equals("pause")) {

            timer.stop();
            for(int row = 0; row < map.getHeight(); row++) {
                for(int col = 0; col < map.getWidth(); col++) {
                    Vector2d pos = new Vector2d(col, row);
                    // has animal
                    if(map.isOccupied(pos) == 1){
                        // add button to panel
                        JPanel tempPanel = (JPanel) simGrid.getComponent(col + row * map.getWidth());
                        tempPanel.removeAll();

                        JButton button = new JButton();

                        button.setBackground(Color.red);

                        button.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent e) {
                                AnimalInfo AI = new AnimalInfo(map.getAnimalAt(pos));
                                if(AI.set(dayCount, simNumber, animalInfoCount++)){
                                    animalInfoList.add(AI);
                                }
                            }
                        });

                        tempPanel.add(button);
                        tempPanel.repaint();
                        tempPanel.revalidate();
                    }
                }
            }

        // else means start
        } else {
            timer.start();
        }

    }

    private BufferedImage resize(BufferedImage src, int targetSize) {
        if (targetSize <= 0) {
            return src; //this can't be resized
        }
        int targetWidth = targetSize;
        int targetHeight = targetSize;
        float ratio = ((float) src.getHeight() / (float) src.getWidth());
        if (ratio <= 1) { //square or landscape-oriented image
            targetHeight = (int) Math.ceil((float) targetWidth * ratio);
        } else { //portrait image
            targetWidth = Math.round((float) targetHeight / ratio);
        }
        BufferedImage bi = new BufferedImage(targetWidth, targetHeight, src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //produces a balanced resizing (fast and decent quality)
        g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return bi;
    }

}