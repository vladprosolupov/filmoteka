package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import web.dao.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.exceptions.ParsingJsonToDaoException;
import web.model.FilmJSON;
import web.model.FilmJSONAdmin;
import web.model.FilmJSONIndex;
import web.model.FilmJSONSearch;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Rostyk on 12.06.2017.
 */
@Service("FilmService")
@Transactional
public class FilmService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private SearchService searchService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private LinkToNetworkService linkToNetworkService;

    @Autowired
    private StudioService studioService;

    @Autowired
    private FilmActorService filmActorService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private AwardService awardService;

    @Autowired
    private TrailerService trailerService;

    @Autowired
    private ScreenshotService screenshotService;

    private static final Logger log = LogManager.getLogger(FilmService.class);


    public List<FilmJSONAdmin> getAllFilms() {
        log.info("getAllFilms()");

        Session session = sessionFactory.getCurrentSession();
        List<FilmJSONAdmin> listOfFilms = session.createQuery("select f.title, f.releaseDate, f.rating, f.lenght from FilmDb f").list();

        log.info("getAllFilms() returns : listOfFilms.size()=" + listOfFilms.size());
        return listOfFilms;
    }

    public String getTitleOfFilmWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getTitleOfFilmWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        String title = (String) session.createQuery("select f.title from FilmDb f where f.id=" + id).list().get(0);

        log.info("getTitleOfFilmWithId() returns : title=" + title);
        return title;
    }

    public FilmDb getFilmWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getFilmWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        FilmDb o = (FilmDb) session.createQuery("from FilmDb f where f.id=" + id).list().get(0);

        log.info("getFilmWithId() returns : o=" + o);
        return o;
    }

    public void saveOrUpdate(FilmDb filmToSave) throws HibernateException {
        log.info("saveOrUpdate(filmToSave=" + filmToSave + ")");

        if (filmToSave == null) {
            log.error("Error : filmToSave is null");

            throw new IllegalArgumentException("FilmToSave should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(filmToSave);

        log.info("succ. saved or updated film");
    }

    public void delete(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from FilmDb f where f.id=" + id).executeUpdate();

        log.info("succ. deleted film");
    }

    public List<FilmJSONIndex> getFilmsForIndexPage(int page) throws HibernateException {
        log.info("getFilmsForIndex(page=" + page + ")");

        Session session = sessionFactory.getCurrentSession();
        int limit = 10;
        int start = (page - 1) * limit;
        List<FilmJSONIndex> list = session.createQuery("select F.title, F.releaseDate, F.cover, F.id, F.rating from FilmDb F order by F.releaseDate desc").setFirstResult(start).setMaxResults(limit).list();
        return list;
    }

    public long getNumberOfFilms() throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        long result = (long)session.createQuery("select count(F.id) from FilmDb F").list().get(0);
        return result;
    }

    public List<FilmJSONIndex> getFilmsForNewPage(int page) throws HibernateException {
        log.info("getFilmsForNewPage(page=" + page + ")");

        Session session = sessionFactory.getCurrentSession();
        int limit = 10;
        int start = (page - 1) * limit;
        List<FilmJSONIndex> list = session.createQuery("select F.title, F.releaseDate, F.cover, F.id, F.rating from FilmDb F order by F.rating desc").setFirstResult(start).setMaxResults(limit).list();
        return list;
    }

    public List<FilmJSONSearch> getFilmsWithTitleForQuick(String title) throws HibernateException {
        log.info("getFilmsWithTitleForQuick(title=" + title + ")");

        Session session = sessionFactory.getCurrentSession();
        int limit = 10;
        List<FilmJSONSearch> list = session.createQuery("select F.id, F.title from FilmDb F where F.titleSearch like '%" + title + "%' order by charindex('" + title + "', F.titleSearch)").setMaxResults(limit).list();

        log.info("getFilmsWithTitleForQuick() returns : list.size()=" + list.size());
        return list;
    }

    public long getNumberOfFilmsWithTitle(String title) throws HibernateException {
        log.info("getNumberOfFilmsWithTitle(title=" + title + ")");

        Session session = sessionFactory.getCurrentSession();
        long result = (long)session.createQuery("select count (f.id) from FilmDb f where f.titleSearch like '%" + title + "%'").list().get(0);

        log.info("getNumberOfFilmsWithTitle() returns: result=" + result);
        return result;
    }

    public List<FilmJSONIndex> getFilmsWithTitle(String title, String page) throws HibernateException, NumberFormatException {
        log.info("getFilmsWithTitle(title=" + title + ")");

        int limit = 10;
        int start = (Integer.parseInt(page) - 1) * limit;
        Session session = sessionFactory.getCurrentSession();
        List<FilmJSONIndex> list = session.createQuery("select F.title, F.releaseDate, F.cover, F.id, F.rating from FilmDb F where F.titleSearch like '%" + title + "%' order by charindex('" + title + "', F.titleSearch)").setFirstResult(start).setMaxResults(limit).list();

        log.info("getFilmsWithTitle() returns : list.size()=" + list.size());
        return list;
    }


    public FilmDb convert(FilmJSON filmJSON) throws ParsingJsonToDaoException, ParseException {
        log.info("convert(filmJSON=" + filmJSON + ")");

        if (filmJSON == null) {
            log.error("Error : filmJSON is null");

            throw new IllegalArgumentException("FilmJSON should not be null");
        }
        FilmDb filmDb = new FilmDb();
        filmDb.setId(filmJSON.getId());
        filmDb.setAge(filmJSON.getAge());
        filmDb.setBudget(filmJSON.getBudget());
        filmDb.setCover(filmJSON.getCover());
        filmDb.setDescription(filmJSON.getDescription());
        filmDb.setTitle(filmJSON.getTitle());
        filmDb.setLenght(filmJSON.getLenght());
        filmDb.setRating(filmJSON.getRating());
        filmDb.setLanguageByIdLanguage(languageService.getLanguageWithId(filmJSON.getLanguage()));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = format.parse(filmJSON.getReleaseDate());
        filmDb.setReleaseDate(new java.sql.Date(parsed.getTime()));

        filmDb.setSlogan(filmJSON.getSlogan());

        filmDb.setTitleSearch(searchService.titleToTitleSearch(filmJSON.getTitle()));

        Set<CategoryDb> setOfCategories = new HashSet<>();
        for (String c : filmJSON.getCategories()) {
            log.info("for loop");

            setOfCategories.add(categoryService.getCategoryWithId(c));
        }
        filmDb.setFilmCategories(setOfCategories);

        Set<CountryDb> setOfCountries = new HashSet<>();
        for (String s : filmJSON.getCountries()) {
            log.info("for loop");

            setOfCountries.add(countryService.getCountryWithId(s));
        }
        filmDb.setFilmCountries(setOfCountries);

        Set<DirectorDb> setOfDirectors = new HashSet<>();
        for (String s : filmJSON.getDirectors()) {
            log.info("for loop");

            setOfDirectors.add(directorService.getDirectorWithId(s));
        }
        filmDb.setFilmDirectors(setOfDirectors);

        Set<LinkToNetworkDb> setOfLinkToNetworks =
                linkToNetworkService.createSetOfLinkToNetwork(filmJSON.getNetworks());
        filmDb.setFilmNetworks(setOfLinkToNetworks);

        Set<StudioDb> setOfStudios = new HashSet<>();
        for (String s : filmJSON.getStudios()) {
            log.info("for loop");

            setOfStudios.add(studioService.getStudioWithId(s));
        }
        filmDb.setFilmStudios(setOfStudios);

        Set<FilmActorDb> setOfFilmActors = filmActorService.createSetOfFilmActor(filmJSON.getActors());
        filmDb.setFilmActorsById(setOfFilmActors);


        Set<AwardDb> setOfAwards = awardService.createSetOfAwards(filmJSON.getAwards());
        filmDb.setAwardsById(setOfAwards);


        Set<TrailerDb> setOfTrailers = trailerService.createTrailerDbSet(filmJSON.getTrailers());
        filmDb.setTrailersById(setOfTrailers);

        Set<ScreenshotDb> setOfScreenShots = screenshotService.createScreenshotSet(filmJSON.getScreenshots());
        filmDb.setScreenshotsById(setOfScreenShots);

        log.info("convert() returns : filmDb=" + filmDb);
        return filmDb;
    }
}
