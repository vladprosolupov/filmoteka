package web.exceptions;

public class ParsingJsonToDaoException extends Exception {

    ParsingJsonToDaoException(String msg, Exception e) {
        super(msg, e);
    }
}
