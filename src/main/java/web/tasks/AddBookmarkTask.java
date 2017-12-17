package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.model.BookmarkJSON;
import web.services.BookmarkService;

@Component("AddBookmarkTask")
public class AddBookmarkTask implements Runnable {

    private ClientDb clientDb;
    private BookmarkJSON bookmarkJSON;

    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(AddBookmarkTask.class);

    public AddBookmarkTask(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void run() {
        log.info("run()");

        BookmarkService bookmarkService = new BookmarkService(sessionFactory);
        bookmarkService.saveOrUpdate(
                bookmarkService.convertToBookmarkDbFromBookmark(
                        bookmarkService.convertToBookmarkFromBookmarkJSON(bookmarkJSON, clientDb)));

        log.info("run() done");
    }

    public BookmarkJSON getBookmarkJSON() {
        return bookmarkJSON;
    }

    public void setBookmarkJSON(BookmarkJSON bookmarkJSON) {
        this.bookmarkJSON = bookmarkJSON;
    }

    public ClientDb getClientDb() {
        return clientDb;
    }

    public void setClientDb(ClientDb clientDb) {
        this.clientDb = clientDb;
    }
}
