package web.services;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.*;
import web.model.aiModel.CombinedFilm;

import java.sql.Date;
import java.util.*;

@Service("AiService")
@Transactional
public class AiService {
    @Autowired(required = true)
    SessionFactory sessionFactory;

    @Autowired
    FilmService filmService;

    @Autowired
    ClientService clientService;

    @Autowired
    ClientDataService clientDataService;

    private final int categoryPoints = 10;
    private final int ratingPoints = 8;
    private final int countryPoints = 3;
    private final int actorsPoints = 3;
    private final int directorsPoints = 3;
    private final int studiosPoints = 3;
    private final int releaseDatePoints = 2;
    private final int titlePoints = 2;


    public double getComparisonMapInteger(Map<Integer, Integer> map, Set<Integer> set) {
        int fullSize = map.size();
        int counter = 0;
        for (int i : set) {
            if (map.containsKey(i))
                counter += map.get(i);
        }
        return ((double) counter / (double) fullSize);
    }

    public double getComparisonMapString(Map<String, Integer> map, String string) {
        LevenshteinDistance levenshteinDistance = new LevenshteinDistance();
        double counter = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            counter += (1 - (double) levenshteinDistance.apply(entry.getKey(), string) / Math.max(entry.getKey().length(), string.length())) * entry.getValue();
        }
        return counter;
    }

    public double getComparisonMapDate(Map<Date, Integer> map, Date date) {
        double counter = 0;
        for (Map.Entry<Date, Integer> entry : map.entrySet()) {
            counter += (1 / Math.max(1, Math.abs(entry.getKey().getTime() - date.getTime()))) * entry.getValue();
        }
        return counter;
    }


    private CombinedFilm getCombinedFilmForClient(ClientDb currentClient) {
        CombinedFilm combinedFilm = new CombinedFilm();


        Session session = sessionFactory.openSession();

        //Getting start row
        session.beginTransaction();
        long start = Math.max(0, ((long) session.createQuery("select count(fl.filmLike.filmByIdFilm.id) from FilmLikeDb fl where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).list().get(0)) - 42);
        session.getTransaction().commit();


        //Getting categories
        session.beginTransaction();
        Map<Integer, Integer> categories = new HashMap<>();
        List<CategoryDb> categoryDbList = session.createQuery("select f.filmCategories from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int)start).setMaxResults(42).list();
        categoryDbList.forEach(categoryDb -> categories.merge(categoryDb.getId(), 1, Integer::sum));
        combinedFilm.setCategories(categories);
        session.getTransaction().commit();


        //Getting titles
        session.beginTransaction();
        Map<String, Integer> titles = new HashMap<>();
        List<String> searchTitles = session.createQuery("select f.titleSearch from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int)start).setMaxResults(42).list();
        searchTitles.forEach(s -> titles.merge(s, 1, Integer::sum));
        combinedFilm.setTitles(titles);
        session.getTransaction().commit();


        //Getting countries
        session.beginTransaction();
        Map<Integer, Integer> countries = new HashMap<>();
//        start = Math.max(0, ((int) session.createQuery("select count(f.id) from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).list().get(0)) - 42);
        List<CountryDb> countryDbList = session.createQuery("select f.filmCountries from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int)start).setMaxResults(42).list();
        countryDbList.forEach(countryDb -> countries.merge(countryDb.getId(), 1, Integer::sum));
        combinedFilm.setCountries(countries);
        session.getTransaction().commit();


        //Getting actors
        session.beginTransaction();
        Map<Integer, Integer> actors = new HashMap<>();
//        start = Math.max(0, ((int) session.createQuery("select f.filmActorsById.size from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).list().get(0)) - 42);
        List<ActorDb> actorsDbList = session.createQuery("select f.actorByIdActor from FilmActorDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.filmByIdFilm.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int)start).setMaxResults(42).list();
        actorsDbList.forEach(actorDb -> actors.merge(actorDb.getId(), 1, Integer::sum));
        combinedFilm.setActors(actors);
        session.getTransaction().commit();


        //Getting directors
        session.beginTransaction();
        Map<Integer, Integer> directors = new HashMap<>();
//        start = Math.max(0, ((int) session.createQuery("select f.filmCountries.size from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).list().get(0)) - 42);
        List<DirectorDb> directorDbList = session.createQuery("select f.filmDirectors from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int)start).setMaxResults(42).list();
        directorDbList.forEach(directorDb -> directors.merge(directorDb.getId(), 1, Integer::sum));
        combinedFilm.setDirectors(directors);
        session.getTransaction().commit();



        //Getting studios
        session.beginTransaction();
        Map<Integer, Integer> studios = new HashMap<>();
//        start = Math.max(0, ((int) session.createQuery("select f.filmCountries.size from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).list().get(0)) - 42);
        List<StudioDb> studioDbList = session.createQuery("select f.filmStudios from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int)start).setMaxResults(42).list();
        studioDbList.forEach(studioDb -> studios.merge(studioDb.getId(), 1, Integer::sum));
        combinedFilm.setStudios(studios);
        session.getTransaction().commit();


        //Getting release dates
        session.beginTransaction();
        Map<Date, Integer> dates = new HashMap<>();
        List<Date> sqlDates = session.createQuery("select f.releaseDate from FilmDb f inner join FilmLikeDb fl on fl.filmLike.filmByIdFilm.id = f.id where fl.filmLike.clientByIdClient.id = ?").setParameter(0, currentClient.getId()).setFirstResult((int)start).setMaxResults(42).list();
        sqlDates.forEach(date -> dates.merge(date, 1, Integer::sum));
        combinedFilm.setReleaseDates(dates);
        session.getTransaction().commit();
        session.close();
        //returning combined film
        return combinedFilm;
    }

    public double compareCombinedWithNormal(CombinedFilm combinedFilm, FilmDb normalFilm) {
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


        aiPoints += ratingPoints *  (1 - 1/normalFilm.getRating());

        return aiPoints;
    }


    public void generateFilmsForSuggestion(ClientDb currentClient) {
        //generate CombinedFilm for current user
        CombinedFilm combinedFilm = getCombinedFilmForClient(currentClient);

        System.out.println(combinedFilm);
        //create clientDataMap to fill it later
        Map<Integer, Double> clientDataMap = new HashMap<>();
        //Generate clientDataMap



        //Save clientDataMap
        clientDataService.saveClientDataMap(clientDataMap, currentClient);
    }


}
