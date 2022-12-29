import Menu.Menu;
import Objects.Vector2d;
import World.Settings;
import World.WorldHandler;

import java.util.*;

import static java.lang.System.out;

// DONE
// - inicjalizacja mapy - zwierzeta i trawa na siebie nie nachodzą
// - przekazywanie genomu i mutacje
// - remove dead
// - move animals
// - komparator po energii -> wieku -> dzietnosci -> random
// - eat grass - do sprawdzenia
// - rozmnazanie - do sprawdzenia
// - przemieszanie kolejnosci wykonywania genow
// - wyrastanie trawy - bardziej efektywnie + fix inifite loop przy pełnej mapie

// interface:
// settings


// TODO
// - sortowanie po smiertelosci pól
// - wizualizacja


public class Main {
    public static void main(String[] args) throws InterruptedException {
        WorldHandler map = new WorldHandler(10, 10, Settings.WorldType.EARTH,
                10, 5, 50, Settings.GrassType.TOXIC,
                100, 5, 50, 70, 20, 25, Settings.AnimalType.PREDESTINATION,
                10, 1, Settings.MutationType.CORRECTION);

        out.println(map);
        for(int i=1; i<20; i++){
            map.removeDead(i);
            map.moveAnimals();
            map.eatGrass();
            map.growGrass();
            out.println(map);
        }


//        Menu menu = new Menu();
//        menu.startSimulation(null);

    }
}
