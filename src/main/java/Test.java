import dao.CountryDb;
import hibernate.HibernateUtil;
import org.hibernate.Session;
import services.FilmService;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
public class Test {
    public static void main(String[] args) {
        FilmService filmService = new FilmService();
        System.out.println(filmService.getAllFilms());
    }
}
