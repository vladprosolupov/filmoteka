package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.services.AiService;

import java.util.concurrent.ExecutionException;

public class AiTask implements Runnable {

    private SessionFactory sessionFactory;

    private ClientDb currentClient;

    private static final Logger log = LogManager.getLogger(AiTask.class);

    public AiTask(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void run() {
        log.info("run()");

        AiService aiService = new AiService(sessionFactory);

        try {
            aiService.generateFilmsForSuggestion(currentClient);
        } catch (ExecutionException e) {
            log.error("ERROR - " + e);
        } catch (InterruptedException e) {
            log.error("ERROR - " + e);
        }

        log.info("run() done");
    }

    public ClientDb getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(ClientDb currentClient) {
        this.currentClient = currentClient;
    }
}
