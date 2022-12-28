package objects;

import maps.AbstractWorldMap;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.min;

public class Animal {
    private final int birthDate;
    private int currHealth;
    private final int maxHealth;
    private Vector2d position;
    private final List<Integer> genome;
    private int genomeIndex;
    private final AbstractWorldMap map;

    public Animal(Vector2d pos_, int maxHealth_, int currHealth_, int birthDate_, List<Integer> genome_ , AbstractWorldMap map_){
        birthDate = birthDate_;
        currHealth = currHealth_;
        maxHealth = maxHealth_;
        position = pos_;
        genome = genome_;
        map = map_;

        genomeIndex = ThreadLocalRandom.current().nextInt(0, genome.size() + 1);
    }

    public int getCurrHealth() {
        return currHealth;
    }

    // returns position
    public Vector2d getPosition() {
        return position;
    }

    public void decreaseHealth(int change){
        currHealth -= change;
    }

    public void increaseHealth(int change){
        currHealth = min(currHealth + change, maxHealth);
    }

    public int getAge(int currDay){
        return currDay - birthDate;
    }

    // moves the animal
    public void move(){
        Integer movement = genome.get(genomeIndex);
        genomeIndex = (genomeIndex + 1) % genome.size();
        Vector2d newPos = newPos(movement);
        // earth
        if(map.getWorldType() == 0){
            position = new Vector2d(newPos.x % map.getWidth(), newPos.y % map.getHeight());
        } else {
            // portal
            if(newPos.x < 0 || newPos.x >= map.getWidth() || newPos.y < 0 || newPos.y >= map.getHeight()){
                int x = ThreadLocalRandom.current().nextInt(0, map.getWidth());
                int y = ThreadLocalRandom.current().nextInt(0, map.getHeight());
                this.decreaseHealth(map.getBirthEnergyLoss());
                position = new Vector2d(x, y);
            } else {
                position = newPos;
            }
        }
    }

    private Vector2d newPos(int movement){
        return switch (movement){
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
