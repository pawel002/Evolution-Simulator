import maps.AbstractWorldMap;
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
        AbstractWorldMap map = new AbstractWorldMap(20, 10, 0,
                25, 2, 10, 0,
                100, 2, 50, 70, 20,
                10, 0, 2, 0);

        out.println(map);
    }
}
