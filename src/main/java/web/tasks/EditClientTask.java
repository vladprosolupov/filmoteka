package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ClientJSON;
import web.services.ClientService;

@Component("EditClientTask")
public class EditClientTask implements Runnable {

    private SessionFactory sessionFactory;

    private ClientJSON clientJSON;

    private static final Logger log = LogManager.getLogger(EditClientTask.class);

    public EditClientTask(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void run() {
        log.info("run(); clientJSON=" + clientJSON);

        ClientService clientService = new ClientService(sessionFactory);

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
