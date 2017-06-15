package web.services;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ClientDb;
import org.hibernate.Session;

/**
 * Created by Rostyk on 12.06.2017.
 */
@Service("ClientService")
@Transactional
public class ClientService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public void saveOrUpdate(ClientDb clientDb){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(clientDb);
    }

    public ClientDb getClientByLogin(String login){
        Session session = sessionFactory.getCurrentSession();
        ClientDb client = (ClientDb) session.createQuery("FROM ClientDb c where c.login='" + login+"'").list().get(0);
        return client;
    }
}
