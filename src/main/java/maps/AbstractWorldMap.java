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
    protected List<Animal> animalsList = new ArrayList<>();
    protected List<Animal> deadAnimalsList = new ArrayList<>();

    protected Map<Vector2d, Grass> hashedGrass = new HashMap<>();
    protected Set<Vector2d> hashedJungle = new HashSet<Vector2d>();
    protected List<Vector2d> freeJungle = new ArrayList<>();
    protected List<Vector2d> freeDune = new ArrayList<>();

    // world properties
    protected final int width;
    protected final int height;
    // 0 - earth, 1 - portal
    protected final Settings.WorldType worldType;

    // grass
    protected int startGrassCount;
    protected final int growingGrassCount;
    protected final int grassEnergy;
    // 0 - equator, 1 - toxic
    protected final Settings.GrassType grassType;

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
    protected final Settings.AnimalType animalType;

    // mutation
    protected final int genomeSize;
    protected final int mutationCoefficient;
    // 0 - random, 1 - slight correction
    protected final Settings.MutationType mutationType;


    public AbstractWorldMap(int width_, int height_, Settings.WorldType worldType_,
                            int startGrassCount_, int growingGrassCount_, int grassEnergy_, Settings.GrassType grassType_,
                            int animalMaxEnergy_, int startAnimalCount_, int startAnimalEnergy_, int animalReadyEnergy_, int birthEnergyLoss_, int dailyConsumption_, Settings.AnimalType animalType_,
                            int genomeSize_, int mutationCoefficient_, Settings.MutationType mutationType_) {

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
        if (grassType == Settings.GrassType.EQUATOR) {
            // define jungle spots 2/5 -> 3/5
            for(int i = 0; i<height; i++){
                for(int j=0 ; j<width; j++){
                    Vector2d v = new Vector2d(j, i);
                    if((height * 2) / 5 <= i && i < (height * 3) / 5){
                        hashedJungle.add(v);
                        freeJungle.add(v);
                    } else {
                        freeDune.add(v);
                    }
                }
            }

            // fill the world with grass
            for(int i=0; i< startGrassCount; i++){
                this.addGrass();
            }
        } else {
            // toxic squares
        }

    }

    // adds single grass
    public boolean addGrass(){
        // if x == 0 then we take less preferable spot
        int x = ThreadLocalRandom.current().nextInt(0, 5);
        if((x == 0 || freeJungle.isEmpty()) && !freeDune.isEmpty() ){
            int index = ThreadLocalRandom.current().nextInt(0, freeDune.size());
            Vector2d v = freeDune.get(index);
            freeDune.remove(index);

            Grass grass = new Grass(v);
            hashedGrass.put(v, grass);
            return true;
        } else if (!freeJungle.isEmpty()){
            // preferable spot
            int index = ThreadLocalRandom.current().nextInt(0, freeJungle.size());
            Vector2d v = freeJungle.get(index);
            freeJungle.remove(index);

            Grass grass = new Grass(v);
            hashedGrass.put(v, grass);
            return true;
        }
        return false;
    }

    public void removeGrass(Vector2d pos){
        if(!hashedGrass.containsKey(pos)){
            return;
        }
        hashedGrass.remove(pos);
        if(hashedJungle.contains(pos)){
            freeJungle.add(pos);
        } else {
            freeDune.add(pos);
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
                removeGrass(animal.getPosition());
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
        if(grassType == Settings.GrassType.EQUATOR){
            int grassAddedCount = 0;
            while (grassAddedCount < growingGrassCount && addGrass()) {
                grassAddedCount ++;
            }
        } else {
            // toxic spots
        }
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
        if (mutationType == Settings.MutationType.RANDOM) {
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

    public Settings.WorldType getWorldType() {
        return worldType;
    }

    public Settings.AnimalType getAnimalType() {
        return animalType;
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

        out.println(String.join(" ", "GRASS STARTING:", Integer.toString(startGrassCount)));
        out.println(String.join(" ", "GRASS EATEN:", Integer.toString(eatenGrass)));
        out.println(String.join(" ", "GRASS LEFT:", Integer.toString(hashedGrass.size())));

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