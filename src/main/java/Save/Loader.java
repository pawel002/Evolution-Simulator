package Save;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import World.Settings;

public class Loader {

    // simulation options
    int delay;

    // world options
    int width;
    int height;
    Settings.WorldType worldType;

    // grass options
    int startGrassCount;
    int growingGrassCount;
    int grassEnergy;
    Settings.GrassType grassType;

    // animal options
    int animalMaxEnergy;
    int dailyConsumption;
    int startAnimalCount;
    int startAnimalEnergy;
    int birthEnergyLoss;
    int animalReadyEnergy;
    Settings.AnimalType animalType;

    //
    int genomeSize;
    int mutationCoefficient;
    Settings.MutationType mutationType;


    public void read(String filename) throws FileNotFoundException {
        File file = new File(String.join("", ".\\configsaves\\", filename));
        Scanner sc = new Scanner(file);

        delay = Integer.parseInt(sc.nextLine());
        
        width = Integer.parseInt(sc.nextLine());
        height = Integer.parseInt(sc.nextLine());
        worldType = Settings.getWorldType(sc.nextLine());
        
        startGrassCount = Integer.parseInt(sc.nextLine());
        growingGrassCount = Integer.parseInt(sc.nextLine());
        grassEnergy = Integer.parseInt(sc.nextLine());
        grassType = Settings.getGrassType(sc.nextLine());

        animalMaxEnergy = Integer.parseInt(sc.nextLine());
        startAnimalCount = Integer.parseInt(sc.nextLine());
        startAnimalEnergy = Integer.parseInt(sc.nextLine());
        animalReadyEnergy = Integer.parseInt(sc.nextLine());
        birthEnergyLoss = Integer.parseInt(sc.nextLine());
        dailyConsumption = Integer.parseInt(sc.nextLine());
        animalType = Settings.getAnimalType(sc.nextLine());

        genomeSize = Integer.parseInt(sc.nextLine());
        mutationCoefficient = Integer.parseInt(sc.nextLine());
        mutationType = Settings.getMutationType(sc.nextLine());

        validate();
    }

    public void validate() throws IllegalArgumentException{
        if(delay <= 0){
            throw new IllegalArgumentException("Invalid delay");
        }
        if(height <= 0){
            throw new IllegalArgumentException("Invalid height");
        }
        if(width <= 0){
            throw new IllegalArgumentException("Invalid width");
        }
        if(startGrassCount < 0){
            throw new IllegalArgumentException("Invalid jungle height");
        }
        if(growingGrassCount < 0){
            throw new IllegalArgumentException("Invalid copulationMinimumEnergy");
        }
        if(grassEnergy < 0){
            throw new IllegalArgumentException("Invalid animalsStartEnergy");
        }
        if(dailyConsumption < 0){
            throw new IllegalArgumentException("Invalid grassSpawnedInEachDay");
        }
        if(startAnimalCount < 0){
            throw new IllegalArgumentException("Invalid grassSpawnedInEachDay");
        }
        if(startAnimalEnergy <= 0){
            throw new IllegalArgumentException("Invalid grassSpawnedInEachDay");
        }
        if(birthEnergyLoss < 0){
            throw new IllegalArgumentException("Invalid grassSpawnedInEachDay");
        }
        if(animalReadyEnergy < 0){
            throw new IllegalArgumentException("Invalid grassSpawnedInEachDay");
        }
        if(genomeSize <= 0){
            throw new IllegalArgumentException("Invalid grassSpawnedInEachDay");
        }
        if(mutationCoefficient <= 0){
            throw new IllegalArgumentException("Invalid grassSpawnedInEachDay");
        }

    }

    public int getDelay() {
        return delay;
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

    public int getStartGrassCount() {
        return startGrassCount;
    }

    public int getGrowingGrassCount() {
        return growingGrassCount;
    }

    public int getGrassEnergy() {
        return grassEnergy;
    }

    public Settings.GrassType getGrassType() {
        return grassType;
    }

    public int getAnimalMaxEnergy() {
        return animalMaxEnergy;
    }

    public int getDailyConsumption() {
        return dailyConsumption;
    }

    public int getStartAnimalCount() {
        return startAnimalCount;
    }

    public int getStartAnimalEnergy() {
        return startAnimalEnergy;
    }

    public int getBirthEnergyLoss() {
        return birthEnergyLoss;
    }

    public int getAnimalReadyEnergy() {
        return animalReadyEnergy;
    }

    public Settings.AnimalType getAnimalType() {
        return animalType;
    }

    public int getGenomeSize() {
        return genomeSize;
    }

    public int getMutationCoefficient() {
        return mutationCoefficient;
    }

    public Settings.MutationType getMutationType() {
        return mutationType;
    }
}
