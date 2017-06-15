package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.LanguageDb;

import java.util.List;

/**
 * Created by Rostyk on 15.06.2017.
 */
@Service("LanguageService")
@Transactional
public class LanguageService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public List<LanguageDb> getAllLanguages(){
        Session session = sessionFactory.getCurrentSession();
        List<LanguageDb> result = session.createQuery("FROM LanguageDb").list();
        return result;
    }
}
