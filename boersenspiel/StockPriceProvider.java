package boersenspiel;
import java.util.TimerTask;


public abstract class StockPriceProvider implements StockPriceInfo {

    static Share[] shareCollection = new Share[0];
    Zeitgeist gini = Zeitgeist.getInstance();
    
    public StockPriceProvider(){

      shareCollection = new Share[11];
      shareCollection[0] = new Share(1500, "bmw");
      shareCollection[1] = new Share(1200, "siemens");
      shareCollection[2] = new Share(1000, "nokia");
      shareCollection[3] = new Share(4900, "audi");
      shareCollection[4] = new Share(8900, "vw");
      shareCollection[5] = new Share(4560, "sony");
      shareCollection[6] = new Share(1542, "lenovo");
      shareCollection[7] = new Share(1212, "microsoft");
      shareCollection[8] = new Share(6266, "apple");
      shareCollection[9] = new Share(5000, "hugo boss");
      shareCollection[10] = new Share(3700, "kuka");
    }
    
    
    @Override
    public long getShareValue(String shareName) {

        Share temp = search(ConstStockPriceProvider.shareCollection, shareName);
        if (temp != null) {
            return temp.getValue();
        } else {
            throw new NullPointerException("Aktie nicht im System.");
        }
    }

    @Override
    public String allSharesToString() {
        String s = "";
        for (int index = 0; index < shareCollection.length; index++){
            s += shareCollection[index].getName() + "(" + shareCollection[index].getValue() + "), <br>"; 
        }
        return s;
    }
    
    public abstract void updateShareRate(Share share);
    
    public void updateShareRates(){
        for(int index = 0; index < shareCollection.length; index++){
            Share temp = shareCollection[index];
            
            updateShareRate(temp);
        }
    }
    
    public void startUpdate(){

        final Zeitgeist timer = Zeitgeist.getInstance();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                updateShareRates();
            }
        }, 2000, 1000);
    }

    public Share search(Share[] collection, String shareName) {

        if (collection.length > 0) {
            for (int index = 0; index < collection.length; index++) {
                if (collection[index].getName().equals(shareName)) {
                    return collection[index];
                }
            }
        }
        throw new NullPointerException("Diese Aktie befindet sich nicht im System.");
    }

}
