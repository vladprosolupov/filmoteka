package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.model.BookmarkJSON;
import web.services.BookmarkService;

@Component("RemoveBookmarkTask")
public class RemoveBookmarkTask implements Runnable {

    @Autowired
    private BookmarkService bookmarkService;

    private BookmarkJSON bookmarkJSON;

    private static final Logger log = LogManager.getLogger(RemoveBookmarkTask.class);

    @Override
    public void run() {
        log.info("run()");

        bookmarkService.delete(
                bookmarkService.convertToBookmarkDbFromBookmark(
                        bookmarkService.convertToBookmarkFromBookmarkJSON(bookmarkJSON)));


        log.info("run() done");
    }

    public BookmarkJSON getBookmarkJSON() {
        return bookmarkJSON;
    }

    public void setBookmarkJSON(BookmarkJSON bookmarkJSON) {
        this.bookmarkJSON = bookmarkJSON;
    }
}
