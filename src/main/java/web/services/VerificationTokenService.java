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

    private final int EXPIRIANCE_TIME = 60*24;

    @Autowired
    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(VerificationTokenService.class);

    public void saveToken(VerificationTokenDb tokenDb) throws HibernateException {
        log.info("saveToken(tokenDb=" + tokenDb + ")");

        if(tokenDb == null) {
            log.error("Error : tokenDb is null");

            throw new IllegalArgumentException("TokenDb should not be null");
        }

        tokenDb.setExpirenceDate(computeExpireDate());
        Session session = sessionFactory.getCurrentSession();
        session.save(tokenDb);

        log.info("succ. saved token");
    }

    public List<VerificationTokenDb> getVerificationToken(String verificationToken) throws HibernateException, IndexOutOfBoundsException {
        log.info("getVerificationToken(verificationToken=" + verificationToken + ")");

        if(verificationToken == null || verificationToken.isEmpty()) {
            log.error("Error : verificationToken is null or empty");

            throw new IllegalArgumentException("Verification token should not be null or empty");
        }

        Session session = sessionFactory.getCurrentSession();
        List<VerificationTokenDb> result= (List<VerificationTokenDb>) session.createQuery("from VerificationTokenDb v where v.token='" + verificationToken + "'").list();

        log.info("getVerificationToken() returns : result.size()=" + result.size());
        return result;
    }

    private Timestamp computeExpireDate(){
        log.info("computeExpireDate()");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE,EXPIRIANCE_TIME);
        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());

        log.info("computeExpireDate() returns : timestamp=" + timestamp);
        return timestamp;
    }
}
