package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.CategoryDb;

import java.util.List;

/**
 * Created by Rostyk on 15.06.2017.
 */
@Service("CategoryService")
@Transactional
public class CategoryService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public List<CategoryDb> getAllCategories(){
        Session session = sessionFactory.getCurrentSession();
        List<CategoryDb> result = session.createQuery("from CategoryDb").list();
        return result;
    }
}
