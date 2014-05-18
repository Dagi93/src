package boersenspiel;

import java.util.Comparator;

public class TransNameComparator implements Comparator<Transaction> {

    @Override
    public int compare(Transaction t1, Transaction t2) {
        if (t1.getShare().getName() == t2.getShare().getName())
            return (int) (((t1.getTime() - 1000) - (t2.getTime()) - 1000));
        else {
            if (t1.getShare().getName().substring(0, 1).toCharArray()[0] < t2.getShare().getName().substring(0, 1).toCharArray()[0]) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}