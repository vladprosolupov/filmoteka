package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.model.BookmarkJSON;
import web.services.BookmarkService;

@Component("RemoveBookmarkTask")
public class RemoveBookmarkTask implements Runnable {

    private SessionFactory sessionFactory;

    private BookmarkJSON bookmarkJSON;
    private ClientDb clientDb;

    private static final Logger log = LogManager.getLogger(RemoveBookmarkTask.class);

    public RemoveBookmarkTask(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void run() {
        log.info("run()");

        BookmarkService bookmarkService = new BookmarkService(sessionFactory);
        bookmarkService.delete(
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
