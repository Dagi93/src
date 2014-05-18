package boersenspiel;

import java.util.Comparator;

public class TransTimeComparator implements Comparator<Transaction> {

    @Override
    public int compare(Transaction t1, Transaction t2) {
        return (int) ((t1.getTime() - 1000) - (t2.getTime() - 1000));
    }

}