package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.PasswordResetTokenDb;

import java.sql.Timestamp;
import java.util.Calendar;

@Service("PasswordResetTokenService")
@Transactional
public class PasswordResetTokenService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    private final int EXPIRIANCE_TIME = 60 * 24;

    private static final Logger log = LogManager.getLogger(PasswordResetTokenService.class);

    public void savePasswordResetToken(PasswordResetTokenDb passwordResetTokenDb) throws HibernateException {
        log.info("savePasswordResetToken(passwordResetTokenDb=" + passwordResetTokenDb + ")");

        passwordResetTokenDb.setExpiryDate(computeExpiryDate());

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(passwordResetTokenDb);
        session.getTransaction().commit();
        session.close();
    }

    public PasswordResetTokenDb getPasswordResetToken(String token) throws HibernateException, IndexOutOfBoundsException {
        log.info("getPasswordResetToken(token=" + token + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        PasswordResetTokenDb passwordResetTokenDb = (PasswordResetTokenDb) session.createQuery("from PasswordResetTokenDb p where p.token='" + token + "'").list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getPasswordResetToken() returns : passwordResetTokenDb=" + passwordResetTokenDb);
        return passwordResetTokenDb;
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
