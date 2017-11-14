package web.services;

import org.hibernate.HibernateException;
import web.dao.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.exceptions.ParsingJsonToDaoException;
import web.model.FilmJSON;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rostyk on 12.06.2017.
 */
@Service("FilmService")
@Transactional
public class FilmService {
    @Autowired(required = true)
    private SessionFactory sessionFactory;

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

    public List<FilmDb> getAllFilms() throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        List<FilmDb> listOfFilms = session.createQuery("FROM FilmDb").list();
        return listOfFilms;
    }

    public FilmDb getFilmWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        return (FilmDb) session.createQuery("from FilmDb f where f.id=" + id).list().get(0);
    }

    public void saveOrUpdate(FilmDb filmToSave) throws HibernateException {
        if (filmToSave == null) {
            throw new IllegalArgumentException("FilmToSave should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(filmToSave);
    }

    public void delete(String id) throws HibernateException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from FilmDb f where f.id=" + id).executeUpdate();
    }

    public FilmDb convert(FilmJSON filmJSON) throws ParsingJsonToDaoException, ParseException {
        if (filmJSON == null) {
            throw new IllegalArgumentException("FilmJSON shouuld not be null");
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

        Set<CategoryDb> setOfCategories = new HashSet<>();
        for (String c : filmJSON.getCategories()) {
            setOfCategories.add(categoryService.getCategoryWithId(c));
        }
        filmDb.setFilmCategories(setOfCategories);

        Set<CountryDb> setOfCountries = new HashSet<>();
        for (String s : filmJSON.getCountries()) {
            setOfCountries.add(countryService.getCountryWithId(s));
        }
        filmDb.setFilmCountries(setOfCountries);

        Set<DirectorDb> setOfDirectors = new HashSet<>();
        for (String s : filmJSON.getDirectors()) {
            setOfDirectors.add(directorService.getDirectorWithId(s));
        }
        filmDb.setFilmDirectors(setOfDirectors);

        Set<LinkToNetworkDb> setOfLinkToNetworks =
                linkToNetworkService.createSetOfLinkToNetwork(filmJSON.getNetworks());
        filmDb.setFilmNetworks(setOfLinkToNetworks);

        Set<StudioDb> setOfStudios = new HashSet<>();
        for (String s : filmJSON.getStudios()) {
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

        return filmDb;
    }
}
