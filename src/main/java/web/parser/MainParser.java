package web.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.awt.windows.ThemeReader;
import web.dao.*;
import web.exceptions.ParsingJsonToDaoException;
import web.services.*;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("MainParser")
public class MainParser {

    @Autowired
    private FilmService filmService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ActorService actorService;

    @Autowired
    private FilmActorService filmActorService;

    @Autowired
    private StudioService studioService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private ScreenshotService screenshotService;

    @Autowired
    private TrailerService trailerService;

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public void kekMain() throws IOException, InterruptedException, ParseException, ParsingJsonToDaoException {
        List<JSONObject> jsonObjectList = getFilmsFromSearch("av");

        System.out.println(jsonObjectList);

        for (JSONObject json : jsonObjectList) {

            JSONArray filmsArray = json.getJSONArray("results");


            List<JSONObject> listOfFilms = new ArrayList<>();
            for (int i = 0; i < filmsArray.length(); i++) {
                JSONObject film = getFilmFromArray(filmsArray.getJSONObject(i).get("id"));
                listOfFilms.add(film);
            }

            for (JSONObject jsonObject : listOfFilms) {
                if (jsonObject.get("poster_path").toString() != null && !jsonObject.get("poster_path").toString().isEmpty() && !jsonObject.get("poster_path").toString().equals("undefined") && !jsonObject.get("poster_path").toString().equals("null")) {
                    FilmDb filmDb = new FilmDb();
                    filmDb.setId(Integer.parseInt(jsonObject.get("id").toString()));
                    filmDb.setBudget("$" + jsonObject.get("budget").toString());
                    filmDb.setTitle(jsonObject.get("title").toString());
                    filmDb.setTitleSearch(searchService.titleToTitleSearch(jsonObject.get("title").toString()));
                    filmDb.setDescription(jsonObject.get("overview").toString());
                    filmDb.setSlogan(jsonObject.get("tagline").toString());
                    try {
                        filmDb.setLenght(Integer.parseInt(jsonObject.get("runtime").toString()));
                    } catch (NumberFormatException e) {
                        filmDb.setLenght(0);
                    }
                    try {
                        filmDb.setRating(Double.parseDouble(jsonObject.get("vote_average").toString()));
                    } catch (NumberFormatException e) {
                        continue;
                    }

                    filmDb.setCover("https://image.tmdb.org/t/p/w780" + jsonObject.get("poster_path").toString());
                    try {
                        filmDb.setReleaseDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.get("release_date").toString()).getTime()));
                    } catch (Exception e) {
                        continue;
                    }

                    try {
                        filmDb.setLanguageByIdLanguage(languageService.getLanguageWithISO(jsonObject.get("original_language").toString()));
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }

                    JSONArray categoryArray = jsonObject.getJSONArray("genres");

                    Set<CategoryDb> categoryDbSet = new HashSet<>();
                    for (int j = 0; j < categoryArray.length(); j++) {
                        JSONObject category = categoryArray.getJSONObject(j);
                        CategoryDb categoryDb = new CategoryDb();
                        categoryDb.setId(Integer.parseInt(category.get("id").toString()));
                        categoryDb.setName(category.get("name").toString());
                        categoryService.saveOrUpdate(categoryDb);
                        categoryDbSet.add(categoryDb);
                    }
                    filmDb.setFilmCategories(categoryDbSet);

                    JSONArray studioArray = jsonObject.getJSONArray("production_companies");

                    Set<StudioDb> studioDbSet = new HashSet<>();
                    for (int j = 0; j < studioArray.length(); j++) {
                        JSONObject studio = studioArray.getJSONObject(j);
                        StudioDb studioDb = new StudioDb();
                        studioDb.setId(Integer.parseInt(studio.get("id").toString()));
                        studioDb.setStudioName(studio.get("name").toString());
                        studioService.saveOrUpdateStudio(studioDb);
                        studioDbSet.add(studioDb);
                    }
                    filmDb.setFilmStudios(studioDbSet);


                    try {
                        JSONArray countryArray = jsonObject.getJSONArray("production_countries");

                        Set<CountryDb> countryDbSet = new HashSet<>();
                        for (int j = 0; j < countryArray.length(); j++) {
                            JSONObject country = countryArray.getJSONObject(j);
                            CountryDb countryDb = countryService.getCountryWithISO(country.get("iso_3166_1").toString());
                            countryDbSet.add(countryDb);
                        }
                        filmDb.setFilmCountries(countryDbSet);
                    } catch (IndexOutOfBoundsException e) {
                        continue;
                    }

                    JSONObject credits = getCreditsForFilm(jsonObject.get("id"));

                    Set<DirectorDb> directorDbSet = new HashSet<>();
                    JSONArray crewArray = credits.getJSONArray("crew");

                    for (int j = 0; j < crewArray.length(); j++) {
                        if (crewArray.getJSONObject(j).get("job").toString().equals("Director")) {
                            int lastSpace = crewArray.getJSONObject(j).get("name").toString().lastIndexOf(" ");
                            DirectorDb directorDb = new DirectorDb();
                            if (lastSpace != -1) {
                                String[] credentials = {crewArray.getJSONObject(j).get("name").toString().substring(0, lastSpace), crewArray.getJSONObject(j).get("name").toString().substring(lastSpace + 1)};
                                directorDb.setFirstName(credentials[0]);
                                directorDb.setLastName(credentials[1]);
                            } else {
                                directorDb.setFirstName(crewArray.getJSONObject(j).get("name").toString());
                                directorDb.setLastName(" ");
                            }
                            directorDb.setId(Integer.parseInt(crewArray.getJSONObject(j).get("id").toString()));
                            directorService.saveOrUpdate(directorDb);
                            directorDbSet.add(directorDb);
                        }
                    }
                    filmDb.setFilmDirectors(directorDbSet);


                    filmService.saveOrUpdate(filmDb);

                    Set<FilmActorDb> filmActorDbSet = new HashSet<>();
                    JSONArray actorArray = credits.getJSONArray("cast");

                    for (int j = 0; j < actorArray.length(); j++) {
                        FilmActorDb filmActorDb = new FilmActorDb();
                        ActorDb actorDb = new ActorDb();
                        int lastSpace = actorArray.getJSONObject(j).get("name").toString().lastIndexOf(" ");
                        if (lastSpace != -1) {
                            String[] credentials = {actorArray.getJSONObject(j).get("name").toString().substring(0, lastSpace), actorArray.getJSONObject(j).get("name").toString().substring(lastSpace + 1)};
                            actorDb.setFirstName(credentials[0]);
                            actorDb.setLastName(credentials[1]);
                        } else {
                            actorDb.setFirstName(actorArray.getJSONObject(j).get("name").toString());
                            actorDb.setLastName("");
                        }
                        actorDb.setId(Integer.parseInt(actorArray.getJSONObject(j).get("id").toString()));
                        actorService.saveOrUpdate(actorDb);

                        filmActorDb.setFilmByIdFilm(filmDb);
                        filmActorDb.setActorByIdActor(actorDb);
                        filmActorDb.setRole(actorArray.getJSONObject(j).get("character").toString());
                        filmActorService.saveOrUpdate(filmActorDb);
                        filmActorDbSet.add(filmActorDb);
                    }
                    filmDb.setFilmActorsById(filmActorDbSet);


                    JSONObject screenshotsJSON = getImagesForFilm(jsonObject.get("id"));

                    Set<ScreenshotDb> screenshotDbSet = new HashSet<>();
                    JSONArray imageArray = screenshotsJSON.getJSONArray("backdrops");
                    List<String> images = new ArrayList<>();
                    for (int j = 0; j < imageArray.length(); j++) {
                        images.add("https://image.tmdb.org/t/p/w780" + imageArray.getJSONObject(j).get("file_path").toString());
                    }
                    Set<ScreenshotDb> setOfScreenShots = screenshotService.createScreenshotSet(images);
                    setOfScreenShots.forEach(screenshotDb -> {
                        screenshotDb.setFilmByIdFilm(filmDb);
                        screenshotService.saveOrUpdate(screenshotDb);
                    });
                    filmDb.setScreenshotsById(setOfScreenShots);

                    JSONObject videosJSON = getVideosForFilm(jsonObject.get("id"));
                    JSONArray videoArray = videosJSON.getJSONArray("results");
                    Set<TrailerDb> trailerDbSet = new HashSet<>();
                    List<String> trailers = new ArrayList<>();
                    for (int j = 0; j < videoArray.length(); j++) {
                        if(videoArray.getJSONObject(j).get("type").toString().equals("Trailer") && videoArray.getJSONObject(j).get("site").toString().equals("YouTube"))
                            trailers.add("https://www.youtube.com/embed/" + videoArray.getJSONObject(j).get("key").toString());
                    }

                    Set<TrailerDb> setOfTrailers = trailerService.createTrailerDbSet(trailers);
                    setOfTrailers.forEach(trailerDb -> {
                        trailerDb.setFilmByIdFilm(filmDb);
                        trailerService.saveOrUpdate(trailerDb);
                    });
                    filmDb.setTrailersById(setOfTrailers);

                    System.out.println("---------------SET OF IMAGES--------------" + setOfScreenShots);
                    System.out.println("---------------SET OF TRAILERS--------------" + setOfTrailers);

                    System.out.println("---------------FILM DB-------------- " + filmDb);
                    filmService.saveOrUpdate(filmDb);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, JSONException, InterruptedException {

    }

    private List<JSONObject> getFilmsFromSearch(String searchParameter) throws IOException, InterruptedException {
        System.out.println("get films from search");

        JSONObject json = readJsonFromUrl("https://api.themoviedb.org/3/search/movie" +
                "?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US&query="
                + searchParameter
                + "&page=1&include_adult=false&region=USA&year=2017");

        int totalPages = Integer.parseInt(json.get("total_pages").toString());

        List<JSONObject> jsonObjectList = new ArrayList<>();

        jsonObjectList.add(json);

        for (int i = 2; i <= totalPages; i++) {
            Thread.sleep(500);

            JSONObject json1 = readJsonFromUrl("https://api.themoviedb.org/3/search/movie" +
                    "?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US&query="
                    + searchParameter
                    + "&page=" + i + "&include_adult=false&region=USA&year=2017");

            jsonObjectList.add(json1);
        }

        return jsonObjectList;
    }

    private JSONObject getCreditsForFilm(Object id) throws InterruptedException, IOException {
        Thread.sleep(2000);
        JSONObject credits;
        try {
            credits = readJsonFromUrl("https://api.themoviedb.org/3/movie/" +
                    id.toString() +
                    "/credits?api_key=00ea1df0a2ba585296e9c9ac50f2289f");
        }catch (Exception e){
            Thread.sleep(10000);
            credits = readJsonFromUrl("https://api.themoviedb.org/3/movie/" +
                    id.toString() +
                    "/credits?api_key=00ea1df0a2ba585296e9c9ac50f2289f");
        }
        return credits;
    }

    private JSONObject getPersonWithId(Object id) throws IOException, InterruptedException {
        Thread.sleep(500);

        JSONObject person = readJsonFromUrl("https://api.themoviedb.org/3/person/" +
                id.toString() + "?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US");
        return person;
    }

    private JSONObject getFilmFromArray(Object id) throws IOException, InterruptedException {
        Thread.sleep(1000);

        JSONObject film = readJsonFromUrl("https://api.themoviedb.org/3/movie/" +
                id.toString() + "?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US");
        return film;
    }

    private JSONObject getImagesForFilm(Object id) throws IOException, InterruptedException {
        Thread.sleep(2000);
        JSONObject images;
        try {
            images = readJsonFromUrl("https://api.themoviedb.org/3/movie/" +
                    id.toString() +
                    "/images?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US&include_image_language=en");
        }catch (Exception e){
            Thread.sleep(10000);
            images = readJsonFromUrl("https://api.themoviedb.org/3/movie/" +
                    id.toString() +
                    "/images?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US&include_image_language=en");
        }
        return images;
    }

    private JSONObject getVideosForFilm(Object id) throws IOException, InterruptedException {
        Thread.sleep(2000);


        JSONObject videos;
        try {
            videos = readJsonFromUrl("https://api.themoviedb.org/3/movie/" +
                    id.toString() +
                    "/videos?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US");
        }catch (Exception e){
            Thread.sleep(10000);
            videos = readJsonFromUrl("https://api.themoviedb.org/3/movie/" +
                    id.toString() +
                    "/videos?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US");
        }
        return videos;
    }

}
