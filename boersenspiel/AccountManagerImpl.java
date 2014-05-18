package boersenspiel;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import exceptions.*;

public class AccountManagerImpl implements AccountManager {

    private HashMap<String, Player> gambler = new HashMap<String, Player>();
    private StockPriceProvider provider;
    private static Logger log = Logger.getLogger(AccountManagerImpl.class.getName());

    public AccountManagerImpl(StockPriceProvider provider) {
        this.provider = provider;
    }

    public String newPlayer(String name) throws PlayerNotFoundException {

        log.log(Level.FINER, gambler.size() + " Spieler vor Hinzufügen von " + name + " vorhanden.");

        Player bob = new Player(name);
        if (!gambler.containsKey(bob.getName())) {
            gambler.put(bob.getName(), bob);
        } else {
            throw new NameAlreadyTakenException("Dieser Spieler existiert bereits.");
        }
        return "Spieler " + name + " wurde hinzugefügt.";
    }

    public String buy(String playerName, String shareName, int amount) throws PlayerNotFoundException, NotEnoughMoneyException {

        Player bob = gambler.get(playerName);
        if (bob == null)
            throw new PlayerNotFoundException("Der Spieler mit dem Namen " + playerName + " wurde nicht gefunden.");
        Share share = provider.search(StockPriceProvider.shares, shareName);
        ShareItem item = new ShareItem(share, amount);

        if (item.getValue() <= bob.getCAcc().getValue()) {
            log.log(Level.FINER, playerName + " besitzt genügend Geld für " + shareName);
            bob.getSAcc().add(item);
            bob.getCAcc().addValue(-(amount * share.getValue()));
        } else {
            throw new NotEnoughMoneyException("Nicht genügend Geld.");
        }
        return "Spieler " + playerName + " hat " + amount + " " + shareName + "-Aktien zum Preis von " + share.getValue() + " gekauft.";

    }

    public String sell(String playerName, String shareName, int amount) throws PlayerNotFoundException, ShareNotFoundException,
            NotEnoughSharesException, NotEnoughMoneyException {

        Player bob = gambler.get(playerName);
        Share share = provider.search(StockPriceProvider.shares, shareName);
        ShareItem item = new ShareItem(share, amount);

        if (share != null && bob != null) {
            if (bob.getSAcc().getItems().containsKey(item.getName())) {
                if (bob.getSAcc().getItems().get(item.getName()).getAmount() > item.getAmount()) {
                    bob.getSAcc().sub(item);

                } else if (bob.getSAcc().getItems().get(item.getName()).getAmount() == item.getAmount()) {
                    log.log(Level.FINER, playerName + " verkauft alle Aktien vom Typ " + shareName + " die er/ sie besitzt.");
                    bob.getSAcc().remove(item);
                    bob.getCAcc().addValue(-(item.getValue()));
                } else {
                    log.log(Level.FINER, playerName + " hat versucht, mehr Aktien vom Typ " + shareName + " zu verkaufen, als er besitzt.");
                    throw new ShareNotFoundException("Der Spieler besitzt nicht genügend Aktien.");
                }

            } else {
                log.log(Level.SEVERE, "Spieler- oder Aktienname nicht gefunden.", new NotEnoughMoneyException(
                        "Spieler- oder Aktienname nicht gefunden."));
                throw new NullPointerException("Spieler- oder Aktienname nicht gefunden.");
            }
        }
        return "Spieler " + playerName + " hat " + amount + " " + shareName + "-Aktien zum Preis von " + share.getValue() + " verkauft.";
    }

    public long getCashValueOf(String playerName) throws PlayerNotFoundException {

        Player bob = gambler.get(playerName);
        if (bob == null)
            throw new PlayerNotFoundException("Spieler nicht gefunden.");
        
        return bob.getCAcc().getValue();
    }

    public long getSharesValueOf(String playerName) throws PlayerNotFoundException {

        Player bob = gambler.get(playerName);
        long value = 0;
        if (bob == null)
            throw new PlayerNotFoundException("Spieler nicht gefunden.");

        for (ShareItem item : bob.getSAcc().getItems().values())
            value += item.getValue();
        
        return value;
    }

    public long getAllAssetsOf(String playerName) throws PlayerNotFoundException {

        Player bob = gambler.get(playerName);
        if (bob == null)
            throw new PlayerNotFoundException("Der Spieler mit dem Namen " + playerName + " wurde nicht gefunden.");
        return (bob.getCAcc().getValue()) + (bob.getSAcc().getValue());
    }

    public boolean checkForProfit(String playerName, String shareName) throws PlayerNotFoundException, ShareNotFoundException {
        Player bob = gambler.get(playerName);
        if (bob == null)
            throw new PlayerNotFoundException("Der Spieler mit dem Namen " + playerName + " wurde nicht gefunden.");

        Share share = provider.search(StockPriceProvider.shares, shareName);
        if (share == null) {
            log.log(Level.FINER, "Aktie wurde nicht gefunden.");
            throw new ShareNotFoundException("Diese Aktie wurde nicht gefunden.");
        }
        long meanValue = 0;

        for (ShareItem item : bob.getSAcc().getItems().values()) {
            if (item.getName().equals(share.getName())) {
                meanValue = item.getValue() / item.getAmount();
            }
        }

        if (meanValue < share.getValue())
            return true;
        else
            return false;
    }

    public ShareItem search(ShareItem[] collection, String shareName) throws ShareNotFoundException {

        if (collection.length > 0) {
            for (int index = 0; index < collection.length; index++) {
                if (collection[index].getName().equals(shareName)) {
                    return collection[index];
                }
            }
        }
        log.log(Level.SEVERE, "Dieses Aktienpaket befindet sich nicht im Aktiendepot", new ShareNotFoundException(
                "Dieses Aktienpaket befindet sich nicht im Aktiendepot"));
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
        Player bob = gambler.get(playerName);
        PlayerAgent agent = new RandomPlayerAgent(bob, provider);
        agent.startProcess(this);
    }

    public HashMap<String, Player> getGambler() {
        return gambler;
    }

}
