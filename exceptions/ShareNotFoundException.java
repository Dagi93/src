package exceptions;

public class ShareNotFoundException extends Throwable {

    public ShareNotFoundException(){
        super();
    }
    
    public ShareNotFoundException(String s){
        super(s);
    }
}
