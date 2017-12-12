package web.services;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.*;
import web.model.aiModel.CombinedFilm;
import web.tasks.*;

import javax.persistence.criteria.Subquery;
import java.sql.Date;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service("AiService")
@Transactional
public class AiService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private FilmService filmService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientDataService clientDataService;

    @Autowired
    private GetPropCategoryTask getPropCategoryTask;

    @Autowired
    private GetPropActorTask getPropActorTask;

    @Autowired
    private GetPropDirectorTask getPropDirectorTask;

    @Autowired
    private GetPropStudioTask getPropStudioTask;

    @Autowired
    private GetPropCountryTask getPropCountryTask;

    @Autowired
    private GetPropDatesTask getPropDatesTask;

    private final int categoryPoints = 10;
    private final int ratingPoints = 8;
    private final int countryPoints = 3;
    private final int actorsPoints = 3;
    private final int directorsPoints = 3;
    private final int studiosPoints = 3;
    private final int releaseDatePoints = 2;
    private final int titlePoints = 2;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private static final Logger log = LogManager.getLogger(AiService.class);

    public double getComparisonMapInteger(Map<Integer, Integer> map, Set<Integer> set) {
        log.info("getComparisonMapInteger(map=" + map + ", set=" + set + ")");

        int fullSize = map.size();
        int counter = 0;
        for (int i : set) {
            if (map.containsKey(i))
                counter += map.get(i);
        }
        if (fullSize == 0) {
            return 0;
        }

        double result = ((double) counter / (double) fullSize);

        log.info("getComparisonMapInteger() returns : result=" + result);
        return result;
    }

    public double getComparisonMapString(Map<String, Integer> map, String string) {
        log.info("getComparisonMapString(map=" + map + ", string=" + string + ")");

        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        double counter = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            counter += (1 - (double) levenshteinDistance.apply(entry.getKey(), string) / Math.max(entry.getKey().length(), string.length())) * entry.getValue();
        }

        log.info("getComparisonMapString() returns : counter=" + counter);
        return counter;
    }

    public double getComparisonMapDate(Map<Date, Integer> map, Date date) {
        log.info("getComparisonMapDate(map=" + map + ", date=" + date + ")");

        double counter = 0;
        for (Map.Entry<Date, Integer> entry : map.entrySet()) {
            counter += (1 / Math.max(1, Math.abs(entry.getKey().getTime() - date.getTime()))) * entry.getValue();
        }

        log.info("getComparisonMapDate() returns : counter=" + counter);
        return counter;
    }


    private CombinedFilm getCombinedFilmForClient(ClientDb currentClient) {
        log.info("getCombinedFilmForClient(currentClient=" + currentClient + ")");

        CombinedFilm combinedFilm = new CombinedFilm();

        Session session = sessionFactory.openSession();

        //Getting start row
        session.beginTransaction();
        long start = Math.max(0, ((long) session.createQuery("select count(fl.filmLike.filmByIdFilm.id) from FilmLikeDb fl where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).list().get(0)) - 42);
        session.getTransaction().commit();


        //Getting categories
        session.beginTransaction();
        Map<Integer, Integer> categories = new HashMap<>();
        List<CategoryDb> categoryDbList = session.createQuery("select f.filmCategories from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int) start).setMaxResults(42).list();
        categoryDbList.forEach(categoryDb -> categories.merge(categoryDb.getId(), 1, Integer::sum));
        combinedFilm.setCategories(categories);
        session.getTransaction().commit();


        //Getting titles
        session.beginTransaction();
        Map<String, Integer> titles = new HashMap<>();
        List<String> searchTitles = session.createQuery("select f.titleSearch from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int) start).setMaxResults(42).list();
        searchTitles.forEach(s -> titles.merge(s, 1, Integer::sum));
        combinedFilm.setTitles(titles);
        session.getTransaction().commit();


        //Getting countries
        session.beginTransaction();
        Map<Integer, Integer> countries = new HashMap<>();
        List<CountryDb> countryDbList = session.createQuery("select f.filmCountries from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int) start).setMaxResults(42).list();
        countryDbList.forEach(countryDb -> countries.merge(countryDb.getId(), 1, Integer::sum));
        combinedFilm.setCountries(countries);
        session.getTransaction().commit();


        //Getting actors
        session.beginTransaction();
        Map<Integer, Integer> actors = new HashMap<>();
        List<ActorDb> actorsDbList = session.createQuery("select f.actorByIdActor from FilmActorDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.filmByIdFilm.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int) start).setMaxResults(42).list();
        actorsDbList.forEach(actorDb -> actors.merge(actorDb.getId(), 1, Integer::sum));
        combinedFilm.setActors(actors);
        session.getTransaction().commit();


        //Getting directors
        session.beginTransaction();
        Map<Integer, Integer> directors = new HashMap<>();
        List<DirectorDb> directorDbList = session.createQuery("select f.filmDirectors from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int) start).setMaxResults(42).list();
        directorDbList.forEach(directorDb -> directors.merge(directorDb.getId(), 1, Integer::sum));
        combinedFilm.setDirectors(directors);
        session.getTransaction().commit();


        //Getting studios
        session.beginTransaction();
        Map<Integer, Integer> studios = new HashMap<>();
        List<StudioDb> studioDbList = session.createQuery("select f.filmStudios from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int) start).setMaxResults(42).list();
        studioDbList.forEach(studioDb -> studios.merge(studioDb.getId(), 1, Integer::sum));
        combinedFilm.setStudios(studios);
        session.getTransaction().commit();


        //Getting release dates
        session.beginTransaction();
        Map<Date, Integer> dates = new HashMap<>();
        List<Date> sqlDates = session.createQuery("select f.releaseDate from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int) start).setMaxResults(42).list();
        sqlDates.forEach(date -> dates.merge(date, 1, Integer::sum));
        combinedFilm.setReleaseDates(dates);
        session.getTransaction().commit();
        session.close();

        log.info("getCombinedFilmForClient() returns : combinedFilm=" + combinedFilm);
        //returning combined film
        return combinedFilm;
    }

    public double compareCombinedWithNormal(CombinedFilm combinedFilm, FilmDb normalFilm) {
        log.info("compareCombinedWithNormal(combinedFilm=" + combinedFilm + ", normalFilm=" + normalFilm + ")");

        double aiPoints = 0;
        Set<Integer> categories = new HashSet<>();
        normalFilm.getFilmCategories().forEach(categoryDb -> categories.add(categoryDb.getId()));
        aiPoints += getComparisonMapInteger(combinedFilm.getCategories(), categories) * categoryPoints;

        aiPoints += getComparisonMapString(combinedFilm.getTitles(), normalFilm.getTitleSearch()) * titlePoints;

        Set<Integer> countries = new HashSet<>();
        normalFilm.getFilmCountries().forEach(countryDb -> countries.add(countryDb.getId()));
        aiPoints += getComparisonMapInteger(combinedFilm.getCountries(), countries) * countryPoints;

        Set<Integer> actors = new HashSet<>();
        normalFilm.getFilmActorsById().forEach(filmActorDb -> actors.add(filmActorDb.getActorByIdActor().getId()));
        aiPoints += getComparisonMapInteger(combinedFilm.getActors(), actors) * actorsPoints;

        Set<Integer> directors = new HashSet<>();
        normalFilm.getFilmDirectors().forEach(directorDb -> directors.add(directorDb.getId()));
        aiPoints += getComparisonMapInteger(combinedFilm.getDirectors(), directors) * directorsPoints;

        Set<Integer> studios = new HashSet<>();
        normalFilm.getFilmStudios().forEach(studioDb -> studios.add(studioDb.getId()));
        aiPoints += getComparisonMapInteger(combinedFilm.getStudios(), studios) * studiosPoints;

        aiPoints += getComparisonMapDate(combinedFilm.getReleaseDates(), normalFilm.getReleaseDate()) * releaseDatePoints;


        aiPoints += ratingPoints * (1 - 1 / normalFilm.getRating());

        log.info("compareCombinedWithNormal() returns : aiPoints=" + aiPoints);
        return aiPoints;
    }


    public void generateFilmsForSuggestion(ClientDb currentClient) throws ExecutionException, InterruptedException {
        log.info("generateFilmsForSuggestion(currentClient=" + currentClient + ")");

        //generate CombinedFilm for current user
        CombinedFilm combinedFilm = getCombinedFilmForClient(currentClient);

        //create clientDataMap to fill it later
        Map<Integer, Double> clientDataMap = new HashMap<>();
        //Generate clientDataMap

        // For categories
        Map<Integer, Integer> categoryPercentage = calculatePercentage(combinedFilm.getCategories());
        Set<FilmDb> setOfFilms = new HashSet<>();
//        for (Map.Entry<Integer, Integer> entry : categoryPercentage.entrySet()) {
//            setOfFilms.addAll(getPropCategory(entry.getValue(), entry.getKey(), currentClient));
//        }

        getPropCategoryTask.setCurrentClient(currentClient);
        getPropCategoryTask.setPercentage(categoryPercentage);
        Future<Set<FilmDb>> futureCategory = executorService.submit(getPropCategoryTask);

        // For actors
        Map<Integer, Integer> actorPercentage = calculatePercentage(combinedFilm.getActors());
//        for (Map.Entry<Integer, Integer> entry : actorPercentage.entrySet()) {
//            setOfFilms.addAll(getPropActor(entry.getValue(), entry.getKey(), currentClient));
//        }

        getPropActorTask.setCurrentClient(currentClient);
        getPropActorTask.setPercentage(actorPercentage);
        Future<Set<FilmDb>> futureActor = executorService.submit(getPropActorTask);

        // For directors
        Map<Integer, Integer> directorPercentage = calculatePercentage(combinedFilm.getDirectors());
//        for (Map.Entry<Integer, Integer> entry : directorPercentage.entrySet()) {
//            setOfFilms.addAll(getPropDirector(entry.getValue(), entry.getKey(), currentClient));
//        }

        getPropDirectorTask.setCurrentClient(currentClient);
        getPropDirectorTask.setPercentage(directorPercentage);
        Future<Set<FilmDb>> futureDirector = executorService.submit(getPropDirectorTask);

        // For studios
        Map<Integer, Integer> studioPercentage = calculatePercentage(combinedFilm.getStudios());
//        for (Map.Entry<Integer, Integer> entry : studioPercentage.entrySet()) {
//            setOfFilms.addAll(getPropStudios(entry.getValue(), entry.getKey(), currentClient));
//        }

        getPropStudioTask.setCurrentClient(currentClient);
        getPropStudioTask.setPercentage(studioPercentage);
        Future<Set<FilmDb>> futureStudio = executorService.submit(getPropStudioTask);

        // For country
        Map<Integer, Integer> countryPercentage = calculatePercentage(combinedFilm.getCountries());
//        for (Map.Entry<Integer, Integer> entry : countryPercentage.entrySet()) {
//            setOfFilms.addAll(getPropCountry(entry.getValue(), entry.getKey(), currentClient));
//        }

        getPropCountryTask.setCurrentClient(currentClient);
        getPropCountryTask.setPercentage(countryPercentage);
        Future<Set<FilmDb>> futureCountry = executorService.submit(getPropCountryTask);

        // For dates
        Map<Integer, Integer> datesPercentage = calculatePercentage(convertMapToMapDates(combinedFilm.getReleaseDates()));
//        for (Map.Entry<Integer, Integer> entry : datesPercentage.entrySet()) {
//            setOfFilms.addAll(getPropDates(entry.getValue(), entry.getKey(), currentClient));
//        }

        getPropDatesTask.setCurrentClient(currentClient);
        getPropDatesTask.setPercentage(datesPercentage);
        Future<Set<FilmDb>> futureDates = executorService.submit(getPropDatesTask);

        while(!futureActor.isDone() || !futureCategory.isDone()
                || !futureCountry.isDone() || !futureDates.isDone()
                || !futureDirector.isDone() || !futureStudio.isDone()) {

        }

        setOfFilms.addAll(futureActor.get());
        setOfFilms.addAll(futureCategory.get());
        setOfFilms.addAll(futureCountry.get());
        setOfFilms.addAll(futureDates.get());
        setOfFilms.addAll(futureDirector.get());
        setOfFilms.addAll(futureStudio.get());

        //Generate AI points for each film
        for (FilmDb f : setOfFilms) {
            clientDataMap.put(f.getId(), compareCombinedWithNormal(combinedFilm, f));
        }

        //Sort map by values and cut it to 30 most matching results
        LinkedHashMap<Integer, Double> sortedClientDataMap =
                clientDataMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .limit(30)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));

        //Save clientDataMap
        clientDataService.saveClientDataMap(sortedClientDataMap, currentClient);

        log.info("generateFilmsForSuggestion() done");
    }

    private Map<Integer, Integer> calculatePercentage(Map<Integer, Integer> map) {
        log.info("calculatePercentage(map=" + map + ")");

        Map<Integer, Integer> percentage = new HashMap<>();

        int amount = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            amount += entry.getValue();
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            double proc = (double) entry.getValue() / (double) amount;
            proc = proc * 100;
            percentage.put(entry.getKey(), (int) proc);
        }

        log.info("calculatePercentage() returns : percentage.size()=" + percentage.size());
        return percentage;
    }

    private Map<Integer, Integer> convertMapToMapDates(Map<Date, Integer> dates) {
        log.info("convertMapToMapDates(dates=" + dates + ")");

        Map<Integer, Integer> mapOfDates = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        for (Map.Entry<Date, Integer> entry : dates.entrySet()) {
            calendar.setTime(entry.getKey());
            mapOfDates.put(calendar.get(Calendar.YEAR), entry.getValue());
        }

        log.info("convertMapToMapDates() returns : mapOfDates.size()=" + mapOfDates.size());
        return mapOfDates;
    }

    public List<FilmDb> getPropCategory(int amount, int category, ClientDb currentClient) {
        log.info("getPropCategory(amount=" + amount + ", category=" + category + ", currentClient=" + currentClient + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        DetachedCriteria subqueryLike = sqlForLikedFilms(currentClient);

        DetachedCriteria subqueryDislike = sqlForDislikedFilms(currentClient);

        List<FilmDb> list = session.createCriteria(FilmDb.class, "f")
                .createAlias("f.filmCategories", "fc")
                .add(Restrictions.eq("fc.id", category))
                .add(Subqueries.propertyNotIn("f.id", subqueryLike))
                .add(Subqueries.propertyNotIn("f.id", subqueryDislike))
                .addOrder(Order.desc("f.rating")).setMaxResults(amount).list();
        session.getTransaction().commit();
        session.close();

        log.info("getPropCategory() returns : list.size()=" + list.size());
        return list;
    }

    public List<FilmDb> getPropActor(int amount, int actor, ClientDb currentClient) {
        log.info("getPropActor(amount=" + amount + ", actor=" + actor + ", currentClient=" + currentClient + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        DetachedCriteria subqueryLike = sqlForLikedFilms(currentClient);

        DetachedCriteria subqueryDislike = sqlForDislikedFilms(currentClient);

        List<FilmDb> list = session.createCriteria(FilmDb.class, "f")
                .createAlias("f.filmActorsById", "fa")
                .add(Restrictions.eq("fa.actorByIdActor.id", actor))
                .add(Subqueries.propertyNotIn("f.id", subqueryLike))
                .add(Subqueries.propertyNotIn("f.id", subqueryDislike))
                .addOrder(Order.desc("f.rating")).setMaxResults(amount).list();
        session.getTransaction().commit();
        session.close();

        log.info("getPropActor() returns : list.size()=" + list.size());
        return list;
    }

    public List<FilmDb> getPropDirector(int amount, int director, ClientDb currentClient) {
        log.info("getPropDirector(amount=" + amount + ", director=" + director + ", currentClient=" + currentClient + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        DetachedCriteria subqueryLike = sqlForLikedFilms(currentClient);

        DetachedCriteria subqueryDislike = sqlForDislikedFilms(currentClient);

        List<FilmDb> list = session.createCriteria(FilmDb.class, "f")
                .createAlias("f.filmDirectors", "fd")
                .add(Restrictions.eq("fd.id", director))
                .add(Subqueries.propertyNotIn("f.id", subqueryLike))
                .add(Subqueries.propertyNotIn("f.id", subqueryDislike))
                .addOrder(Order.desc("f.rating")).setMaxResults(amount).list();

        session.getTransaction().commit();
        session.close();

        log.info("getPropDirector() returns : list.size()=" + list.size());
        return list;
    }

    public List<FilmDb> getPropStudios(int amount, int studio, ClientDb currentClient) {
        log.info("getPropStudios(amount=" + amount + ", studio=" + studio + ", currentClient=" + currentClient + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        DetachedCriteria subqueryLike = sqlForLikedFilms(currentClient);

        DetachedCriteria subqueryDislike = sqlForDislikedFilms(currentClient);

        List<FilmDb> list = session.createCriteria(FilmDb.class, "f")
                .createAlias("f.filmStudios", "fs")
                .add(Restrictions.eq("fs.id", studio))
                .add(Subqueries.propertyNotIn("f.id", subqueryLike))
                .add(Subqueries.propertyNotIn("f.id", subqueryDislike))
                .addOrder(Order.desc("f.rating")).setMaxResults(amount).list();

        session.getTransaction().commit();
        session.close();

        log.info("getPropStudios() returns : list.size()=" + list.size());
        return list;
    }

    public List<FilmDb> getPropCountry(int amount, int country, ClientDb currentClient) {
        log.info("getPropCountry(amount=" + amount + ", country=" + country + ", currentClient=" + currentClient + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        DetachedCriteria subqueryLike = sqlForLikedFilms(currentClient);

        DetachedCriteria subqueryDislike = sqlForDislikedFilms(currentClient);

        List<FilmDb> list = session.createCriteria(FilmDb.class, "f")
                .createAlias("f.filmCountries", "fc")
                .add(Restrictions.eq("fc.id", country))
                .add(Subqueries.propertyNotIn("f.id", subqueryLike))
                .add(Subqueries.propertyNotIn("f.id", subqueryDislike))
                .addOrder(Order.desc("f.rating")).setMaxResults(amount).list();

        session.getTransaction().commit();
        session.close();

        log.info("getPropCountry() returns : list.size()=" + list.size());
        return list;
    }

    public List<FilmDb> getPropDates(int amount, int year, ClientDb currentClient) {
        log.info("getPropDates(amount=" + amount + ", year=" + year + ", currentClient=" + currentClient + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, Calendar.JANUARY, 1);
        java.util.Date lo = calendar.getTime();

        calendar.set(year, Calendar.DECEMBER, 31);
        java.util.Date hi = calendar.getTime();

        DetachedCriteria subqueryLike = sqlForLikedFilms(currentClient);

        DetachedCriteria subqueryDislike = sqlForDislikedFilms(currentClient);

        List<FilmDb> list = session.createCriteria(FilmDb.class, "f")
                .add(Restrictions.between("f.releaseDate", lo, hi))
                .add(Subqueries.propertyNotIn("f.id", subqueryLike))
                .add(Subqueries.propertyNotIn("f.id", subqueryDislike))
                .addOrder(Order.desc("f.rating"))
                .setMaxResults(amount)
                .list();

        session.getTransaction().commit();
        session.close();

        log.info("getPropDates() returns : list.size()=" + list.size());
        return list;
    }

    // SQL for getting films which are liked and disliked

    private DetachedCriteria sqlForLikedFilms(ClientDb currentClient) {
        log.info("sqlForLikedFilms(currentClient=" + currentClient + ")");

        DetachedCriteria subqueryLike = DetachedCriteria.forClass(FilmLikeDb.class)
                .add(Restrictions.eq("filmLike.clientByIdClient.id", currentClient.getId()))
                .setProjection(Property.forName("filmLike.filmByIdFilm.id"));

        log.info("sqlForLikedFilms() returns : subqueryLike=" + subqueryLike);
        return subqueryLike;
    }

    private DetachedCriteria sqlForDislikedFilms(ClientDb currentClient) {
        log.info("sqlForDislikedFilms(currentClient=" + currentClient + ")");

        DetachedCriteria subqueryDislike = DetachedCriteria.forClass(FilmDislikeDb.class)
                .add(Restrictions.eq("filmDislike.clientByIdClient.id", currentClient.getId()))
                .setProjection(Property.forName("filmDislike.filmByIdFilm.id"));

        log.info("sqlForDislikedFilms() returns : subqueryDislike=" + subqueryDislike);
        return subqueryDislike;
    }


}
