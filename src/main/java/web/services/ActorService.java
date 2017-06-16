package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ActorDb;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("ActorService")
@Transactional
public class ActorService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public ActorDb getActorWithId(int id){
        Session session = sessionFactory.getCurrentSession();
        ActorDb actorDb = (ActorDb) session.createQuery("from ActorDb a where a.id=" + id).list().get(0);
        return actorDb;
    }

    public void saveOrUpdate(ActorDb actorDb){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(actorDb);
    }

    public void delete(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from ActorDb a where a.id=" + id).executeUpdate();
    }

    public List<ActorDb> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<ActorDb> result = session.createQuery("from ActorDb ").list();
        return result;
    }
}
