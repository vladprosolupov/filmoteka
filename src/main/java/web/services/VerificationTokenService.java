package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.VerificationTokenDb;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service("VerificationTokenService")
@Transactional
public class VerificationTokenService {

    private final int EXPIRIANCE_TIME = 60 * 24;

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(VerificationTokenService.class);

    public void saveToken(VerificationTokenDb tokenDb) throws HibernateException {
        log.info("saveToken(tokenDb=" + tokenDb + ")");

        if (tokenDb == null) {
            log.error("Error : tokenDb is null");

            throw new IllegalArgumentException("TokenDb should not be null");
        }

        tokenDb.setExpiryDate(computeExpiryDate());
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(tokenDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved token");
    }

    public List<VerificationTokenDb> getVerificationToken(String verificationToken) throws HibernateException, IndexOutOfBoundsException {
        log.info("getVerificationToken(verificationToken=" + verificationToken + ")");

        if (verificationToken == null || verificationToken.isEmpty()) {
            log.error("Error : verificationToken is null or empty");

            throw new IllegalArgumentException("Verification token should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<VerificationTokenDb> result = (List<VerificationTokenDb>) session.createQuery("from VerificationTokenDb v where v.token='" + verificationToken + "'").list();
        session.getTransaction().commit();
        session.close();

        log.info("getVerificationToken() returns : result.size()=" + result.size());
        return result;
    }

    public void removeVerificationToken(VerificationTokenDb verificationTokenDb) throws HibernateException{
        log.info("removeVerificationToken(verificationTokenDb=" + verificationTokenDb + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(verificationTokenDb);
        session.getTransaction().commit();
        session.close();
    }

    private Timestamp computeExpiryDate() {
        log.info("computeExpiryDate()");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE, EXPIRIANCE_TIME);
        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());

        log.info("computeExpiryDate() returns : timestamp=" + timestamp);
        return timestamp;
    }
}
