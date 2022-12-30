package Simulation;

import World.WorldHandler;

public class ThreadHandler{

    public static WorldHandler map;
    public boolean writeToCSV;
    public int delay;
    public String filename;
    Simulation sim;
    public Thread thread;


    public ThreadHandler(WorldHandler map_, int delay_, boolean writeToCSV_, String filename_){
        map = map_;
        delay = delay_;
        writeToCSV = writeToCSV_;
        filename = filename_;

        sim = new Simulation(map, delay, writeToCSV, filename);
    }

    public void runThread(){
        thread = new Thread(sim);
        thread.start();
    }
}
