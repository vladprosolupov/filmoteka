package web.exceptions;

public class ValidationError extends Exception {

    public ValidationError(String msg, Exception e) {
        super(msg, e);
    }

    public ValidationError(String msg) {
        super(msg);
    }
}
