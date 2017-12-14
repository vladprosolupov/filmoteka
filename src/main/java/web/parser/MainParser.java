package web.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import web.dao.CategoryDb;
import web.dao.FilmDb;
import web.dao.StudioDb;
import web.services.FilmService;
import web.services.SearchService;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainParser {

    @Autowired
    private FilmService filmService;

    @Autowired
    private SearchService searchService;

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

    public void kekMain() throws IOException, InterruptedException {
        List<JSONObject> jsonObjectList = getFilmsFromSearch("a");

        for(JSONObject json : jsonObjectList) {

            JSONArray filmsArray = json.getJSONArray("results");


            List<JSONObject> listOfFilms = new ArrayList<>();
            for (int i = 0; i < filmsArray.length(); i++) {
                JSONObject film = getFilmFromArray(filmsArray.getJSONObject(0).get("id"));
                listOfFilms.add(film);
            }

            for(JSONObject jsonObject : listOfFilms) {
                FilmDb filmDb = new FilmDb();
                filmDb.setId(Integer.parseInt(jsonObject.get("id").toString()));
                filmDb.setBudget(jsonObject.get("budget").toString());
                filmDb.setTitle(jsonObject.get("title").toString());
                filmDb.setTitleSearch(searchService.titleToTitleSearch(jsonObject.get("title").toString()));
                filmDb.setSlogan(jsonObject.get("tagline").toString());
                filmDb.setLenght(Integer.parseInt(jsonObject.get("runtime").toString()));
                filmDb.setRating(Double.parseDouble(jsonObject.get("vote_average").toString()));
                filmDb.setCover("https://image.tmdb.org/t/p/w500" + jsonObject.get("poster_path").toString());

                JSONArray categoryArray = jsonObject.getJSONArray("genres");

                Set<CategoryDb> categoryDbSet = new HashSet<>();
                for(int j = 0; j < categoryArray.length(); j++) {
                    JSONObject category = categoryArray.getJSONObject(j);
                    CategoryDb categoryDb = new CategoryDb();
                    categoryDb.setId(Integer.parseInt(category.get("id").toString()));
                    categoryDb.setName(category.get("name").toString());
                    categoryDbSet.add(categoryDb);
                }
                filmDb.setFilmCategories(categoryDbSet);

                JSONArray studioArray = jsonObject.getJSONArray("production_companies");

                Set<StudioDb> studioDbSet = new HashSet<>();
                for(int j = 0; j < studioArray.length(); j++) {
                    JSONObject studio = studioArray.getJSONObject(j);
                    StudioDb studioDb = new StudioDb();
                    studioDb.setId(Integer.parseInt(studio.get("id").toString()));
                    studioDb.setStudioName(studio.get("name").toString());
                    studioDbSet.add(studioDb);
                }
                filmDb.setFilmStudios(studioDbSet);

            }


        }
    }

    public static void main(String[] args) throws IOException, JSONException, InterruptedException {

    }

    private List<JSONObject> getFilmsFromSearch(String searchParameter) throws IOException, InterruptedException {
        JSONObject json = readJsonFromUrl("https://api.themoviedb.org/3/search/movie" +
        "?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US&query="
                + searchParameter
                + "&page=1&include_adult=false&region=USA&year=2017");

        int totalPages = Integer.parseInt(json.get("total_pages").toString());

        List<JSONObject> jsonObjectList = new ArrayList<>();

        for (int i = 1; i < totalPages; i++) {

            Thread.sleep(500);

            JSONObject json1 = readJsonFromUrl("https://api.themoviedb.org/3/search/movie" +
                    "?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US&query="
                    + searchParameter
                    + "&page=" + i + "&include_adult=false&region=USA&year=2017");

            jsonObjectList.add(json1);
        }

        return jsonObjectList;
    }

    private JSONObject getFilmFromArray(Object id) throws IOException, InterruptedException {
        Thread.sleep(500);

        JSONObject film = readJsonFromUrl("https://api.themoviedb.org/3/movie/" +
                id.toString() + "?api_key=00ea1df0a2ba585296e9c9ac50f2289f&language=en-US");
        return film;
    }

}
