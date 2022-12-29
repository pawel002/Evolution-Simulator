package maps;

import objects.Animal;

import java.util.Comparator;

public class AnimalCompare{
    public static int compare(Object a, Object b) {
        if (a == b)
            return 0;
        if (!(a instanceof Animal first) || !(b instanceof Animal second))
            return 0;

        // energy
        if (((Animal) a).getCurrHealth() != ((Animal) b).getCurrHealth()) {
            return ((Animal) a).getCurrHealth() > ((Animal) b).getCurrHealth() ? 1 : -1;
        }

        // age
        if (((Animal) a).getBirthDate() != ((Animal) b).getBirthDate()) {
            return ((Animal) a).getBirthDate() < ((Animal) b).getBirthDate() ? 1 : -1;
        }

        // number of children
        if (((Animal) a).getChildCount() != ((Animal) b).getChildCount()) {
            return ((Animal) a).getChildCount() > ((Animal) b).getChildCount() ? 1 : -1;
        }

        // random
        return 1;
    }
}
