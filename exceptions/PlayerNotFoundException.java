package exceptions;

public class PlayerNotFoundException extends Throwable {

    public PlayerNotFoundException(){
        super();
    }
    
    public PlayerNotFoundException(String s){
        super(s);
    }
}
