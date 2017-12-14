package web.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.dao.*;
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

    public void kekMain() throws IOException, InterruptedException, ParseException {
        List<JSONObject> jsonObjectList = getFilmsFromSearch("t");

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
                        filmDb.setRating(0.0);
                    }

                    filmDb.setCover("https://image.tmdb.org/t/p/w780" + jsonObject.get("poster_path").toString());
                    filmDb.setReleaseDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.get("release_date").toString()).getTime()));
                    filmDb.setLanguageByIdLanguage(languageService.getLanguageWithISO(jsonObject.get("original_language").toString()));

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

                    JSONArray countryArray = jsonObject.getJSONArray("production_countries");

                    Set<CountryDb> countryDbSet = new HashSet<>();
                    for (int j = 0; j < countryArray.length(); j++) {
                        JSONObject country = countryArray.getJSONObject(j);
                        CountryDb countryDb = countryService.getCountryWithISO(country.get("iso_3166_1").toString());
                        countryDbSet.add(countryDb);
                    }
                    filmDb.setFilmCountries(countryDbSet);


                    JSONObject credits = getCreditsForFilm(jsonObject.get("id"));

                    Set<DirectorDb> directorDbSet = new HashSet<>();
                    JSONArray crewArray = credits.getJSONArray("crew");

                    for (int j = 0; j < crewArray.length(); j++) {
                        if (crewArray.getJSONObject(j).get("job").toString().equals("Director")) {
                            int lastSpace = crewArray.getJSONObject(j).get("name").toString().lastIndexOf(" ");
                            String[] credentials = {crewArray.getJSONObject(j).get("name").toString().substring(0, lastSpace), crewArray.getJSONObject(j).get("name").toString().substring(lastSpace + 1)};
                            DirectorDb directorDb = new DirectorDb();
                            directorDb.setId(Integer.parseInt(crewArray.getJSONObject(j).get("id").toString()));
                            directorDb.setFirstName(credentials[0]);
                            directorDb.setLastName(credentials[1]);
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
                        int lastSpace = crewArray.getJSONObject(j).get("name").toString().lastIndexOf(" ");
                        String[] credentials = {crewArray.getJSONObject(j).get("name").toString().substring(0, lastSpace), crewArray.getJSONObject(j).get("name").toString().substring(lastSpace + 1)};
                        actorDb.setId(Integer.parseInt(actorArray.getJSONObject(j).get("id").toString()));
                        actorDb.setFirstName(credentials[0]);
                        actorDb.setLastName(credentials[1]);
                        actorService.saveOrUpdate(actorDb);

                        filmActorDb.setFilmByIdFilm(filmDb);
                        filmActorDb.setActorByIdActor(actorDb);
                        filmActorDb.setRole(actorArray.getJSONObject(j).get("character").toString());
                        filmActorService.saveOrUpdate(filmActorDb);
                        filmActorDbSet.add(filmActorDb);
                    }
                    filmDb.setFilmActorsById(filmActorDbSet);

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
        Thread.sleep(500);

        JSONObject credits = readJsonFromUrl("https://api.themoviedb.org/3/movie/" +
                id.toString() +
                "/credits?api_key=00ea1df0a2ba585296e9c9ac50f2289f");
        return credits;
    }

    private JSONObject getPersonWithId(Object id) throws IOException, InterruptedException {
        Thread.sleep(500);

        JSONObject person = readJsonFromUrl("https://api.themoviedb.org/3/person/" +
                id.toString() + "?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US");
        return person;
    }

    private JSONObject getFilmFromArray(Object id) throws IOException, InterruptedException {
        Thread.sleep(500);

        JSONObject film = readJsonFromUrl("https://api.themoviedb.org/3/movie/" +
                id.toString() + "?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US");
        return film;
    }

}
