package exceptions;

public class NotEnoughMoneyException extends Throwable {

    public NotEnoughMoneyException(){
        super();
    }
    
    public NotEnoughMoneyException(String s){
        super(s);
    }
}
