package exceptions;

public class BadInputException extends Throwable{
    String message;

    public BadInputException(String string) {
        this.message = string;
    }
    
    public String getMessage(){
        return message;
    }

}
