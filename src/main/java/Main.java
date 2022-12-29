import maps.AbstractWorldMap;
import maps.AnimalCompare;
import objects.Animal;
import objects.Grass;
import objects.Vector2d;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.out;
import static java.lang.System.setOut;

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


// TODO
// - sortowanie po smiertelosci pól


public class Main {
    public static void main(String[] args) throws InterruptedException {
        AbstractWorldMap map = new AbstractWorldMap(100, 100, 0,
                25, 5, 50, 0,
                100, 20, 50, 70, 20, 5, 0,
                10, 1, 1);

        out.println(map);
        for(int i=0; i<100; i++){
            map.moveAnimals();
            map.removeDead();
            map.eatGrass();
            map.growGrass();
            out.println(map);
        }
    }
}
