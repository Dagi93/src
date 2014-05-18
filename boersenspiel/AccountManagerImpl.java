package boersenspiel;

import java.util.logging.Level;
import java.util.logging.Logger;

import exceptions.*;

public class AccountManagerImpl implements AccountManager {

    private Player[] gambler = new Player[0];
//    private Transaction lastTransaction;
    private StockPriceProvider provider;
    private static Logger log = Logger.getLogger(AccountManagerImpl.class.getName());

    public AccountManagerImpl(StockPriceProvider provider) {
        this.provider = provider;
    }

    public String newPlayer(String name) throws PlayerNotFoundException {

        if(gambler.length > 0)
            log.log(Level.FINER, gambler.length + " Spieler vor Hinzufügen von " + name + " vorhanden.");
        
        Player bob = new Player(name);
        if ((search(gambler, name)) == null) {
            gambler = copy(gambler, newPlayerArray(gambler), bob);
        } else {
            throw new NameAlreadyTakenException("Dieser Spieler existiert bereits.");
        }
        return "Spieler " + name + " wurde hinzugefügt.";
    }

    public String buy(String playerName, String shareName, int amount) throws PlayerNotFoundException, NotEnoughMoneyException {

        Player bob = search(this.gambler, playerName);
        if (bob == null)
            throw new PlayerNotFoundException("Der Spieler mit dem Namen " + playerName + " wurde nicht gefunden.");
        Share share = provider.search(StockPriceProvider.shareCollection, shareName);
        ShareItem temp = new ShareItem(share, amount);

        if (temp.getValue() <= bob.getCAcc().getValue()) {
            log.log(Level.FINER, playerName + " besitzt genügend Geld für " + shareName);
            if (!bob.getSAcc().search(bob.getSAcc().getCollection(), share)) {
                log.log(Level.FINER, playerName + " besitzt noch keine Aktien vom Typ " + shareName);
                bob.getSAcc().expandCollection(bob, temp);
            } else {
                log.log(Level.FINER, playerName + " besitzt bereits Aktien vom Typ " + shareName);
                for (int index = 0; index < bob.getSAcc().getCollection().length; index++) {
                    if (bob.getSAcc().getCollection()[index].getName().equals(temp.getName())) {
                        bob.getSAcc().setCollection(index, temp);
                    }
                }
            }
            bob.getCAcc().setValue(-(amount * share.getValue()));
//            lastTransaction = new Transaction(bob.getName(), temp);
        } else {
            throw new NotEnoughMoneyException("Nicht genügend Geld.");
        }
        return "Spieler " + playerName + " hat " + amount + " " + shareName + "-Aktien zum Preis von " + share.getValue() + " gekauft.";

    }

    public String sell(String playerName, String shareName, int amount) throws PlayerNotFoundException, ShareNotFoundException, NotEnoughSharesException, NotEnoughMoneyException{

        Player bob = search(this.gambler, playerName);
        Share share = provider.search(StockPriceProvider.shareCollection, shareName);
        ShareItem item = new ShareItem(share, amount);

        if (share != null && bob != null) {
            try{
            if (search(bob.getSAcc().getCollection(), share.getName()) != null && search(bob.getSAcc().getCollection(), share.getName()).getSAmount() > amount) {
                log.log(Level.FINER, playerName + " verkauft weniger Aktien vom Typ " + shareName + ", als er/ sie besitzt.");
                buy(bob.getName(), share.getName(), -amount);
            } else if (search(bob.getSAcc().getCollection(), share.getName()) != null && search(bob.getSAcc().getCollection(), share.getName()).getSAmount() == amount) {
                log.log(Level.FINER, playerName + " verkauft alle Aktien vom Typ " + shareName + " die er/ sie besitzt.");
                buy(bob.getName(), share.getName(), -amount);
                bob.getSAcc().shortenCollection(bob, item);
//                lastTransaction = new Transaction(bob.getName(), item);
            } else {
                log.log(Level.FINER, playerName + " hat versucht, mehr Aktien vom Typ " + shareName + " zu verkaufen, als er besitzt." );
                throw new ShareNotFoundException("Der Spieler besitzt nicht genügend Aktien.");
            }
            }catch(NotEnoughMoneyException e){
                log.log(Level.SEVERE, "NotEnoughMoneyException wurde in sell geworfen, obwohl dies nicht passieren dürfte. ", new NotEnoughMoneyException("Diese Exception wird nicht geworfen."));
            }

        } else {
            log.log(Level.SEVERE, "Spieler- oder Aktienname nicht gefunden.", new NotEnoughMoneyException("Spieler- oder Aktienname nicht gefunden."));
            throw new NullPointerException("Spieler- oder Aktienname nicht gefunden.");
        }
        return "Spieler " + playerName + " hat " + amount + " " + shareName + "-Aktien zum Preis von " + share.getValue() + " verkauft.";
    }

