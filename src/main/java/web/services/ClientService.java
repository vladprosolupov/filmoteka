package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AvatarDb;
import web.dao.ClientDb;
import org.hibernate.Session;
import web.enums.ClientRole;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ClientJSON;
import web.model.FilmJSONIndex;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by Rostyk on 12.06.2017.
 */
@Service("ClientService")
@Transactional
public class ClientService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private AvatarService avatarService;

    private static final Logger log = LogManager.getLogger(ClientService.class);

    public ClientDb saveOrUpdate(ClientDb clientDb) throws HibernateException {
        log.info("saveOrUpdate(clientDb=" + clientDb + ")");

        if (clientDb == null) {
            log.error("Error : clientDb is null");

            throw new IllegalArgumentException("ClientDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(clientDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved or updated client, clientDb=" + clientDb);
        return clientDb;
    }

    public ClientDb getClientByLogin(String login) throws HibernateException, IndexOutOfBoundsException {
        log.info("getClientByLogin(login=" + login + ")");

        if (login == null || login.isEmpty()) {
            log.error("Error : login is incorrect");

            throw new IllegalArgumentException("Login should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ClientDb client = (ClientDb) session.createQuery("FROM ClientDb c where c.login=?").setParameter(0, login).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getClientByLogin() returns : client=" + client);
        return client;
    }

    public int getClientIdByLogin(String login) throws HibernateException {
        log.info("getClientIdByLogin(login=" + login + ")");

        if (login == null || login.isEmpty()) {
            log.error("Error : login is incorrect");

            throw new IllegalArgumentException("Login should not be null");
        }
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        int clientId = (int) session.createQuery("select c.id FROM ClientDb c where c.login=?").setParameter(0, login).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getClientIdByLogin() returns : client=" + clientId);
        return clientId;

    }

    public ClientDb getClientByEmail(String email) throws HibernateException, IndexOutOfBoundsException {
        log.info("getClientByEmail(email=" + email + ")");

        if (email == null || email.isEmpty()) {
            log.error("Email is null or empty");

            throw new IllegalArgumentException("Email should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ClientDb clientDb = (ClientDb) session.createQuery("from ClientDb c where c.email='" + email + "'").list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getClientByEmail() returns : clientDb=" + clientDb);
        return clientDb;
    }

    public List<ClientDb> getAll() throws HibernateException {
        log.info("getAll()");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ClientDb> result = session.createQuery("from ClientDb").list();
        session.getTransaction().commit();
        session.close();

        log.info("getAll() returns : result.size()=" + result.size());
        return result;
    }

    public void delete(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from ClientDb c where c.id=" + id).executeUpdate();
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted client");
    }

    public ClientDb getClientById(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getClientById(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        ClientDb clientDb = (ClientDb) session.createQuery("from ClientDb c where c.id=" + id).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getClientById() returns : clientDb=" + clientDb);
        return clientDb;
    }

    public boolean loginCheck(String login) throws HibernateException {
        log.info("loginCheck(login=" + login + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long count = (long) session.createQuery("select count (c.id) from ClientDb c where c.login = '" + login + "'").list().get(0);
        session.getTransaction().commit();
        boolean result = (count == (long) 0);
        session.close();

        log.info("loginCheck() returns: " + result);
        return result;
    }

    public void blockClient(String login) throws HibernateException, IndexOutOfBoundsException {
        log.info("blockClient(login=" + login + ")");

        if (login == null || login.isEmpty()) {
            log.error("Login is null");

            throw new IllegalArgumentException("Login should not be null or empty");
        }
        ClientDb clientDb = getClientByLogin(login);
        clientDb.setEnabled(0);
        saveOrUpdate(clientDb);

        log.info("succ. blocked client");
    }

    public void unblockClient(String login) throws HibernateException, IndexOutOfBoundsException {
        log.info("blockClient(login=" + login + ")");

        if (login == null || login.isEmpty()) {
            log.error("Login is null");

            throw new IllegalArgumentException("Login should not be null or empty");
        }
        ClientDb clientDb = getClientByLogin(login);
        clientDb.setEnabled(1);
        saveOrUpdate(clientDb);

        log.info("succ. blocked client");
    }

    public ClientDb convertToClientDb(ClientJSON clientJSON) throws ParsingJsonToDaoException, IllegalArgumentException {
        log.info("convertToClientDb(clientJSON=" + clientJSON + ")");

        if (clientJSON == null) {
            log.error("Error : clientJSON is null");

            throw new IllegalArgumentException("ClientJSON should not be null");
        }

        ClientDb clientDb = new ClientDb();

        if (clientJSON.getId() == 0) {
            log.info("creating new client");

            clientDb.setFirstName(clientJSON.getFirstName());
            clientDb.setLastName(clientJSON.getLastName());
            clientDb.setEmail(clientJSON.getEmail());
            clientDb.setLogin(clientJSON.getLogin());
            clientDb.setPassword(PasswordGenerator.hashPassword(clientJSON.getPassword()));
            clientDb.setPhoneNumber(clientJSON.getPhoneNumber());
            clientDb.setRole(ClientRole.user.name());
            clientDb.setCreationDate(new Timestamp(System.currentTimeMillis()));
            clientDb.setAvatarByAvatar(avatarService.getAvatarById(clientJSON.getAvatar()));

        } else {
            log.info("editing existing client");
            clientDb = getClientById(String.valueOf(clientJSON.getId()));

            clientDb.setFirstName(clientJSON.getFirstName());
            clientDb.setLastName(clientJSON.getLastName());
            clientDb.setPhoneNumber(clientJSON.getPhoneNumber());

            clientDb.setAvatarByAvatar(avatarService.getAvatarByPath(clientJSON.getAvatar()));
        }

        log.info("convertToClientDb() returns : clientDb=" + clientDb);
        return clientDb;
    }

    public List<ClientJSON> getAllForAdmin() {
        log.info("getAllForAdmin()");
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ClientJSON> allUsers = session.createQuery("select c.id, c.enabled, c.login, c.email, c.firstName, c.lastName from ClientDb c").list();
        session.getTransaction().commit();
        session.close();

        log.info("getAllForAdmin() returns : allUsers.size()=" + allUsers.size());
        return allUsers;
    }

    public List<FilmJSONIndex> getFilmsForSuggestion(int page, int idClient) throws HibernateException {
        log.info("getFilmsForSuggestion(page=" + page + ", idClient= " + idClient + ")");

        Session session = sessionFactory.openSession();
        int limit = 10;
        int start = (page - 1) * limit;
        session.beginTransaction();
        List<FilmJSONIndex> list = session.createQuery("select cd.filmByIdFilm.title, cd.filmByIdFilm.releaseDate, cd.filmByIdFilm.cover, cd.filmByIdFilm.id, cd.filmByIdFilm.rating from ClientDataDb cd where cd.clientByIdClient.id = ?").setParameter(0, idClient).setFirstResult(start).setMaxResults(limit).list();
        session.getTransaction().commit();
        session.close();

        log.info("getFilmsForSuggestion() returns : list.size()=" + list.size());
        return list;
    }

    public long getNumberOfSuggested(int idClient) throws HibernateException {
        log.info("getNumberOfSuggested()");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long result = (long) session.createQuery("select count(F.filmByIdFilm.id) from ClientDataDb F where F.clientByIdClient.id = ?").setParameter(0, idClient).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getNumberOfSuggested() returns : result=" + result);
        return result;
    }

}
