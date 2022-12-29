package maps;

import objects.Animal;
import objects.Grass;
import objects.Vector2d;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.System.out;

public class AbstractWorldMap {
    protected Map<Vector2d, List<Animal>> hashedAnimals = new HashMap<>();
    protected Map<Vector2d, Grass> hashedGrass = new HashMap<>();
    protected List<Animal> animalsList = new ArrayList<>();
    protected List<Animal> deadAnimalsList = new ArrayList<>();
    protected List<Grass> grassList = new ArrayList<>();

    // world properties
    protected final int width;
    protected final int height;
    // 0 - earth, 1 - portal
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
    protected final int dailyConsumption;
    protected final int startAnimalCount;
    protected final int startAnimalEnergy;
    protected final int animalReadyEnergy;
    protected final int birthEnergyLoss;
    // 0 - predestination, 1 - random moves
    protected final int animalType;

    // mutation
    protected final int genomeSize;
    protected final int mutationCoefficient;
    // 0 - random, 1 - slight correction
    protected final int mutationType;


    public AbstractWorldMap(int width_, int height_, int worldType_,
                            int startGrassCount_, int growingGrassCount_, int grassEnergy_, int grassType_,
                            int animalMaxEnergy_, int startAnimalCount_, int startAnimalEnergy_, int animalReadyEnergy_, int birthEnergyLoss_, int dailyConsumption_, int animalType_,
                            int genomeSize_, int mutationCoefficient_, int mutationType_) {

        width = width_;
        height = height_;
        worldType = worldType_;

        startGrassCount = startGrassCount_;
        growingGrassCount = growingGrassCount_;
        grassEnergy = grassEnergy_;
        grassType = grassType_;

        animalMaxEnergy = animalMaxEnergy_;
        dailyConsumption = dailyConsumption_;
        startAnimalCount = startAnimalCount_;
        startAnimalEnergy = startAnimalEnergy_;
        birthEnergyLoss = birthEnergyLoss_;
        animalReadyEnergy = animalReadyEnergy_;

        genomeSize = genomeSize_;
        animalType = animalType_;
        mutationCoefficient = mutationCoefficient_;
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
                this.addHashedAnimal(animal);
                animalsList.add(animal);
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
                        y = ThreadLocalRandom.current().nextInt(2 * height / 3, height);
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
        if (animalsList.isEmpty()) {
            return;
        }

        List<Animal> tempList = new ArrayList<>();

        for (Animal animal : animalsList) {
            if (animal.getCurrHealth() <= 0) {
                tempList.add(animal);
            }
        }

        for (Animal animal : tempList) {
            animalsList.remove(animal);
            deadAnimalsList.add(animal);
            removeHashedAnimal(animal);
        }
    }

    public void moveAnimals() {
        for (Animal animal : animalsList) {
            this.removeHashedAnimal(animal);

            animal.move();
            animal.decreaseHealth(dailyConsumption);

            this.addHashedAnimal(animal);
        }
    }

