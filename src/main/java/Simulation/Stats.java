package Simulation;

import World.WorldHandler;

import javax.swing.*;
import java.awt.*;

public class Stats extends JPanel {
    private final WorldHandler map;

    private int avgEnergyLvl = 0;
    private String avgLifespan = "";
    private final JProgressBar bar = new JProgressBar();
    private final Font f1 = new Font("Helvetica", Font.PLAIN, 25);
    private JLabel enLabel = new JLabel();
    private JLabel genLabel = new JLabel();
    private JLabel lfLabel = new JLabel();
    private JLabel dayCountLabel = new JLabel();
    private int day;

    Stats(WorldHandler map) {
        this.map = map;
        this.setLayout(new GridLayout(4, 1));
        dayCountLabel.setFont(f1);
        dayCountLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(dayCountLabel);

        enLabel.setFont(f1);
        enLabel.setText("Average animal energy: ");
        JPanel barPane = new JPanel();
        barPane.setMaximumSize(new Dimension(200, 30));
        bar.setMinimumSize(new Dimension(200, 50));
        bar.setBounds(0,0,200,50);
        bar.setMaximum(map.getAnimalMaxEnergy());
        bar.setValue(avgEnergyLvl);
        bar.setStringPainted(true);
        barPane.add(bar);

        JPanel prog = new JPanel();
        prog.setLayout(new BoxLayout(prog, BoxLayout.X_AXIS));
        prog.add(new JPanel());
        prog.add(enLabel);
        prog.add(barPane);
        prog.add(new JPanel());
        this.add(prog);


        bar.setMinimumSize(new Dimension(200, 50));
        bar.setMaximum(map.getAnimalMaxEnergy());
        bar.setValue(avgEnergyLvl);
        bar.setStringPainted(true);

        genLabel.setFont(f1);
        genLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(genLabel);

        lfLabel.setFont(f1);
        lfLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(lfLabel);
    }

    public void setDay(int d){
        day = d;
    }

    public void refresh() {
        this.avgEnergyLvl = (int) this.map.getAverageEnergy();
        this.avgLifespan = String.valueOf((int) this.map.getAverageLifespan());
        bar.setValue(avgEnergyLvl);
        dayCountLabel.setText("Current day: " + String.valueOf(day));
        genLabel.setText("Most popular genes: " + map.getDominantGenes());
        lfLabel.setText("Average animal lifespan: " + avgLifespan + " days");

    }
    @Override
    protected void paintComponent(Graphics gg) {
        this.refresh();
        Graphics2D g = (Graphics2D) gg;
        super.paintComponent(g);
    }

}