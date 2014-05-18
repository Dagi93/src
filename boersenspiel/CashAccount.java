package boersenspiel;

public class CashAccount extends Asset {

    /** Konstruktor */
    public CashAccount(String name, long seedCap) {
        this.setValue(seedCap);
        this.setName(name);
    }

    public String toString() {
        return "Name: " + getName() + ", Kontostand: " + (double) getValue() / 100 + "€.";

    }
}