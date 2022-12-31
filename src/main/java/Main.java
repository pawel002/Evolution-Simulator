import Menu.Menu;
import Objects.Vector2d;
import Objects.Animal;
import Save.Loader;
import Save.Saver;
import Simulation.AnimalInfo;
import World.Settings;
import World.WorldHandler;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.out;
import static java.lang.Thread.sleep;

// DONE
// - inicjalizacja mapy - zwierzeta i trawa na siebie nie nachodzą
// - przekazywanie genomu i mutacje
// - remove dead
// - move animals
// - komparator po energii -> wieku -> dzietnosci -> random
// - eat grass
// - rozmnazanie
// - przemieszanie kolejnosci wykonywania genow
// - wyrastanie trawy - bardziej efektywnie + fix inifite loop przy pełnej mapie
// - sortowanie po smiertelosci pól

// interface:
// settings
// zapisywanie działa



public class Main {

    public static void main(String[] args) throws InterruptedException {

        Menu menu = new Menu();
        menu.openMenu();

    }
}
