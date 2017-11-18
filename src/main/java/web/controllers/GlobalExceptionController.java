package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ErrorJSON;

@ControllerAdvice
public class GlobalExceptionController {


    private static final Logger log = LogManager.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ErrorJSON indexOutOfBoundExceptionOccur (IndexOutOfBoundsException e){
        log.error("indexOutOfBoundExceptionOccur (e=" + e + ")");
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getCause().getLocalizedMessage());
        errorJSON.setStatusCode("500 Server Error");

        log.error("indexOutOfBoundExceptionOccur () returns : errorJSON=" + errorJSON);
        return errorJSON;
    }

    @ExceptionHandler(HibernateException.class)
    public ErrorJSON hibernateExceptionOccur(HibernateException e) {
        log.error("hibernateExceptionOccur(e=" + e + ")");
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getCause().getLocalizedMessage());
        errorJSON.setStatusCode("500 Database error");

        log.error("hibernateExceptionOccur() returns : errorJSON=" + errorJSON);
        return errorJSON;
    }

    @ExceptionHandler(ParsingJsonToDaoException.class)
    public ErrorJSON parsingJSONExceptionOccur(ParsingJsonToDaoException e) {
        log.error("parsingJSONExceptionOccur(e=" + e + ")");
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getCause().getLocalizedMessage());
        errorJSON.setStatusCode("500 Database error");

        log.error("parsingJSONExceptionOccur() returns : errorJSON=" + errorJSON);
        return errorJSON;
    }
}
