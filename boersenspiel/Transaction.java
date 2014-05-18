package boersenspiel;

import java.util.Comparator;
import java.util.Date;

public class Transaction implements Comparator<Transaction>, Comparable<Transaction>{

    private Date date = new Date();
    private Player bob;
    private Share share;
    private int amount;
    private long shareValue;
    private long cAccValue;
    private long time = date.getTime();
    private String method;
    
    public Transaction(Player bob, Share share, int amount, String method) {
        this.bob = bob;
        this.setShare(share);
        this.amount = amount;
        this.shareValue = share.getValue();
        this.cAccValue = bob.getCAcc().getValue();
        this.method = method;
    }
    
    @Override
    public String toString(){
        return date.toString() + " " + method + ": " + amount + " " + getShare().getName() + "-Aktien(" + (double)shareValue/100 + "€); Betrag: " + (double)(amount*shareValue)/100 + "; neuer Kontostand: " + (double)cAccValue/100;
    }

    

    public Share getShare() {
        return share;
    }

    public void setShare(Share share) {
        this.share = share;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public int compare(Transaction t1, Transaction t2) {
        if (this.getShare().getName() == t2.getShare().getName()) 
            return (int) (((this.getTime() - 1000) - (t2.getTime()) - 1000));
        else{
            if(this.getShare().getName().substring(0, 1).toCharArray()[0] < t2.getShare().getName().substring(0, 1).toCharArray()[0]){
                return -1;
            }else{
                return 0;
            }
        }
    }

    @Override
    public int compareTo(Transaction t2) {
        return 0;
    }
}
