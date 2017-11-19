package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import web.dao.ScreenshotDb;
import web.services.ScreenshotService;

import java.util.List;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Controller
@RequestMapping("/screenshot")
public class ScreenshotController {

    @Autowired
    private ScreenshotService screenshotService;


    private static final Logger log = LogManager.getLogger(ScreenshotController.class);

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody
    String addOrUpdate(ScreenshotDb screenshotDb) {
        log.info("addOrUpdate(screenshotDb=" + screenshotDb + ")");

        screenshotService.saveOrUpdate(screenshotDb);

        log.info("addOrUpdate() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        log.info("delete(id=" + id + ")");

        screenshotService.delete(id);

        log.info("delete() returns : OK");
        return "OK";
    }

    //    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public @ResponseBody
    List<ScreenshotDb> getAll() {
        log.info("getAll()");

        List<ScreenshotDb> screenshotDbs = screenshotService.getAll();

        log.info("getAll() returns : screenshotDbs.size()=" + screenshotDbs.size());
        return screenshotDbs;
    }
}
