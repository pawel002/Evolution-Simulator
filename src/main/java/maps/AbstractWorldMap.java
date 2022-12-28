package maps;

import objects.Animal;
import objects.Grass;
import objects.Vector2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;
import static java.lang.Math.min;

public abstract class AbstractWorldMap {
    protected Map<Vector2d, List<Animal>> hashedAnimals = new HashMap<>();
    protected Map<Vector2d, Grass> hashedGrass = new HashMap<>();
    protected List<Animal> animalsList = new ArrayList<>();
    protected List<Animal> deadAnimalsList = new ArrayList<>();
    protected List<Grass> grassList = new ArrayList<>();

    // world properties
    protected final int width;
    protected final int height;
    protected final int worldType;

    // grass
    protected int startGrassCount;
    protected final int growingGrassCount;
    protected final int grassEnergy;
    // 0 - equator, 1 - toxic
    protected final int grassType;

    // dynamic grass count
    int eatenGrass;

    // animals
    protected final int animalMaxEnergy;
    protected final int startAnimalCount;
    protected final int startAnimalEnergy;
    protected final int animalReadyEnergy;
    protected final int birthEnergyLoss;
    protected final int genomeSize;
    // 0 - predestination, 1 - random moves
    protected final int animalType;

    // mutation
    protected final int mutationCoeff;
    // 0 - random, 1 - slight correction
    protected final int mutationType;


    public AbstractWorldMap(int width_, int height_, int worldType_,
                            int startGrassCount_, int growingGrassCount_, int grassEnergy_, int grassType_,
                            int animalMaxEnergy_, int startAnimalCount_, int startAnimalEnergy_, int animalReadyEnergy_, int birthEnergyLoss_,
                            int genomeSize_, int animalType_, int mutationCoeff_, int mutationType_) {

        width = width_;
        height = height_;
        worldType = worldType_;

        startGrassCount = startGrassCount_;
        growingGrassCount = growingGrassCount_;
        grassEnergy = grassEnergy_;
        grassType = grassType_;

        animalMaxEnergy = animalMaxEnergy_;
        startAnimalCount = startAnimalCount_;
        startAnimalEnergy = startAnimalEnergy_;
        birthEnergyLoss = birthEnergyLoss_;
        animalReadyEnergy = animalReadyEnergy_;

        genomeSize = genomeSize_;
        animalType = animalType_;
        mutationCoeff = mutationCoeff_;
        mutationType = mutationType_;

        // placing animals
        while (animalsList.size() < startAnimalCount) {
            int x = ThreadLocalRandom.current().nextInt(0, width);
            int y = ThreadLocalRandom.current().nextInt(0, height);
            Vector2d pos = new Vector2d(x, y);
            if (!this.isOccupied(pos)) {
                // generate genome
                List<Integer> genome = new ArrayList<>();
                for (int i = 0; i < genomeSize; i++) {
                    int number = ThreadLocalRandom.current().nextInt(0, 8);
                    genome.add(number);
                }
                Animal animal = new Animal(pos, animalMaxEnergy, startAnimalEnergy, 0, genome, this);
                animalsList.add(animal);
                hashedAnimals.get(pos).add(animal);
            }
        }

        // placing grass
        // only width * height - startAnimalsCount can be placed
        startGrassCount = min(startGrassCount, width * height - startAnimalCount);

        // 0 when equator
        if (grassType == 0) {
            while (grassList.size() < startGrassCount) {
                int number = ThreadLocalRandom.current().nextInt(0, 5);
                // if we roll 0 -> the grass grows in less preferable spot
                int x, y;
                x = ThreadLocalRandom.current().nextInt(0, width);
                if (number == 0) {
                    int side = ThreadLocalRandom.current().nextInt(0, 2);
                    if (side == 0) {
                        y = ThreadLocalRandom.current().nextInt(0, height / 3 + 1);
                    } else {
                        y = ThreadLocalRandom.current().nextInt(2 * height / 3, height + 1);
                    }
                } else {
                    y = ThreadLocalRandom.current().nextInt(height / 3, 2 * height / 3 + 1);
                }
                Vector2d pos = new Vector2d(x, y);
                if (!isOccupied(pos)) {
                    Grass grass = new Grass(pos);
                    grassList.add(grass);
                    hashedGrass.put(pos, grass);
                }
            }
        } else {
            // no animals died -> we can place grass at random
            while (grassList.size() < startGrassCount) {
                int x = ThreadLocalRandom.current().nextInt(0, width);
                int y = ThreadLocalRandom.current().nextInt(0, height);
                Vector2d pos = new Vector2d(x, y);
                Grass grass = new Grass(pos);
                grassList.add(grass);
                hashedGrass.put(pos, grass);
            }
        }
    }

