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
import web.model.FilmJSONIndex;

import java.util.List;

@Service("BookmarkService")
@Transactional
public class BookmarkService {
    private static final Logger log = LogManager.getLogger(BookmarkService.class);

    private SessionFactory sessionFactory;

    public BookmarkService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

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

    public boolean checkBookmarkFilm(String id) {
        log.info("checkBookmarkFilm(idFilm=" + id + ")");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("Error : user is not logged in");

            throw new IllegalArgumentException("User is not logged in");
        }
        ClientService clientService = new ClientService(sessionFactory);
        int clientId = clientService.getClientIdByLogin(authentication.getName());

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List list = session.createQuery("select b.id from BookmarkDb b where b.id.clientByIdClient.id = " + clientId + " and b.id.filmByIdFilm.id = " + id).list();
        session.getTransaction().commit();
        session.close();

        boolean result = !list.isEmpty();
        log.info("checkBookmarkFilm() returns :" + result);

        return result;
    }

    public Bookmark convertToBookmarkFromBookmarkJSON(BookmarkJSON bookmarkJSON, ClientDb clientDb) {
        log.info("convertToBookmarkFromBookmarkJSON(bookmarkJSON=" + bookmarkJSON + ")");

        FilmService filmService = new FilmService(sessionFactory);

        FilmDb filmDb = filmService.getFilmWithId(String.valueOf(bookmarkJSON.getIdFilm()));

        Bookmark bookmark = new Bookmark();
        bookmark.setClientByIdClient(clientDb);
        bookmark.setFilmByIdFilm(filmDb);

        log.info("convertToBookmarkFromBookmarkJSON() returns :" + bookmark + ")");
        return bookmark;
    }


    public BookmarkDb convertToBookmarkDbFromBookmark(Bookmark bookmark) {

        log.info("convertToBookmarkDbFromBookmark(bookmark=" + bookmark + ")");
        BookmarkDb result = new BookmarkDb();
        result.setBookmark(bookmark);
        log.info("convertToBookmarkDbFromBookmark returns :" + result + ")");
        return result;
    }


    public List<FilmJSONIndex> getBookmarkedFilms(String p) throws NumberFormatException {
        log.info("getBookmarkedFilms(page=" + p + ")");

        int page = Integer.parseInt(p);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("Error : user is not logged in");

            throw new IllegalArgumentException("User is not logged in");
        }
        ClientService clientService = new ClientService(sessionFactory);
        int clientId = clientService.getClientIdByLogin(authentication.getName());

        Session session = sessionFactory.openSession();
        int limit = 10;
        int start = (page - 1) * limit;
        session.beginTransaction();
        List<FilmJSONIndex> list = session.createQuery("select b.bookmark.filmByIdFilm.title, b.bookmark.filmByIdFilm.releaseDate, b.bookmark.filmByIdFilm.cover, b.bookmark.filmByIdFilm.id, b.bookmark.filmByIdFilm.rating from BookmarkDb b where b.bookmark.clientByIdClient.id = " + clientId).setFirstResult(start).setMaxResults(limit).list();
        session.getTransaction().commit();
        session.close();

        log.info("getBookmarkedFilms(() returns : list.size()=" + list.size() + ")");
        return list;
    }

    public long getNumbersOfBookmarkByUser(ClientDb clientDb) {
        log.info("getNumbersOfBookmarkByUser(clientDb=" + clientDb + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long result = (long) session.createQuery("select count(b.bookmark.filmByIdFilm.id) from BookmarkDb b where b.bookmark.clientByIdClient.id=" + clientDb.getId()).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getNumbersOfBookmarkByUser() returns : result=" + result);
        return result;
    }

    public long getNumbersOfBookmarkByUserId(int clientId) {
        log.info("getNumbersOfBookmarkByUserId(clientId=" + clientId + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long result = (long) session.createQuery("select count(b.bookmark.filmByIdFilm.id) from BookmarkDb b where b.bookmark.clientByIdClient.id=" + clientId).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getNumbersOfBookmarkByUserId() returns : result=" + result);
        return result;
    }


}
