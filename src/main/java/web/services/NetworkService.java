package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.NetworkDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.NetworkJSON;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("NetworkService")
@Transactional
public class NetworkService {

//    @Autowired(required = true)
    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(NetworkService.class);

    public NetworkService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public NetworkDb getNetworkWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getNetworkWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        NetworkDb networkDb =
                (NetworkDb) session.createQuery("from NetworkDb n where n.id=" + id).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getNetworkWithId() returns : networkDb=" + networkDb);
        return networkDb;
    }

    public void saveOrUpdate(NetworkDb networkDb) throws HibernateException {
        log.info("saveOrUpdate(networkDb=" + networkDb + ")");

        if (networkDb == null) {
            log.error("Error : networkDb is null");

            throw new IllegalArgumentException("NetworkDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(networkDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved or updated network");
    }

    public void delete(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from NetworkDb n where n.id=" + id).executeUpdate();
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted network");
    }

    public List<NetworkDb> getAll() throws HibernateException {
        log.info("getAll()");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<NetworkDb> result = session.createQuery("from NetworkDb").list();
        session.getTransaction().commit();
        session.close();

        log.info("getAll() returns : result.size()=" + result.size());
        return result;
    }

    public NetworkDb convertToNetworkDb(NetworkJSON networkJSON) throws ParsingJsonToDaoException {
        log.info("convertToNetworkDb(networkJSON=" + networkJSON + ")");

        if (networkJSON == null) {
            log.error("Error : networkJSON is null");

            throw new IllegalArgumentException("NetworkJSON should not be null");
        }

        NetworkDb networkDb = new NetworkDb();
        networkDb.setNetworkLogo(networkJSON.getNetworkLogo());
        networkDb.setId(networkJSON.getId());
        networkDb.setNetworkName(networkJSON.getNetworkName());

        log.info("convertToNetworkDb() returns : networkDb=" + networkDb);
        return networkDb;
    }

}
