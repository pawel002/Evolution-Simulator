package Simulation;

import World.WorldHandler;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.swing.*;

public class Simulation implements Runnable{

    public static Stage primaryStage;
    public static GridPane grid;
    public static WorldHandler map;
    public boolean writeToCSV;
    public int delay;
    public String filename;

    public Simulation(WorldHandler map_, int delay_, boolean writeToCSV_, String filename_){
        primaryStage = new Stage();
        map = map_;
        delay = delay_;
        writeToCSV = writeToCSV_;
        filename = filename_;


        if(writeToCSV){
            // handle clearing file
            // every iteration call map.writeToCSV(filename)
        }


    }

    public void start(){
        grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);
        grid.setPrefSize(600, 600);
        grid.setGridLinesVisible(true);
        primaryStage.setTitle("Programowanie obiektowe");
        primaryStage.show();
    }

    @Override
    public void run() {
        grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setAlignment(Pos.CENTER);
        grid.setPrefSize(600, 600);
        grid.setGridLinesVisible(true);
        primaryStage.setTitle("Programowanie obiektowe");
        primaryStage.show();
    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//
//    }
}
