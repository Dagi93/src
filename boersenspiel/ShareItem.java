package boersenspiel;

public class ShareItem extends Asset {
    private int amount;

    /** Konstruktor */
    public ShareItem(Share share, int amount) {
        this.setName(share.getName());
        this.setAmount(amount);
        this.setValue(share.getValue() * amount);
    }

    public int getAmount() {
        return amount;
    }

    public void addAmount(int difference) {
        this.amount = this.amount + difference;
    }
    
    public void setAmount(int amount){
        this.amount = amount;
    }

    public String toString() {
        return "Name: " + this.getName() + ", Menge: " + this.getAmount() + ", Gesamtwert: " + (double) this.getValue() / 100 + "€.";
    }

}