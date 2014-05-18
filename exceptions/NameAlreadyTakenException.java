package exceptions;

public class NameAlreadyTakenException extends RuntimeException{
    private String s;
    
    public NameAlreadyTakenException(){
        super();
    }
    
    public NameAlreadyTakenException(String s){
        super(s);
    }
    
    public String getS(){
        return s;
    }
    
}
