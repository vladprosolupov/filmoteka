package web.services;

import web.dao.ClientDb;
import hibernate.HibernateUtil;
import org.hibernate.Session;

/**
 * Created by Rostyk on 12.06.2017.
 */
public class ClientService {

    public static void saveOrUpdate(ClientDb clientDb){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(clientDb);
        session.getTransaction().commit();
        session.close();
    }

    public static ClientDb getClientByLogin(String login){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ClientDb client = (ClientDb) session.createQuery("FROM ClientDb c where c.login='" + login+"'").list().get(0);
        session.getTransaction().commit();
        session.close();
        return client;
    }
}
