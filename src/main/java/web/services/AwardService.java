package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger log = LogManager.getLogger(AwardService.class);

    public AwardDb getAwardWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getAwardWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        AwardDb awardDb = (AwardDb) session.createQuery("from AwardDb a where a.id=" + id).list().get(0);
        session.close();

        log.info("getAwardWithId() returns : awardDb=" + awardDb);
        return awardDb;
    }

    public List<AwardDb> getAwardsWithFilmId(int id) throws HibernateException {
        log.info("getAwardsWithFilmId(id=" + id + ")");

        if (id < 0) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be smaller than 0");
        }

        Session session = sessionFactory.openSession();
        List<AwardDb> awardDbList = session.createQuery("from AwardDb a where a.filmByIdFilm=" + id).list();
        session.close();

        log.info("getAwardsWithFilmId() returns : awardDbList.size()=" + awardDbList.size());
        return awardDbList;
    }

    public void saveOrUpdateAward(AwardDb awardDb) throws HibernateException {
        log.info("saveOrUpdateAward(awardDb=" + awardDb + ")");

        if (awardDb == null) {
            log.error("Error : awardDb is null");

            throw new IllegalArgumentException("AwardDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.saveOrUpdate(awardDb);
        session.close();

        log.info("succ saved or updated award");
    }

    public void deleteAward(String id) throws HibernateException {
        log.info("deleteAward(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.createQuery("delete from AwardDb a where a.id=" + id).executeUpdate();
        session.close();

        log.info("succ. deleted award");
    }

    public List<AwardDb> getAll() throws HibernateException {
        log.info("getAll()");

        Session session = sessionFactory.openSession();
        List<AwardDb> result = session.createQuery("from AwardDb ").list();
        session.close();

        log.info("getAll() returns : result.size()=" + result.size());
        return result;
    }

    public Set<AwardDb> createSetOfAwards(Map<String, Integer> awards) throws ParsingJsonToDaoException {
        log.info("createSetOfAwards(awards=" + awards + ")");

        if (awards == null) {
            log.error("Error : awards is null");

            throw new IllegalArgumentException("Awards should not be null");
        }

        Set<AwardDb> awardDbSet = new HashSet<>();
        for (Map.Entry<String, Integer> m : awards.entrySet()) {
            AwardDb awardDb = new AwardDb();
            awardDb.setAwardName(m.getKey());
            awardDb.setAwardYear(m.getValue());
            awardDbSet.add(awardDb);
        }

        log.info("createSetOfAwards() returns : awardDbSet.size()=" + awardDbSet.size());
        return awardDbSet;
    }

    public void checkForAwards(int filmId, Set<AwardDb> awardDbSet) throws HibernateException {
        log.info("checkForAwards(filmId=" + filmId + ", awardDbSet=" + awardDbSet + ")");

        if (filmId < 0) {
            log.error("Error : filmId is incorrect");

            throw new IllegalArgumentException("FilmId should not be null");
        }
        if (awardDbSet == null) {
            log.error("Error : awardDbSet is null");

            throw new IllegalArgumentException("AwardDbSer should not be null");
        }

        Session session = sessionFactory.openSession();
        session.createQuery("delete from AwardDb a where a.filmByIdFilm=" + filmId).executeUpdate();
        for (AwardDb a : awardDbSet) {
            a.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(filmId)));
            session.saveOrUpdate(a);
        }
        session.close();

        log.info("succ. get results");
    }
}

