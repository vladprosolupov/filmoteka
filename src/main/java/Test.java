import dao.CountryDb;
import hibernate.HibernateUtil;
import org.hibernate.Session;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
public class Test {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        CountryDb countryDb = new CountryDb();
        countryDb.setName("Afghanistan");
        session.save(countryDb);
        session.getTransaction().commit();
        session.close();
    }
}
