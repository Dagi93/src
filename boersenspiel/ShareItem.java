package boersenspiel;


public class ShareItem extends Asset {
    private int sAmount;
    private long purchaseValue = 0;

    
    /**Konstruktor*/
    public ShareItem (Share share, int amount){
        this.setName(share.getName());
        this.setSAmount(amount);
        this.setValue(share.getValue()*amount);
    }
    
    public int getSAmount() {
        return sAmount;
    }

    public void setSAmount(int difference) {
        this.sAmount = this.sAmount + difference;
    }
    

    public String toString() {
        return "Name: " + this.getName() + ", Menge: " + this.getSAmount() + ", Gesamtwert: " + (double)this.getValue()/100 + "€.";
    }

    
    
}