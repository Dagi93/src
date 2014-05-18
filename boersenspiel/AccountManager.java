package boersenspiel;
import exceptions.*;


public interface AccountManager {
    String newPlayer(String name) throws PlayerNotFoundException;
    String buy(String name, String shareName, int amount) throws NotEnoughMoneyException, PlayerNotFoundException;
    String sell(String PlayerName, String shareName, int amount) throws PlayerNotFoundException, ShareNotFoundException, NotEnoughSharesException, NotEnoughMoneyException;
    long getCashValueOf(String playerName) throws PlayerNotFoundException;
    long getAllAssetsOf(String playerName) throws PlayerNotFoundException;
    long getSharesValueOf(String playerName) throws PlayerNotFoundException;
    boolean checkForProfit(String playerName, String shareName) throws PlayerNotFoundException, ShareNotFoundException;
}
