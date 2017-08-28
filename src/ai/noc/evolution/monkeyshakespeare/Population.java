package ai.noc.evolution.monkeyshakespeare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Population {
    float mutationRate;
    DNA[] population;
    ArrayList<DNA> matingpool = new ArrayList<>();
    public static String target;
    public int generation = 0;
    public boolean finished = false;
    final int perfectScore = 1;

    public Population(float mutationRate, String target, int size) {
        this.mutationRate = mutationRate;
        this.target = target;
        population = new DNA[size];
        for(int i=0;i<population.length;i++) {
            population[i] = new DNA();
        }
        calcFitnessOfPopulation();
    }

    public void calcFitnessOfPopulation() {
        /*for(int i=0;i<population.length;i++) {
            population[i].calcFitness();
        }*/

        Arrays.asList(population).stream().forEach(dna -> dna.calcFitness());
    }

    public void naturalSelection() {
        matingpool.clear();
        float maxFitness = getMaxFitness();
        Arrays.asList(population).stream().forEach(dna -> {
            float fitWeight = 10;
            if(maxFitness > 0) {
                fitWeight = 100 * dna.fitness / maxFitness;
            }

            for(int i = 0; i< fitWeight; i++) {
                matingpool.add(dna);
            }
        });
    }

    public void nextGen() {
       /* population = (DNA[]) Arrays.asList(population).stream().map(dna ->{
            DNA parant1 = getRandomParent();
            DNA parent2 = getRandomParent();
            DNA child = parant1.crossover(parent2);
            return child.mutate(mutationRate);
        }).collect(Collectors.toList()).toArray();*/
       for(int i = 0 ; i< population.length; i++) {
           DNA parant1 = getRandomParent();
           DNA parent2 = getRandomParent();
           DNA embryo = parant1.crossover(parent2);
           DNA child = embryo.mutate(mutationRate);
           population[i] = child;
       }
        generation++;
    }

    private DNA getRandomParent() {
        return this.matingpool.get(ThreadLocalRandom.current().nextInt(matingpool.size()));
    }

    private float getMaxFitness() {
        float maxFitness = 0.000f;
        for(int i = 0 ; i< population.length; i++) {
            if(population[i].fitness > maxFitness) {
                maxFitness = population[i].fitness;
            }
        }
        return maxFitness;
    }

    public String getBest() {
        float worldrecord = 0.000f;
        String best = "";
        for(int i = 0 ; i< population.length; i++) {
            if(population[i].fitness > worldrecord) {
                worldrecord = population[i].fitness;
                best = population[i].getDNA();
                //System.out.println("record : "+ worldrecord);
            }
        }
        if(best.equals(target)) {
            finished = true;
        }

        return best;
    }
}
