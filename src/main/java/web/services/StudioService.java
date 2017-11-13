package web.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.StudioDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.StudioJSON;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("StudioService")
@Transactional
public class StudioService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public StudioDb getStudioWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        Session session = sessionFactory.getCurrentSession();
        StudioDb studioDb = (StudioDb) session.createQuery("from StudioDb s where s.id=" + id).list().get(0);
        return studioDb;
    }

    public void saveOrUpdateStudio(StudioDb studioDb) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(studioDb);
    }

    public void deleteStudio(String id) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from StudioDb s where s.id=" + id).executeUpdate();
    }

    public List<StudioDb> getAll() throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        List<StudioDb> result = session.createQuery("from StudioDb s order by s.studioName").list();
        return result;
    }

    public StudioDb convertToStudioDb(StudioJSON studioJSON) throws ParsingJsonToDaoException {
        StudioDb studioDb = new StudioDb();
        studioDb.setId(studioJSON.getId());
        studioDb.setStudioName(studioJSON.getStudioName());
        return studioDb;
    }
}
