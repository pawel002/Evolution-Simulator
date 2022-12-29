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


// TODO
// - wyrastanie trawy
// - sortowanie po smiertelosci pól


public class Main {
    public static void main(String[] args) throws InterruptedException {
        AbstractWorldMap map = new AbstractWorldMap(20, 10, 0,
                25, 2, 50, 0,
                100, 2, 50, 70, 20, 20, 0,
                10, 1, 1);

        List<Integer> genome1 = new ArrayList<>();
        List<Integer> genome2 = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int number1 = 7;
            genome1.add(number1);
            int number2 = ThreadLocalRandom.current().nextInt(0, 8);
            genome2.add(number2);
        }

        Animal animal1 = new Animal(new Vector2d(1, 1), 200, 150, 0, genome1, map);
        Animal animal2 = new Animal(new Vector2d(1, 1), 200, 150, 0, genome2, map);
        out.println(AnimalCompare.compare(animal1, animal2));
    }
}
