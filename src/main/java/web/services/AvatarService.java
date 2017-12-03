package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.AvatarDb;

import java.io.StringReader;

@Service("AvatarService")
@Transactional
public class AvatarService {

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(AvatarService.class);

    public AvatarDb getAvatarById(String id) throws IndexOutOfBoundsException {
        log.info("getAvatarById(id=" + id + ")");

        if(id == null || id.isEmpty()) {
            log.error("id is null or empty");

            throw new IllegalArgumentException("Id is null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        AvatarDb avatarDb = (AvatarDb) session.createQuery("from AvatarDb a where a.id='" + id + "'").list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getAvatarById() returns : avatarDb=" + avatarDb);
        return avatarDb;
    }

    public AvatarDb getAvatarByPath(String path) throws IndexOutOfBoundsException {
        log.info("getAvatarByPath(path=" + path + ")");

        if(path == null || path.isEmpty()) {
            log.error("link is null or empty");

            throw new IllegalArgumentException("Path is null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        AvatarDb avatarDb = (AvatarDb) session.createQuery("from AvatarDb a where a.path=?").setParameter(0, path).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getAvatarByPath() returns : avatarDb=" + avatarDb);
        return avatarDb;
    }
}
