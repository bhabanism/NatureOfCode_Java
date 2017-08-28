package ai.noc.evolution.monkeyshakespeare;

import java.util.concurrent.ThreadLocalRandom;
import static ai.noc.evolution.monkeyshakespeare.Population.target;

public class DNA {
    private char[] genes;
    public float fitness;

    public DNA() {
        int length = target.length();
        genes = new char[length];
        for(int i = 0; i<length; i++) {
            genes[i] = getRandomGene();
        }
    }

    private char getRandomGene() {
        return (char) ThreadLocalRandom.current().nextInt(32, 126 + 1);
    }

    public String getDNA() {
        return new String(genes);
    }

    public void calcFitness() {
        int score = 0;
        if(genes.length != target.length()) {
            fitness = 0.0000f;
        } else {
            for(int i = 0; i< genes.length; i++) {
                if(genes[i] == target.charAt(i)) {
                    score++;
                }
            }

            fitness = (float)score / target.length();
            /*if(score > 0) {
                System.out.println("score : " + score);
                System.out.println("fitness : " + fitness);
            }*/
        }
    }

    public DNA crossover(DNA partner) {
        DNA child = new DNA();
        int midrand = ThreadLocalRandom.current().nextInt(1, this.genes.length);
        String childGenes = this.getDNA().substring(0, midrand);
        childGenes += partner.getDNA().substring(midrand);
        child.genes = childGenes.toCharArray();
        return child;
    }

    public DNA mutate(float mutationrate) {
        //System.out.println("before mutations " + this.getDNA());
        for(int i=0; i<this.genes.length; i++) {
            if(ThreadLocalRandom.current().nextFloat() < mutationrate) {
                genes[i] = getRandomGene();
            }
        }
        //System.out.println("after mutations " + this.getDNA());
        return this;
    }


}
