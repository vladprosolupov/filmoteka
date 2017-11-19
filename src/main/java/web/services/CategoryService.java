package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.CategoryDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.CategoryJSON;

import java.util.List;

/**
 * Created by Rostyk on 15.06.2017.
 */
@Service("CategoryService")
@Transactional
public class CategoryService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(AwardService.class);

    public List<CategoryDb> getAllCategories() throws HibernateException {
        log.info("getAllCategories()");

        Session session = sessionFactory.getCurrentSession();
        List<CategoryDb> result = session.createQuery("from CategoryDb").list();

        log.info("getAllCategories() returns : result.size()=" + result.size());
        return result;
    }

    public List<CategoryJSON> getForNav(){
        log.info("getForNav()");

        Session session = sessionFactory.getCurrentSession();
        int limit = 10;
        List<CategoryJSON> result = session.createQuery("select C.id, C.name from CategoryDb C").setMaxResults(limit).list();

        log.info("getForNav() returns : result.size()" + result.size());
        return result;
    }

    public CategoryDb getCategoryWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        CategoryDb categoryDb = (CategoryDb) session.createQuery("from CategoryDb c where c.id=" + id).list().get(0);
        return categoryDb;
    }

    public void saveOrUpdate(CategoryDb categoryDb) throws HibernateException {
        if (categoryDb == null) {
            throw new IllegalArgumentException("CategoryDb should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(categoryDb);
    }

    public void deleteCategory(String id) throws HibernateException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from CategoryDb c where c.id=" + id).executeUpdate();
    }

    public CategoryDb convertToCategoryDb(CategoryJSON categoryJSON) throws ParsingJsonToDaoException {
        if (categoryJSON == null) {
            throw new IllegalArgumentException("CategoryJSON should not be null");
        }
        CategoryDb categoryDb = new CategoryDb();
        categoryDb.setId(categoryJSON.getId());
        categoryDb.setName(categoryJSON.getName());
        return categoryDb;
    }
}
