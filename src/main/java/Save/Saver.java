package Save;

import World.Settings;

import java.io.FileWriter;
import java.io.IOException;

public class Saver {

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

    public Saver(int delay_, int width_, int height_, Settings.WorldType worldType_,
                 int startGrassCount_, int growingGrassCount_, int grassEnergy_, Settings.GrassType grassType_,
                 int animalMaxEnergy_, int startAnimalCount_, int startAnimalEnergy_, int animalReadyEnergy_, int birthEnergyLoss_, int dailyConsumption_, Settings.AnimalType animalType_,
                 int genomeSize_, int mutationCoefficient_, Settings.MutationType mutationType_){

        delay = delay_;

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
        animalType = animalType_;

        genomeSize = genomeSize_;
        mutationCoefficient = mutationCoefficient_;
        mutationType = mutationType_;
    }

    public void save(String filename) throws IOException {
        FileWriter myWriter = new FileWriter(String.join("", ".\\configsaves\\", filename));
        myWriter.write(Integer.toString(delay));
        myWriter.write("\n");

        myWriter.write(Integer.toString(width));
        myWriter.write("\n");
        myWriter.write(Integer.toString(height));
        myWriter.write("\n");
        myWriter.write(worldType.toString());
        myWriter.write("\n");

        myWriter.write(Integer.toString(startGrassCount));
        myWriter.write("\n");
        myWriter.write(Integer.toString(growingGrassCount));
        myWriter.write("\n");
        myWriter.write(Integer.toString(grassEnergy));
        myWriter.write("\n");
        myWriter.write(grassType.toString());
        myWriter.write("\n");

        myWriter.write(Integer.toString(animalMaxEnergy));
        myWriter.write("\n");
        myWriter.write(Integer.toString(startAnimalCount));
        myWriter.write("\n");
        myWriter.write(Integer.toString(startAnimalEnergy));
        myWriter.write("\n");
        myWriter.write(Integer.toString(animalReadyEnergy));
        myWriter.write("\n");
        myWriter.write(Integer.toString(birthEnergyLoss));
        myWriter.write("\n");
        myWriter.write(Integer.toString(dailyConsumption));
        myWriter.write("\n");
        myWriter.write(animalType.toString());
        myWriter.write("\n");

        myWriter.write(Integer.toString(genomeSize));
        myWriter.write("\n");
        myWriter.write(Integer.toString(mutationCoefficient));
        myWriter.write("\n");
        myWriter.write(mutationType.toString());
        myWriter.write("\n");
        myWriter.close();
    }
}
