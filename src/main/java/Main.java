import objects.Grass;
import objects.Vector2d;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Map<Vector2d, Grass> hashedGrass = new HashMap<>();
        hashedGrass.put(new Vector2d(2, 2), new Grass(new Vector2d(2, 2)));
        hashedGrass.put(new Vector2d(2, 2), new Grass(new Vector2d(2, 2)));
        Grass a = hashedGrass.get(new Vector2d(2, 2));
        out.println(a);
    }
}
