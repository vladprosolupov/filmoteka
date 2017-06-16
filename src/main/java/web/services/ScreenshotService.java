package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ScreenshotDb;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("ScreenshotService")
@Transactional
public class ScreenshotService {

    @Autowired
    private SessionFactory sessionFactory;

    public ScreenshotDb getScreenshotWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        ScreenshotDb screenshotDb =
                (ScreenshotDb) session.createQuery("from ScreenshotDb s where s.id=" + id).list().get(0);
        return screenshotDb;
    }

    public void saveOrUpdate(ScreenshotDb screenshotDb){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(screenshotDb);
    }

    public void delete(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from ScreenshotDb s where s.id=" + id).executeUpdate();
    }

    public List<ScreenshotDb> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<ScreenshotDb> result = session.createQuery("from ScreenshotDb ").list();
        return result;
    }
}
