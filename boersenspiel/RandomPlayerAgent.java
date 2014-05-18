package boersenspiel;

import java.util.Timer;
import java.util.TimerTask;

import exceptions.*;

public class RandomPlayerAgent implements PlayerAgent {

    private Player bob;
    private StockPriceProvider provider;

    public RandomPlayerAgent(Player bob, StockPriceProvider provider) {
        this.bob = bob;
        this.provider = provider;

    }

    @Override
    public void buy(AccountManagerImpl impl) {
        int sharePosition = (int) (Math.random() * StockPriceProvider.shares.size());
        Share[] sa = (Share[])StockPriceProvider.shares.toArray();
        String share = sa[sharePosition].getName();
        int amount = (int) (Math.random() * 30) + 1;

        try {
            impl.buy(bob.getName(), share, amount);
        } catch (PlayerNotFoundException e) {
            System.out.println("Spieler nicht gefunden.");
        } catch (NotEnoughMoneyException e) {
            System.out.println("Nicht genügend Geld.");
        }
    }

    @Override
    public void sell(AccountManagerImpl impl) {
        int amount = 0;
        
        if (bob.getSAcc().getItems().size() > 0) {
            int sharePosition = (int) (Math.random() * bob.getSAcc().getItems().size());
            Share[] sa = (Share[])StockPriceProvider.shares.toArray();
            String share = sa[sharePosition].getName();
            for(ShareItem si : bob.getSAcc().getItems().values())
                if(si.getName().equals(share)){
                    amount = (int) (Math.random() * si.getAmount()) + 1;
                }
            try {
                impl.sell(bob.getName(), share, amount);
            } catch (PlayerNotFoundException e) {
                System.out.println("Spieler nicht gefunden.");
            } catch (ShareNotFoundException e) {
                System.out.println("Aktie nicht gefunden.");
                e.printStackTrace();
            } catch (NotEnoughSharesException e) {
                System.out.println("Nicht genügend Aktien.");
                e.printStackTrace();
            } catch (NotEnoughMoneyException e) {
                System.out.println("Nicht genügend Geld.");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doTransaction(AccountManagerImpl impl) {
        int decision = (int) (Math.random() * 10);
        if (decision < 5)
            buy(impl);
        else
            sell(impl);
    }

    @Override
    public void startProcess(final AccountManagerImpl impl) {

        Timer agentTimer = Zeitgeist.getInstance();
        agentTimer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println("It works!");

                doTransaction(impl);

            }
        }, 2000, 3000);

    }

}
