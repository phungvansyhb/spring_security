package lg.frontend.spring_security_section1.exceptions;

public class ItemQuantityException extends RuntimeException{
    public ItemQuantityException(String message){
        super(message);
    }
}