    public long getCashValueOf(String playerName) throws PlayerNotFoundException {

        Player bob = search(gambler, playerName);
        if (bob == null)
            throw new PlayerNotFoundException("Spieler nicht gefunden.");
        return bob.getCAcc().getValue();
    }

    public long getSharesValueOf(String playerName) throws PlayerNotFoundException {

        Player bob = search(gambler, playerName);
        if (bob == null)
            throw new PlayerNotFoundException("Spieler nicht gefunden.");
        ShareItem[] siTemp = bob.getSAcc().getCollection();
        long value = 0;

        if (siTemp.length > 0) {
            for (int index = 0; index < siTemp.length; index++) {
                ShareItem sTemp = siTemp[index];
                value += provider.search(StockPriceProvider.shareCollection, sTemp.getName()).getValue() * siTemp[index].getSAmount();
            }
            return value;
        } else {
            return 0;
        }
    }

    public long getAllAssetsOf(String playerName) throws PlayerNotFoundException {

        Player bob = search(gambler, playerName);
        if (bob == null)
            throw new PlayerNotFoundException("Der Spieler mit dem Namen " + playerName + " wurde nicht gefunden.");
        return (bob.getCAcc().getValue()) + (bob.getSAcc().getValue());
    }

    public boolean checkForProfit(String playerName, String shareName) throws PlayerNotFoundException, ShareNotFoundException {
        Player bob = search(gambler, playerName);
        if (bob == null)
            throw new PlayerNotFoundException("Der Spieler mit dem Namen " + playerName + " wurde nicht gefunden.");

        Share share = provider.search(StockPriceProvider.shareCollection, shareName);
        if (share == null){
            log.log(Level.FINER, "Aktie wurde nicht gefunden.");
            throw new ShareNotFoundException("Diese Aktie wurde nicht gefunden.");
        }
        long meanValue = 0;

        for (int index = 0; index < bob.getSAcc().getCollection().length; index++) {
            if (bob.getSAcc().getCollection()[index].getName().equals(share.getName())) {
                meanValue = bob.getSAcc().getCollection()[index].getValue() / bob.getSAcc().getCollection()[index].getSAmount();
            }
        }

        if (meanValue < share.getValue())
            return true;
        else
            return false;

    }

    public Player search(Player[] gambler, String name) {

        if (gambler.length > 0) {
            for (int index = 0; index < gambler.length; index++) {
                if (gambler[index].getName().equals(name)) {
                    return gambler[index];
                }
            }
        }
        return null;
    }

    public ShareItem search(ShareItem[] collection, String shareName) throws ShareNotFoundException {

        if (collection.length > 0) {
            for (int index = 0; index < collection.length; index++) {
                if (collection[index].getName().equals(shareName)) {
                    return collection[index];
                }
            }
        }
        log.log(Level.SEVERE, "Dieses Aktienpaket befindet sich nicht im Aktiendepot", new ShareNotFoundException("Dieses Aktienpaket befindet sich nicht im Aktiendepot"));
        throw new ShareNotFoundException("Dieses Aktienpaket befindet sich nicht im Aktiendepot");
    }

    public Player[] newPlayerArray(Player[] oldPlayerArray) {

        Player[] newPlayerArray = new Player[oldPlayerArray.length + 1];
        return newPlayerArray;
    }

    public Player[] copy(Player[] gambler, Player[] newPlayerArray, Player bob) {

        for (int index = 0; index < newPlayerArray.length; index++) {
            if (index != newPlayerArray.length - 1) {
                newPlayerArray[index] = gambler[index];
            } else {
                newPlayerArray[index] = bob;
            }
        }
        return newPlayerArray;
    }

    public void turnAgentOn(String playerName) throws PlayerNotFoundException {
        Player bob = search(gambler, playerName);
        PlayerAgent agent = new RandomPlayerAgent(bob, provider);
        agent.startProcess(this);
    }
    
    public Player[] getGambler(){
        return gambler;
    }

}
