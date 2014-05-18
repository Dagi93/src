package boersenspiel;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;

import exceptions.*;
import logging.*;

/**TO DO: 
            -checkForProfit() den Gewinn ausgeben lassen
            -feinere Logger einbauen*/


public class StockGameLauncher {
    private static Logger logger = Logger.getLogger(StockGameLauncher.class.getName());
    
    public static void main(String[] args) throws Throwable {

        logger.info( "Programm gestartet." );

        
        StockPriceProvider provider = new RandomStockPriceProvider();
        AccountManager accMan = new AccountManagerImpl(provider);

        ClassLoader loader = accMan.getClass().getClassLoader();  
        Class[] interfaces = new Class[] {AccountManager.class};
        LogHandler logHandler = new LogHandler(accMan);  
        AccountManager stockGameProxy = (AccountManager) Proxy.newProxyInstance(loader, interfaces, logHandler);
        
        provider.startUpdate();
        Viewer view = new Viewer(provider);
        view.start();
        StockGameCommandProcessor processor = new StockGameCommandProcessor(stockGameProxy);
        
        try {
            processor.process();
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());
            System.out.println("Das Programm wurde beendet.");
        }
        
    }
    
}
