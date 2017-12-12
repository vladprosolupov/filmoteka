package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.services.AiService;

@Component("AiTask")
public class AiTask implements Runnable {

    @Autowired
    private AiService aiService;

    private ClientDb currentClient;

    private static final Logger log = LogManager.getLogger(AiTask.class);

    @Override
    public void run() {
        log.info("run()");

        aiService.generateFilmsForSuggestion(currentClient);

        log.info("run() done");
    }

    public ClientDb getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(ClientDb currentClient) {
        this.currentClient = currentClient;
    }
}
