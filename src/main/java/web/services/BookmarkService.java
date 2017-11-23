package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.BookmarkDb;
import web.dao.ClientDb;
import web.dao.FilmDb;
import web.embeddable.Bookmark;
import web.model.BookmarkJSON;

@Service("BookmarkService")
@Transactional
public class BookmarkService {
    private static final Logger log = LogManager.getLogger(BookmarkService.class);

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ClientService clientService;

    @Autowired
    private FilmService filmService;

    public void saveOrUpdate(BookmarkDb bookmarkDb) throws HibernateException {
        log.info("saveOrUpdate(bookmarkDb=" + bookmarkDb + ")");

        if (bookmarkDb == null) {
            log.error("Error : bookmarkDb is null");

            throw new IllegalArgumentException("BookmarkDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(bookmarkDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved or updated bookmark");
    }

    public void delete(BookmarkDb bookmarkDb) throws HibernateException {
        log.info("delete(bookmarkDb=" + bookmarkDb + ")");

        if (bookmarkDb == null) {
            log.error("Error : bookmarkDb is null");

            throw new IllegalArgumentException("BookmarkDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(bookmarkDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted bookmark");
    }

    public Bookmark convertToBookmarkFromBookmarkJSON(BookmarkJSON bookmarkJSON) {
        log.info("convertToBookmarkFromBookmarkJSON(bookmarkJSON=" + bookmarkJSON + ")");

        FilmDb filmDb = filmService.getFilmWithId(String.valueOf(bookmarkJSON.getIdFilm()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("Error : user is not logged in");

            throw new IllegalArgumentException("User is not logged in");
        }
        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        Bookmark bookmark = new Bookmark();
        bookmark.setClientByIdClient(clientDb);
        bookmark.setFilmByIdFilm(filmDb);

        log.info("convertToBookmarkFromBookmarkJSON returns :" + bookmark + ")");
        return bookmark;
    }


    public BookmarkDb convertToBookmarkDbFromBookmark(Bookmark bookmark) {

        log.info("convertToBookmarkDbFromBookmark(bookmark=" + bookmark+ ")");
        BookmarkDb result = new BookmarkDb();
        result.setBookmark(bookmark);
        log.info("convertToBookmarkDbFromBookmark returns :" + result + ")");
        return result;
    }


}
