package boersenspiel;

import exceptions.*;

public interface PlayerAgent {

    // Player bob = null;

    void startProcess(AccountManagerImpl impl);

    void buy(AccountManagerImpl impl) throws PlayerNotFoundException;

    void sell(AccountManagerImpl impl) throws PlayerNotFoundException;

    void doTransaction(AccountManagerImpl impl) throws PlayerNotFoundException;

}
