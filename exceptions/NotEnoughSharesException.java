package exceptions;

public class NotEnoughSharesException extends Throwable {

    public NotEnoughSharesException(){
        super();
    }
    
    public NotEnoughSharesException(String s){
        super(s);
    }
}
