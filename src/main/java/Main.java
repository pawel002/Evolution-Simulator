import Menu.Menu;
import Objects.Vector2d;
import Objects.Animal;
import Save.Loader;
import Save.Saver;
import World.Settings;
import World.WorldHandler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.out;

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


// TODO
// - wizualizacja


public class Main {
    public static void main(String[] args) throws InterruptedException {
        WorldHandler map = new WorldHandler(30, 30, Settings.WorldType.EARTH,
                100, 20, 50, Settings.GrassType.EQUATOR,
                100, 10, 100, 70, 20, 10, Settings.AnimalType.PREDESTINATION,
                10, 1, Settings.MutationType.CORRECTION);


//        Saver S = new Saver(100, 30, 30, Settings.WorldType.EARTH,
//                100, 20, 50, Settings.GrassType.EQUATOR,
//                100, 10, 100, 70, 20, 10, Settings.AnimalType.PREDESTINATION,
//                10, 1, Settings.MutationType.CORRECTION);
//        try {
//            S.save("settings2");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

//        Loader L = new Loader();
//        try {
//            L.read("settings1");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        out.print(L.getAnimalType());


//        for(int i=1; i<1000; i++){
//            map.removeDead(i);
//            map.moveAnimals();
//            map.eatGrass();
//            map.animalKids(i);
//            map.growGrass();
//            out.println(map);
//        }


        Menu menu = new Menu();
        menu.startSimulation();

    }
}
