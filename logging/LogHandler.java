package logging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Logger;

import exceptions.*;
import boersenspiel.AccountManagerImpl;

public class LogHandler implements InvocationHandler {
    private Object object;
    private static Logger log = Logger.getLogger(AccountManagerImpl.class.getName());

    public LogHandler(Object object) {
        this.object = object;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws NameAlreadyTakenException, InvocationTargetException,
            IllegalAccessException {
        String s = "";
        Object result = null;
        for (int i = 0; i < args.length; i++)
            s += args[i].toString() + " ";
        log.info(method.getName() + " " + s);

        try {
            result = method.invoke(this.object, args);
        } catch (NameAlreadyTakenException e) {
            throw e;
        } catch (IllegalAccessException e) {
            throw e;
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (InvocationTargetException e) {
            throw e;
        }

        return result;
    }
}