    public void removeDead() {
        for (Animal animal : animalsList) {
            if (animal.getCurrHealth() <= 0) {
                animalsList.remove(animal);
                deadAnimalsList.add(animal);
                hashedAnimals.get(animal.getPosition()).remove(animal);
                if (hashedAnimals.get(animal.getPosition()).size() == 0) {
                    hashedAnimals.remove(animal.getPosition());
                }
            }
        }
    }

    public void moveAnimals() {
        for (Animal animal : animalsList) {
            animal.move();
        }
    }

    public void eatGrass() {
        int eatenGrassCount = 0;
        for (Animal animal : animalsList) {
            if (hashedGrass.containsKey(animal.getPosition())) {
                Grass grass = hashedGrass.get(animal.getPosition());
                animal.increaseHealth(grassEnergy);
                hashedGrass.remove(animal.getPosition());
                grassList.remove(grass);
                eatenGrassCount += 1;
            }
        }

        eatenGrass = eatenGrassCount;
    }

    public void animalKids(int currDate) {
        for (Vector2d pos : hashedAnimals.keySet()) {
            List<Animal> currAnimalList = hashedAnimals.get(pos);
            if (currAnimalList.size() == 2 && currAnimalList.get(0).getCurrHealth() >= animalReadyEnergy && currAnimalList.get(1).getCurrHealth() >= animalReadyEnergy) {
                Animal child = new Animal(currAnimalList.get(0).getPosition(), animalMaxEnergy, 2 * birthEnergyLoss, currDate,
                        childGenome(currAnimalList.get(0), currAnimalList.get(1)), this);
                currAnimalList.get(0).decreaseHealth(birthEnergyLoss);
                currAnimalList.get(1).decreaseHealth(birthEnergyLoss);
                animalsList.add(child);
                currAnimalList.add(child);
            } else if (currAnimalList.size() > 2) {
                Animal animal1, animal2;
                if (AnimalCompare.compare(currAnimalList.get(0), currAnimalList.get(1)) == 1) {
                    animal1 = currAnimalList.get(0);
                    animal2 = currAnimalList.get(1);
                } else {
                    animal2 = currAnimalList.get(0);
                    animal1 = currAnimalList.get(1);
                }

                for (int i = 2; i < currAnimalList.size(); i++) {

                }

            }
        }
    }

    public void growGrass() {

    }

    public List<Integer> childGenome(Animal animal1, Animal animal2) {
        int sumEnergy = animal1.getCurrHealth() + animal2.getCurrHealth();
        int index = (int) ((double) animal1.getCurrHealth() / (double) sumEnergy);
        int random = ThreadLocalRandom.current().nextInt(0, 2);
        List<Integer> childGenome = new ArrayList<>();
        if (random == 0) {
            for (int i = 0; i < genomeSize; i++) {
                if (i <= index)
                    childGenome.add(animal1.getGenomeAt(i));
                else
                    childGenome.add(animal2.getGenomeAt(i));
            }
        } else {
            for (int i = 0; i < genomeSize; i++) {
                if (i <= genomeSize - index)
                    childGenome.add(animal2.getGenomeAt(i));
                else
                    childGenome.add(animal1.getGenomeAt(i));
            }
        }
        return childGenome;
    }

    public int getBirthEnergyLoss() {
        return birthEnergyLoss;
    }

    public boolean isOccupied(Vector2d pos) {
        return hashedAnimals.containsKey(pos) || hashedGrass.containsKey(pos);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getWorldType() {
        return worldType;
    }
}