    public void eatGrass() {
        int eatenGrassCount = 0;
        for (Vector2d pos : hashedAnimals.keySet()) {
            if (hashedGrass.containsKey(pos)) {
                List<Animal> currAnimalList = hashedAnimals.get(pos);
                Animal animal = currAnimalList.get(0);
                for (int i = 1; i < currAnimalList.size(); i++) {
                    if (AnimalCompare.compare(currAnimalList.get(i), animal) == 1) {
                        animal = currAnimalList.get(i);
                    }
                }
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
                currAnimalList.get(0).addChild();
                currAnimalList.get(1).addChild();
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
                    if (AnimalCompare.compare(currAnimalList.get(i), animal1) == 1) {
                        animal2 = animal1;
                        animal1 = currAnimalList.get(i);
                    } else if (AnimalCompare.compare(currAnimalList.get(i), animal2) == 1) {
                        animal2 = currAnimalList.get(i);
                    }
                }
                Animal child = new Animal(animal1.getPosition(), animalMaxEnergy, 2 * birthEnergyLoss, currDate,
                        childGenome(animal1, animal2), this);
                animal1.decreaseHealth(birthEnergyLoss);
                animal2.decreaseHealth(birthEnergyLoss);
                animal1.addChild();
                animal2.addChild();
                animalsList.add(child);
                currAnimalList.add(child);
            }
        }
    }

    public void growGrass() {

    }

    public List<Integer> childGenome(Animal animal1, Animal animal2) {
        int sumEnergy = animal1.getCurrHealth() + animal2.getCurrHealth();
        int index = (int) ((double) animal1.getCurrHealth() * genomeSize / (double) sumEnergy);
        int random = ThreadLocalRandom.current().nextInt(0, 2);
        List<Integer> childGenome = new ArrayList<>();
        if (random == 0) {
            for (int i = 0; i < genomeSize; i++) {
                if (i < index)
                    childGenome.add(animal1.getGenomeAt(i));
                else
                    childGenome.add(animal2.getGenomeAt(i));
            }
        } else {
            for (int i = 0; i < genomeSize; i++) {
                if (i < genomeSize - index)
                    childGenome.add(animal2.getGenomeAt(i));
                else
                    childGenome.add(animal1.getGenomeAt(i));
            }
        }

        ArrayList<Integer> list = new ArrayList<Integer>(genomeSize);
        // mutation - random
        if (mutationType == 0) {
            for (int i = 0; i < genomeSize; i++) {
                list.add(i);
            }

            Random rand = new Random();
            for (int i = 0; i < mutationCoefficient; i++) {
                int idx = rand.nextInt(list.size());
                childGenome.set(list.remove(idx), ThreadLocalRandom.current().nextInt(0, 8));
            }
        } else {
            // slight correction
            for (int i = 0; i < genomeSize; i++) {
                list.add(i);
            }

            Random rand = new Random();
            for (int i = 0; i < mutationCoefficient; i++) {
                int idx = rand.nextInt(list.size());
                int arrIdx = list.remove(idx);
                int newVal = (childGenome.get(arrIdx) + ThreadLocalRandom.current().nextInt(0, 2) * 2 - 1) % 8;
                childGenome.set(arrIdx, newVal);
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

    public int getAnimalType() {
        return animalType;
    }

    // debug methods
    public void addGrass(Grass grass) {
        hashedGrass.put(grass.getPosition(), grass);
        grassList.add(grass);
    }

    public void removeHashedAnimal(Animal animal) {
        if (hashedAnimals.containsKey(animal.getPosition())) {
            hashedAnimals.get(animal.getPosition()).remove(animal);
        }
        if (hashedAnimals.get(animal.getPosition()).isEmpty()) {
            hashedAnimals.remove(animal.getPosition());
        }
    }

    public void addHashedAnimal(Animal animal) {
        if (hashedAnimals.containsKey(animal.getPosition())) {
            hashedAnimals.get(animal.getPosition()).add(animal);
        } else {
            hashedAnimals.put(animal.getPosition(), new ArrayList<Animal>());
            hashedAnimals.get(animal.getPosition()).add(animal);
        }
    }

    @Override
    public String toString() {


        for (Animal a : animalsList) {
            String res = String.join("  ", a.getPosition().toString(), "->", Integer.toString(a.getCurrHealth()));
            out.println(res);
        }

        StringBuilder ans = new StringBuilder();
        for (int i = height - 1; i >= 0; i--) {

            ans.append(Integer.toString(i));
            ans.append("|");

            for (int j = 0; j < width; j++) {
                int flag = 1;
                if (hashedAnimals.containsKey(new Vector2d(j, i))) {
                    ans.append("A");
                    flag = 0;
                }

                if (hashedGrass.containsKey(new Vector2d(j, i)))
                    if (flag == 1) {
                        ans.append("G");
                        flag = 0;
                    }

                if (flag == 1) {
                    ans.append(" ");
                }
                if (j >= 10) {
                    ans.append(" ");
                }
                ans.append("|");
            }
            ans.append("\n");
        }
        ans.append(" ");
        ans.append("|");
        for (int i = 0; i < width; i++) {
            ans.append(Integer.toString(i));
            ans.append(" ");
        }
        return ans.toString();
    }
}