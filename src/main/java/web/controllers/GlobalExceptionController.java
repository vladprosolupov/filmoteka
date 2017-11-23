package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import web.exceptions.NoSuchClientException;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ErrorJSON;

@ControllerAdvice
public class GlobalExceptionController {


    private static final Logger log = LogManager.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public @ResponseBody ErrorJSON indexOutOfBoundExceptionOccur (IndexOutOfBoundsException e){
        log.error("indexOutOfBoundExceptionOccur (e=" + e + ")");
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getMessage());
        errorJSON.setStatusCode("500 Server Error");

        log.error("indexOutOfBoundExceptionOccur () returns : errorJSON=" + errorJSON);
        return errorJSON;
    }

    @ExceptionHandler(HibernateException.class)
    public @ResponseBody ErrorJSON hibernateExceptionOccur(HibernateException e) {
        log.error("hibernateExceptionOccur(e=" + e + ")");
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getCause().getLocalizedMessage());
        errorJSON.setStatusCode("500 Database error");

        log.error("hibernateExceptionOccur() returns : errorJSON=" + errorJSON);
        return errorJSON;
    }

    @ExceptionHandler(NumberFormatException.class)
    public @ResponseBody ErrorJSON numberFormatExceptionOccur(NumberFormatException e){
        log.info("numberFormatExceptionOccur(e=" + e + ")");
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getCause().getLocalizedMessage());
        errorJSON.setStatusCode("500 Server error");

        log.error("numberFormatExceptionOccur() return : errorJSON=" + errorJSON);
        return errorJSON;
    }

    @ExceptionHandler(ParsingJsonToDaoException.class)
    public @ResponseBody ErrorJSON parsingJSONExceptionOccur(ParsingJsonToDaoException e) {
        log.error("parsingJSONExceptionOccur(e=" + e + ")");
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getCause().getLocalizedMessage());
        errorJSON.setStatusCode("500 Database error");

        log.error("parsingJSONExceptionOccur() returns : errorJSON=" + errorJSON);
        return errorJSON;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody ErrorJSON illegalArgumentExceptionOccur(IllegalArgumentException e) {
        log.error("illegalArgumentExceptionOccur(e=" + e + ")");
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getMessage());
        errorJSON.setStatusCode("500 Database error");

        log.error("illegalArgumentExceptionOccur() returns : errorJSON=" + errorJSON);
        return errorJSON;
    }

    @ExceptionHandler(NoSuchClientException.class)
    public @ResponseBody ErrorJSON noSuchClientExceptionOccur(IllegalArgumentException e) {
        log.error("noSuchClientExceptionOccur(e=" + e + ")");
        ErrorJSON errorJSON = new ErrorJSON();

        errorJSON.setName("error");
        errorJSON.setMessage(e.getMessage());
        errorJSON.setStatusCode("500 Database error");

        log.error("noSuchClientExceptionOccur() returns : errorJSON=" + errorJSON);
        return errorJSON;
    }
}
