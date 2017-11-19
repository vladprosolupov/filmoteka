package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.model.FilmJSONIndex;

import java.util.List;

@Service("FilmCategoryService")
@Transactional
public class FilmCategoryService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private FilmService filmService;

    @Autowired
    private CategoryService categoryService;

    private static final Logger log = LogManager.getLogger(FilmCategoryService.class);

    public List<FilmJSONIndex> getAllFilmsForCategory(String id){
        log.info("getAllFilmsForCategory(id=" + id + ")");

        Session session = sessionFactory.getCurrentSession();
        List list = session.createQuery("select f.title, f.releaseDate, f.cover, f.id from FilmDb f INNER JOIN f.filmCategories fc WHERE fc.id = " + id).list();

        log.info("getAllFilmsForCategory() returns : list.size()" + list.size());
        return list;
    }

}
