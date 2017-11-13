package web.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AwardDb;
import web.exceptions.ParsingJsonToDaoException;

import java.util.*;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("AwardService")
@Transactional
public class AwardService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private FilmService filmService;

    public AwardDb getAwardWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        Session session = sessionFactory.getCurrentSession();
        AwardDb awardDb = (AwardDb) session.createQuery("from AwardDb a where a.id=" + id).list().get(0);
        return awardDb;
    }

    public List<AwardDb> getAwardsWithFilmId(int id) throws HibernateException {
        List<AwardDb> awardDbList = new ArrayList<>();
        Session session = sessionFactory.getCurrentSession();
        awardDbList = session.createQuery("from AwardDb a where a.filmByIdFilm=" + id).list();
        return awardDbList;
    }

    public void saveOrUpdateAward(AwardDb awardDb) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(awardDb);
    }

    public void deleteAward(String id) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from AwardDb a where a.id=" + id).executeUpdate();
    }

    public List<AwardDb> getAll() throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        List<AwardDb> result = session.createQuery("from AwardDb ").list();
        return result;
    }

    public Set<AwardDb> createSetOfAwards(Map<String, Integer> awards) throws ParsingJsonToDaoException {
        Set<AwardDb> awardDbSet = new HashSet<>();
        for (Map.Entry<String, Integer> m : awards.entrySet()) {
            AwardDb awardDb = new AwardDb();
            awardDb.setAwardName(m.getKey());
            awardDb.setAwardYear(m.getValue());
            awardDbSet.add(awardDb);
        }
        return awardDbSet;
    }

    public void checkForAwards(int filmId, Set<AwardDb> awardDbSet) throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from AwardDb a where a.filmByIdFilm=" + filmId).executeUpdate();
        for (AwardDb a : awardDbSet) {
            a.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(filmId)));
            session.saveOrUpdate(a);
        }
    }
}

