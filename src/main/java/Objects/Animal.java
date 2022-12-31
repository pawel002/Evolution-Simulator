package Objects;

import World.WorldHandler;
import World.Settings;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.min;

public class Animal {
    private final int birthDate;
    private int deathDate = -1;
    private int currHealth;
    private final int maxHealth;
    private Vector2d position;
    private final List<Integer> genome;
    private int genomeIndex;
    private final WorldHandler map;
    private int childCount = 0;
    private int grassCount = 0;

    public Animal(Vector2d pos_, int maxHealth_, int currHealth_, int birthDate_, List<Integer> genome_, WorldHandler map_) {
        birthDate = birthDate_;
        currHealth = currHealth_;
        maxHealth = maxHealth_;
        position = pos_;
        genome = genome_;
        map = map_;

        genomeIndex = ThreadLocalRandom.current().nextInt(0, genome.size());
    }

    @Override
    public String toString() {
        return String.join(" ", position.toString());
    }

    public void addChild() {
        childCount += 1;
    }

    public int getChildCount() {
        return childCount;
    }

    public void addGrass() {
        grassCount += 1;
    }

    public int getGrassCount() {
        return grassCount;
    }

    public int getMaxHealth() { return maxHealth;}

    public int getGenomeAt(int i) {
        return genome.get(i);
    }

    public int getDeathDate() { return deathDate;}

    public int getCurrHealth() {
        return currHealth;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public int getGenomeIndex() {
        return genomeIndex;
    }

    public int getGenomeSize() {
        return genome.size();
    }

    public String getGenome(){
        StringBuilder res = new StringBuilder();
        int j;
        res.append("[");
        for(int i = 0; i < genome.size(); i++){
            j = (genomeIndex + i) % genome.size();
            res.append(genome.get(j));
            if(i < genome.size() - 1)
                res.append(", ");
        }
        res.append("]");
        return res.toString();
    }

    // returns position
    public Vector2d getPosition() {
        return position;
    }

    public void decreaseHealth(int change) {
        currHealth -= change;
    }

    public void increaseHealth(int change) {
        currHealth = min(currHealth + change, maxHealth);
    }

    public void setDeathDate(int deathDate_) {
        deathDate = deathDate_;
    }

    public int getAge(int currDay) {
        if (currDay == -1){
            return deathDate - birthDate;
        }

        if (deathDate == -1)
            return currDay - birthDate;
        else
            return deathDate - birthDate;
    }

    // moves the animal
    public void move() {
        Integer movement = genome.get(genomeIndex);
        // normal movements
        if (map.getAnimalType() == Settings.AnimalType.PREDESTINATION) {
            genomeIndex = (genomeIndex + 1) % genome.size();
        } else {
            // 20% chance to go to random genome
            int number = ThreadLocalRandom.current().nextInt(0, 5);
            if (number == 0) {
                genomeIndex = ThreadLocalRandom.current().nextInt(0, genome.size());
            }
        }
        Vector2d newPos = newPos(movement);
        // earth
        if (map.getWorldType() == Settings.WorldType.EARTH) {
            position = new Vector2d((newPos.x + map.getWidth()) % map.getWidth(), (newPos.y + map.getHeight()) % map.getHeight());
        } else {
            // portal
            if (newPos.x < 0 || newPos.x >= map.getWidth() || newPos.y < 0 || newPos.y >= map.getHeight()) {
                int x = ThreadLocalRandom.current().nextInt(0, map.getWidth());
                int y = ThreadLocalRandom.current().nextInt(0, map.getHeight());
                this.decreaseHealth(map.getBirthEnergyLoss());
                position = new Vector2d(x, y);
            } else {
                position = newPos;
            }
        }
    }

    private Vector2d newPos(int movement) {
        return switch (movement) {
            case 0 -> position.add(new Vector2d(0, 1));
            case 1 -> position.add(new Vector2d(1, 1));
            case 2 -> position.add(new Vector2d(1, 0));
            case 3 -> position.add(new Vector2d(1, -1));
            case 4 -> position.add(new Vector2d(0, -1));
            case 5 -> position.add(new Vector2d(-1, -1));
            case 6 -> position.add(new Vector2d(-1, 0));
            case 7 -> position.add(new Vector2d(-1, 1));
            default -> throw new IllegalStateException("Unexpected value: " + movement);
        };
    }


}
