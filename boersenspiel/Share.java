package boersenspiel;

public class Share extends Asset implements Comparable<Share> {

    // private String name;
    // private long value;
    //
    // public void setName(String newName){
    // this.name = newName;
    // }
    //
    //
    // public String getName(){
    // return this.name;
    // }
    //
    //
    // public void setValue(long newValue){
    // this.value = newValue;
    // }
    //
    //
    // public long getValue(){
    // return this.value;
    // }

    /** Konstruktor */
    public Share(long newValue, String newName) {
        this.setName(newName);
        this.setValue(newValue);
    }

    /**
     * Vergleicht die Aktie, auf die die Methode aufgerufen wird, mit einer
     * übergebenen Aktie auf Name und Wert. Gibt true aus, wenn die Aktien
     * gleich sind.
     */
    public boolean equals(Share ScndShare) {
        if (ScndShare.getName().equals(this.getName()) && ScndShare.getValue() == this.getValue()) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Name: " + this.getName() + ", Kurs: " + this.getValue() / (double) 100 + "€.";
    }

    @Override
    public int compareTo(Share s) {
        if(this.getName().substring(0, 1).toCharArray()[0] < s.getName().substring(0, 1).toCharArray()[0]){
            return -1;
        }else{
            return 0;
        }
    }

}