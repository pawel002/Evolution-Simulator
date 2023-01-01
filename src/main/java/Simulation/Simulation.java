package Simulation;

import Objects.Animal;
import Objects.Vector2d;
import World.WorldHandler;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler;

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
import java.util.List;

import static java.lang.String.valueOf;

public class Simulation implements ActionListener {

    public final WorldHandler map;
    public boolean writeToCSV;
    public int delay;
    public String filename;
    JPanel panel;
    public int simNumber;
    public int animalInfoCount = 1;

    public List<AnimalInfo> animalInfoList = new ArrayList<>();

    private int dayCount=1;

    public int WIDTH = 1600;
    public final int HEIGHT = 1000;
    private final int  CELL = 15;
    private Stats statsPanel;
    public JFrame mainFrame;
    private JLabel daLabel = new JLabel();
    private JPanel simGrid = new JPanel();
    BufferedImage animalPic = null;
    BufferedImage grassPic = null;
    Timer timer;

    private org.knowm.xchart.XYChart chart;
    private List<Integer> animalCount = new ArrayList<>();
    private List<Integer> grassCount = new ArrayList<>();
    private List<Integer> freeCount = new ArrayList<>();

    public Simulation(WorldHandler map_, int delay_, boolean writeToCSV_, String filename_, int SimulationNumber){
        map = map_;
        delay = delay_;
        writeToCSV = writeToCSV_;
        filename = filename_;
        simNumber = SimulationNumber;

        if(writeToCSV){
            try {
                FileWriter clearer = new FileWriter(String.join("", ".\\reports\\", filename));
                clearer.write(String.join(",", "DAY", "ANIMAL COUNT", "GRASS COUNT", "FREE SPACES", "AVERAGE ENERGY", "AVERAGE LIFESPAN"));
                clearer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void run() throws InterruptedException {
        mainFrame = new JFrame(String.join(" ", "Evolution Simulator", Integer.toString(simNumber)));
        mainFrame.setSize(1600, 1000);

        timer = new Timer(delay, this);
        timer.setActionCommand("update");
        timer.start();

        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                timer.stop();
            }
        });

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        panel = new JPanel();
        mainFrame.add(panel);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));

        try {
            animalPic = ImageIO.read(new File("src/main/resources/newAnimal.png"));
            grassPic = ImageIO.read(new File("src/main/resources/newGrass.png"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        animalPic = this.resize(animalPic, CELL);
        grassPic = this.resize(grassPic, CELL);

        Border blackline = BorderFactory.createLineBorder(Color.black);

        JSplitPane mainPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainPanel.setResizeWeight(0.2d);

        JPanel LeftPanel = new JPanel();
        LeftPanel.setLayout(new BoxLayout(LeftPanel, BoxLayout.Y_AXIS));
        LeftPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        LeftPanel.setBorder(blackline);

        JPanel RightPanel = new JPanel();
        RightPanel.setLayout(new BoxLayout(RightPanel, BoxLayout.Y_AXIS));

        mainPanel.setLeftComponent(LeftPanel);
        mainPanel.setRightComponent(RightPanel);
        panel.add(mainPanel);

        simGrid.setPreferredSize(new Dimension(20*map.getWidth(), 20*map.getHeight()));
        simGrid.setMinimumSize(new Dimension(20*map.getWidth(), 20*map.getHeight()));
        simGrid.setLayout(new GridLayout(map.getWidth(), map.getHeight()));
        simGrid.setBorder(blackline);
        RightPanel.add(simGrid);

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

        RightPanel.add(buttonPanel);

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

        jungleSquare.setBackground(new Color(70,150,90));
        duneSquare.setBackground(new Color(252,252,3));

        jungle.add(jungleSquare);
        dune.add(duneSquare);

        jungle.add(new JLabel(" - Jungle Square"));
        dune.add(new JLabel(" - Dune Square"));
        animal.add(new JLabel(" - Animal"));
        grass.add(new JLabel(" - Grass"));

        jungle.setPreferredSize(new Dimension(100, 30));
        dune.setPreferredSize(new Dimension(100, 30));
        animal.setPreferredSize(new Dimension(100, 30));
        grass.setPreferredSize(new Dimension(100, 30));

        legend.add(jungle);
        legend.add(dune);
        legend.add(animal);
        legend.add(grass);

        RightPanel.add(legend);

        //LEFT PANEL

        statsPanel = new Stats(map);
        statsPanel.setBorder(blackline);

        chart = new XYChartBuilder().width(600).height(400).title("Graph").xAxisTitle("Day").yAxisTitle("").build();
        chart.getStyler().setMarkerSize(0);
        JPanel chartPanel = new XChartPanel<org.knowm.xchart.XYChart>(chart);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);

        chart.addSeries("Animals", new double[] { 0 }, new double[] { 0 });
        chart.addSeries("Grass", new double[] { 0 }, new double[] { 0 });
        chart.addSeries("Free", new double[] { 0 }, new double[] { 0 });

        chartPanel.setMaximumSize(new Dimension(9999, 500));

        LeftPanel.add(chartPanel);
        LeftPanel.add(statsPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getActionCommand().equals("update")) {
            simGrid.revalidate();
            panel.repaint();

            map.removeDead(dayCount);
            map.moveAnimals();
            map.eatGrass();
            map.animalKids(dayCount);
            map.growGrass();

            animalCount.add(map.getAnimalsCount());
            grassCount.add(map.getGrassCount());
            freeCount.add(map.getFreeSpaces());

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
                        temp.setBackground(new Color(70,150,90));
                    }
                    else {
                        temp.setBackground(new Color(252,252,3));
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

            statsPanel.setDay(dayCount);

            // refresh animal infos
            for(AnimalInfo AI : animalInfoList){
                if(AI.isInfoActive()){
                    AI.refresh(dayCount);
                }
            }

            double[] animals = new double[dayCount];
            double[] grass = new double[dayCount];
            double[] free = new double[dayCount];
            double[] days = new double[dayCount];

            for(int i=0; i<dayCount; i++){
                animals[i] = (double) animalCount.get(i);
                grass[i] = (double) grassCount.get(i);
                free[i] = (double) freeCount.get(i);
                days[i] = i;
            }

            chart.updateXYSeries("Animals", days, animals, null);
            chart.updateXYSeries("Grass", days, grass, null);
            chart.updateXYSeries("Free", days, free, null);

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

                        Animal animal = map.getAnimalAt(pos);

                        int c = (int) ((double) animal.getCurrHealth() * 255 / (double) animal.getMaxHealth());

                        button.setBackground(new Color(c, c, c));

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

    public int getDayCount() {
        return dayCount;
    }


}