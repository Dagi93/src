package boersenspiel;

import java.util.Date;

public class Transaction {

    private Date date = new Date();
    private Player bob;
    private Share share;
    private int amount;
    private long shareValue;
    private long cAccValue;
    private long time = date.getTime();
    
    public Transaction(Player bob, Share share, int amount) {
        this.bob = bob;
        this.share = share;
        this.amount = amount;
        this.shareValue = share.getValue();
        this.cAccValue = bob.getCAcc().getValue();
    }
    
}
