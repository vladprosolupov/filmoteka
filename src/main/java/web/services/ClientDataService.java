package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ClientDataDb;
import web.dao.ClientDb;
import web.dao.FilmDb;

import java.util.Map;

@Service("ClientDataService")
@Transactional

public class ClientDataService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private FilmService filmService;

    private static final Logger log = LogManager.getLogger(ClientDataService.class);

    public void saveOrUpdate(ClientDataDb clientDataDb) throws HibernateException {

        log.info("saveOrUpdate(clientDataDb=" + clientDataDb + ")");

        if (clientDataDb == null) {
            log.error("Error : clientDataDb is null");

            throw new IllegalArgumentException("clientDataDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(clientDataDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved or updated clientData");

    }

    public void saveClientDataMap(Map<Integer, Double> clientDataMap, ClientDb clientDb) {
        log.info("saveClientDataMap(clientDataMap.size()=" + clientDataMap.size() + ", clientDb=" + clientDb + ")");
        if (clientDataMap.isEmpty()) {
            log.error("Error : clientDataMap is empty");

            throw new IllegalArgumentException("clientDataMap is empty");
        } else if (clientDb == null) {
            log.error("Error : clientDb is null");

            throw new IllegalArgumentException("clientDb should not be null");
        }

        Session session = sessionFactory.openSession();
        //Removing all existed suggested films
        session.beginTransaction();
        session.createQuery("delete from ClientDataDb where clientByIdClient.id=?").setParameter(0,clientDb.getId()).executeUpdate();
        session.getTransaction().commit();


        //Adding new suggested films
        session.beginTransaction();
        for (Map.Entry<Integer, Double> entry : clientDataMap.entrySet())
        {
            ClientDataDb clientDataDb = new ClientDataDb();
            clientDataDb.setClientByIdClient(clientDb);
            clientDataDb.setFilmByIdFilm(filmService.getFilmWithId(String.valueOf(entry.getKey())));
            clientDataDb.setAiPoints(entry.getValue());
            session.saveOrUpdate(clientDataDb);
        }
        session.getTransaction().commit();

        //Closing session
        session.close();
    }

    public void removeFilm(String filmId, String clientId) {
        log.info("removeFilm(filmId=" + filmId + ", clientId=" + clientId + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from ClientDataDb cl where cl.filmByIdFilm.id = ? and cl.clientByIdClient.id = ?")
                .setParameter(0, filmId).setParameter(1, clientId).executeUpdate();
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted film");
    }

}
