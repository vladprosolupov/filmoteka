package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ClientDb;
import org.hibernate.Session;
import web.enums.ClientRole;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ClientJSON;

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

    @Autowired(required = true)
    private AddressService addressService;

    private static final Logger log = LogManager.getLogger(ClientService.class);

    public ClientDb saveOrUpdate(ClientDb clientDb) throws HibernateException {
        log.info("saveOrUpdate(clientDb=" + clientDb + ")");

        if (clientDb == null) {
            log.error("Error : clientDb is null");

            throw new IllegalArgumentException("ClientDb should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(clientDb);

        log.info("succ. saved or updated client, clientDb=" + clientDb);
        return clientDb;
    }

    public ClientDb getClientByLogin(String login) throws HibernateException, IndexOutOfBoundsException {
        log.info("getClientByLogin(login=" + login + ")");

        if (login == null || login.isEmpty()) {
            log.error("Error : login is incorrect");

            throw new IllegalArgumentException("Login should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        ClientDb client = (ClientDb) session.createQuery("FROM ClientDb c where c.login='" + login + "'").list().get(0);

        log.info("getClientByLogin() returns : client=" + client);
        return client;
    }

    public List<ClientDb> getAll() throws HibernateException {
        log.info("getAll()");

        Session session = sessionFactory.getCurrentSession();
        List<ClientDb> result = session.createQuery("from ClientDb").list();

        log.info("getAll() returns : result.size()=" + result.size());
        return result;
    }

    public void delete(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from ClientDb c where c.id=" + id).executeUpdate();

        log.info("succ. deleted client");
    }

    public ClientDb getClientById(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getClientById(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        ClientDb clientDb = (ClientDb) session.createQuery("from ClientDb c where c.id=" + id).list().get(0);

        log.info("getClientById() returns : clientDb=" + clientDb);
        return clientDb;
    }

    public ClientDb convertToClientDb(ClientJSON clientJSON) throws ParsingJsonToDaoException, IllegalArgumentException {
        log.info("convertToClientDb(clientJSON=" + clientJSON + ")");

        if (clientJSON == null) {
            log.error("Error : clientJSON is null");

            throw new IllegalArgumentException("ClientJSON should not be null");
        }

        ClientDb clientDb = new ClientDb();

        clientDb.setFirstName(clientJSON.getFirstName());
        clientDb.setLastName(clientJSON.getLastName());
        clientDb.setEmail(clientJSON.getEmail());
//        clientDb.setEnabled(clientJSON.getEnabled());
        clientDb.setLogin(clientJSON.getLogin());
        clientDb.setPassword(PasswordGenerator.hashPassword(clientJSON.getPassword()));
        clientDb.setPhoneNumber(clientJSON.getPhoneNumber());
        clientDb.setRole(ClientRole.user.name());
        clientDb.setCreationDate(new Timestamp(System.currentTimeMillis()));
//        clientDb.setAddressByIdAddress(addressService.getAddressById(clientJSON.getIdAddress()));

        log.info("convertToClientDb() returns : clientDb=" + clientDb);
        return clientDb;
    }
}
