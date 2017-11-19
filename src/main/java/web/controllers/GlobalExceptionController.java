package web.controllers;

import org.hibernate.HibernateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ErrorJSON;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ErrorJSON indexOutOfBoundExceptionOccur (IndexOutOfBoundsException e){
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getCause().getLocalizedMessage());
        errorJSON.setStatusCode("500 Server Error");

        return errorJSON;
    }

    @ExceptionHandler(HibernateException.class)
    public ErrorJSON hibernateExceptionOccur(HibernateException e) {
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getCause().getLocalizedMessage());
        errorJSON.setStatusCode("500 Database error");

        return errorJSON;
    }

    @ExceptionHandler(NumberFormatException.class)
    public ErrorJSON NumberFormatExceptionOccur(NumberFormatException e){
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getCause().getLocalizedMessage());
        errorJSON.setStatusCode("500 Server error");

        return errorJSON;
    }

    @ExceptionHandler(ParsingJsonToDaoException.class)
    public ErrorJSON parsingJSONExceptionOccur(ParsingJsonToDaoException e) {
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getCause().getLocalizedMessage());
        errorJSON.setStatusCode("500 Database error");

        return errorJSON;
    }
}
