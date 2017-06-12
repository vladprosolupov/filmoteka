import dao.ClientDb;
import dao.CountryDb;
import hibernate.HibernateUtil;
import org.hibernate.Session;
import services.ClientService;
import services.FilmService;
import services.PasswordGenerator;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
public class Test {
    public static void main(String[] args) {

//        ClientDb clientDb = new ClientDb();
//        clientDb.setFirstName("admin");
//        clientDb.setLastName("admin");
//        clientDb.setLogin("admin");
//        clientDb.setPassword(PasswordGenerator.hashPassword("VladRostykGerman!"));
//        clientDb.setAddressByIdAddress(null);
//        clientDb.setEmail("filmoteka.project@gmail.com");
//        clientDb.setCreationDate(new Timestamp(new Date().getTime()));
//        clientDb.setPhoneNumber("111");
//        clientDb.setRole("admin");

        ClientDb clientDb = ClientService.getClientByLogin("admin");
        clientDb.setEnabled(1);
        ClientService.saveOrUpdate(clientDb);
    }
}
