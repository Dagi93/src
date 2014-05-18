package boersenspiel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TimerTask;

public abstract class StockPriceProvider implements StockPriceInfo {

    static ArrayList<Share> shares = new ArrayList<Share>();
    Zeitgeist gini = Zeitgeist.getInstance();

    public StockPriceProvider() {

        shares.add(new Share(1500, "bmw"));
        shares.add(new Share(1200, "siemens"));
        shares.add(new Share(1000, "nokia"));
        shares.add(new Share(4900, "audi"));
        shares.add(new Share(8900, "vw"));
        shares.add(new Share(4560, "sony"));
        shares.add(new Share(1542, "lenovo"));
        shares.add(new Share(1212, "microsoft"));
        shares.add(new Share(6266, "apple"));
        shares.add(new Share(5000, "hugo boss"));
        shares.add(new Share(3700, "kuka"));
        
        Collections.sort(shares);
    }
        
        
    @Override
    public long getShareValue(String shareName) {

        Share temp = search(ConstStockPriceProvider.shares, shareName);
        if (temp != null) {
            return temp.getValue();
        } else {
            throw new NullPointerException("Aktie nicht im System.");
        }
    }

    @Override
    public String allSharesToString() {
        String s = "";
        for (Share share : shares ) {
            s += share.getName() + "(" + share.getValue() + "), <br>";
        }
        return s;
    }

    public abstract void updateShareRate(Share share);

    public void updateShareRates() {
        for (Share share : shares) {
            Share temp = share;

            updateShareRate(temp);
        }
    }

    public void startUpdate() {

        final Zeitgeist timer = Zeitgeist.getInstance();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                updateShareRates();
            }
        }, 2000, 1000);
    }

    public Share search(Collection<Share> c, String shareName) {

        if (c.size() > 0) {
            for (Share s : c) {
                if (s.getName().equals(shareName)) {
                    return s;
                }
            }
        }
        throw new NullPointerException("Diese Aktie befindet sich nicht im System.");
    }

}
