package main;

import com.group.model.CountryEntity;
import com.hibernate.HibernateSession;
import org.hibernate.Session;

/**
 * Created by 1rost on 5/22/2017.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");

        Session session = HibernateSession.getSessionFactory().openSession();
        session.beginTransaction();
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setName("USA");
        session.save(countryEntity);
        session.delete(session.createQuery("FROM CountryEntity ").list().get(4));
        System.out.println(countryEntity.getId());
        session.getTransaction().commit();
        System.out.println(session.createQuery("FROM CountryEntity ").list());
        session.close();
        System.out.println("German loh");

        HibernateSession.shutdown();

    }
}
