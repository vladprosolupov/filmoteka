package main;

import com.hibernate.HibernateSession;
import org.hibernate.Session;

/**
 * Created by 1rost on 5/22/2017.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hibernate tutorial");

        Session session = HibernateSession.getSessionFactory().openSession();
        System.out.println(session.createQuery("FROM CountryEntity ").list());
        session.close();
        System.out.println("German loh");

    }
}
