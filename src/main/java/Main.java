import Menu.Menu;

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
//        AbstractWorldMap map = new AbstractWorldMap(10, 10, Settings.WorldType.EARTH,
//                10, 5, 50, Settings.GrassType.EQUATOR,
//                100, 10, 50, 70, 20, 5, Settings.AnimalType.PREDESTINATION,
//                10, 1, Settings.MutationType.CORRECTION);
//
//        out.println(map);
//        for(int i=0; i<10; i++){
//            map.moveAnimals();
//            map.removeDead();
//            map.eatGrass();
//            out.println(map);
//        }
        Menu menu = new Menu();
        menu.startSimulation(null);
    }
}
