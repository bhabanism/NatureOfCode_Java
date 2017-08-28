package ai.noc.evolution.monkeyshakespeare;

public class Main {
    public final static String target = "Add Fuel to Fire";
    //public final static String target = "ABC";
    public static void main(String[] args) {
        /*DNA dna1 = new DNA();
        DNA dna2 = new DNA();
        System.out.println(dna1.getDNA());
        System.out.println(dna2.getDNA());
        DNA child = dna1.crossover(dna2);
        System.out.println(child.getDNA());
        System.out.println(child.mutate(0.2f).getDNA());*/
        Population pop = new Population(0.05f, target, 100);
        while(!pop.finished) {
            pop.naturalSelection();
            pop.nextGen();
            pop.calcFitnessOfPopulation();
            System.out.println("Best yet : " + pop.getBest());
            System.out.println("generation : " + pop.generation);
        }
    }
}
