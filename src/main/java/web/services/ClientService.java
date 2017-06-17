package web.services;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ClientDb;
import org.hibernate.Session;

import java.util.List;

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

    public List<ClientDb> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<ClientDb> result = session.createQuery("from ClientDb").list();
        return result;
    }

    public void delete(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from ClientDb c where c.id=" + id).executeUpdate();
    }
}
