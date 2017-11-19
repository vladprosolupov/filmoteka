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

    private static final Logger log = LogManager.getLogger(CategoryService.class);

    public List<CategoryDb> getAllCategories() throws HibernateException {
        log.info("getAllCategories()");

        Session session = sessionFactory.getCurrentSession();
        List<CategoryDb> result = session.createQuery("from CategoryDb").list();

        log.info("getAllCategories() returns : result.size()=" + result.size());
        return result;
    }

    public List<CategoryJSON> getForNav() {
        log.info("getForNav()");

        Session session = sessionFactory.getCurrentSession();
        int limit = 10;
        List<CategoryJSON> result = session.createQuery("select C.id, C.name from CategoryDb C").setMaxResults(limit).list();

        log.info("getForNav() returns : result.size()" + result.size());
        return result;
    }

    public CategoryDb getCategoryWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getCategoryWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        CategoryDb categoryDb = (CategoryDb) session.createQuery("from CategoryDb c where c.id=" + id).list().get(0);

        log.info("getCategoryWithId() returns : categoryDb=" + categoryDb);
        return categoryDb;
    }

    public void saveOrUpdate(CategoryDb categoryDb) throws HibernateException {
        log.info("saveOrUpdate(categoryDb=" + categoryDb + ")");

        if (categoryDb == null) {
            log.error("Error : categoryDb is null");

            throw new IllegalArgumentException("CategoryDb should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(categoryDb);

        log.info("succ. saved or updated category");
    }

    public void deleteCategory(String id) throws HibernateException {
        log.info("deleteCategory(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from CategoryDb c where c.id=" + id).executeUpdate();

        log.info("succ. deleted category");
    }

    public CategoryDb convertToCategoryDb(CategoryJSON categoryJSON) throws ParsingJsonToDaoException {
        log.info("convertToCategoryDb(categoryJSON=" + categoryJSON + ")");

        if (categoryJSON == null) {
            log.error("Error : categoryJSON is null");

            throw new IllegalArgumentException("CategoryJSON should not be null");
        }
        CategoryDb categoryDb = new CategoryDb();
        categoryDb.setId(categoryJSON.getId());
        categoryDb.setName(categoryJSON.getName());

        log.info("convertTiCategoryDb() retuns : categoryDb=" + categoryDb);
        return categoryDb;
    }
}
