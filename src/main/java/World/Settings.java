package World;

public class Settings {

    public enum WorldType {
        EARTH,
        PORTAL;

        @Override
        public String toString() {
            return switch (this) {
                case EARTH -> "E";
                case PORTAL -> "P";
            };
        }
    }


    public enum GrassType {
        EQUATOR,
        TOXIC;

        @Override
        public String toString() {
            return switch (this) {
                case TOXIC -> "T";
                case EQUATOR -> "E";
            };
        }
    }

    public enum AnimalType {
        PREDESTINATION,
        RANDOM;

        @Override
        public String toString() {
            return switch (this) {
                case PREDESTINATION -> "P";
                case RANDOM -> "R";
            };
        }
    }

    public enum MutationType {
        RANDOM,
        CORRECTION;

        @Override
        public String toString() {
            return switch (this) {
                case RANDOM -> "R";
                case CORRECTION -> "C";
            };
        }
    }

    public static Settings.WorldType getWorldType(String str) {
        if (str.equals("E"))
            return Settings.WorldType.EARTH;
        else if (str.equals("P"))
            return Settings.WorldType.PORTAL;
        throw new IllegalArgumentException(String.join(" ", "World type wrong argument:", str, ". Can only be E and P."));
    }

    public static Settings.GrassType getGrassType(String str) {
        if (str.equals("E"))
            return Settings.GrassType.EQUATOR;
        else if (str.equals("T"))
            return Settings.GrassType.TOXIC;
        throw new IllegalArgumentException(String.join(" ", "Grass type wrong argument:", str, ". Can only be E and T."));
    }

    public static Settings.AnimalType getAnimalType(String str) {
        if (str.equals("R"))
            return Settings.AnimalType.RANDOM;
        else if (str.equals("P"))
            return Settings.AnimalType.PREDESTINATION;
        throw new IllegalArgumentException(String.join(" ", "Animal type wrong argument:", str, ". Can only be P and R."));
    }

    public static Settings.MutationType getMutationType(String str) {
        if (str.equals("R"))
            return Settings.MutationType.RANDOM;
        else if (str.equals("C"))
            return Settings.MutationType.CORRECTION;
        throw new IllegalArgumentException(String.join(" ", "Mutation type wrong argument:", str, ". Can only be C and R."));
    }
}
