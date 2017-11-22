package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.LinkToNetworkDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.LinkToNetworkJSON;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("LinkToNetworkService")
@Transactional
public class LinkToNetworkService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private NetworkService networkService;

    private static final Logger log = LogManager.getLogger(LinkToNetworkService.class);

    public LinkToNetworkDb getLinkWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getLinkWithID(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        LinkToNetworkDb linkToNetworkDb =
                (LinkToNetworkDb) session.createQuery("from LinkToNetworkDb l where l.id=" + id).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getLinkWithId() returns : linkToNetworkDb=" + linkToNetworkDb);
        return linkToNetworkDb;
    }

    public int save(LinkToNetworkDb linkToNetworkDb) throws HibernateException {
        log.info("save(linkToNetworkDb=" + linkToNetworkDb + ")");

        if (linkToNetworkDb == null) {
            log.error("Error : linkToNetwork is null");

            throw new IllegalArgumentException("LinkToNetworkDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(linkToNetworkDb);
        session.getTransaction().commit();
        session.close();

        log.info("save() returns : linkToNetworkDb.getId()=" + linkToNetworkDb.getId());
        return linkToNetworkDb.getId();
    }

    public int update(LinkToNetworkDb linkToNetworkDb) throws HibernateException {
        log.info("update(linkToNetwork=" + linkToNetworkDb + ")");

        if (linkToNetworkDb == null) {
            log.error("Error : linkToNetworkDb is null");

            throw new IllegalArgumentException("LinkToNetworkDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(linkToNetworkDb);
        session.getTransaction().commit();
        session.close();

        log.info("update() returns : linkToNetworkDb.getId()=" + linkToNetworkDb.getId());
        return linkToNetworkDb.getId();
    }

    public void delete(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from LinkToNetworkDb l where l.id=" + id).executeUpdate();
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted link");
    }

    public Set<LinkToNetworkDb> createSetOfLinkToNetwork(Map<String, Integer> links) throws ParsingJsonToDaoException {
        log.info("createSetOfLinkToNetwork(links=" + links + ")");

        if (links == null) {
            log.error("Error : link is null");

            throw new IllegalArgumentException("Links should not be null");
        }

        Set<LinkToNetworkDb> linkToNetworkDbSet = new HashSet<>();
        for (Map.Entry<String, Integer> m : links.entrySet()) {
            log.info("for loop");

            LinkToNetworkDb linkToNetworkDb = new LinkToNetworkDb();
            linkToNetworkDb.setLink(m.getKey());
            linkToNetworkDb.setNetworkByIdNetwork(
                    networkService.getNetworkWithId(Integer.toString(m.getValue())));
            linkToNetworkDbSet.add(linkToNetworkDb);
        }

        log.info("createSetOfLinkToNetwork() returns : linkToNetworkDbSet.size()=" + linkToNetworkDbSet.size());
        return linkToNetworkDbSet;
    }
}
