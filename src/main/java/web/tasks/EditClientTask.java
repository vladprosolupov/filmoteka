package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ClientJSON;
import web.services.ClientService;

@Component("EditClientTask")
public class EditClientTask implements Runnable {

    @Autowired
    private ClientService clientService;

    private ClientJSON clientJSON;

    private static final Logger log = LogManager.getLogger(EditClientTask.class);

    @Override
    public void run() {
        log.info("run(); clientJSON=" + clientJSON);

        try {
            clientService.saveOrUpdate(clientService.convertToClientDb(clientJSON));
        } catch (ParsingJsonToDaoException e) {
            log.error("ERROR - " + e);
        }

        log.info("succ. edited client");
    }

    public ClientJSON getClientJSON() {
        return clientJSON;
    }

    public void setClientJSON(ClientJSON clientJSON) {
        this.clientJSON = clientJSON;
    }
}
