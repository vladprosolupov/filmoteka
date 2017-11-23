package web.exceptions;

public class NoSuchClientException extends Exception {

    public NoSuchClientException(String msg, Exception e) {
        super(msg, e);
    }

    public NoSuchClientException(String msg) {
        super(msg);
    }
}
