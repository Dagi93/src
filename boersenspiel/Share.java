package boersenspiel;

public class Share extends Asset{

//    private String name;
//    private long value;
//    
//    public void setName(String newName){
//        this.name = newName;
//    }
//    
//    
//    public String getName(){
//        return this.name;
//    }
//    
//    
//    public void setValue(long newValue){
//        this.value = newValue;
//    }
//    
//    
//    public long getValue(){
//        return this.value;
//    }
    
    /**Konstruktor*/
    public Share(long newValue, String newName){
        this.setName(newName);
        this.setValue(newValue);
    }
    
    

    
    /**Vergleicht die Aktie, auf die die Methode aufgerufen wird, mit einer übergebenen Aktie auf Name und Wert.
     * Gibt true aus, wenn die Aktien gleich sind.*/
    public boolean equals(Share ScndShare) {
        if (ScndShare.getName().equals(this.getName()) && ScndShare.getValue() == this.getValue()){
            return true;
        } else {
            return false;
        }
    }

    
    public String toString() {
        return "Name: " + this.getName() + ", Kurs: " + this.getValue()/(double)100 + "€.";
    }
    
}