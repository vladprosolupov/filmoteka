package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.dao.FilmDb;
import web.services.AiService;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

@Component("GetPropActorTask")
public class GetPropActorTask implements Callable<Set<FilmDb>> {

    @Autowired
    private AiService aiService;

    private ClientDb currentClient;
    private Map<Integer, Integer> percentage;

    private static final Logger log = LogManager.getLogger(GetPropActorTask.class);

    @Override
    public Set<FilmDb> call() throws Exception {
        log.info("call()");

        Set<FilmDb> filmDbSet = new HashSet<>();
        for(Map.Entry<Integer, Integer> entry : percentage.entrySet()) {
            filmDbSet.addAll(aiService.getPropActor(entry.getValue(), entry.getKey(), currentClient));
        }

        log.info("call() returns : filmDbSet.size()=" + filmDbSet.size());
        return filmDbSet;
    }

    public ClientDb getCurrentClient() {
        return currentClient;
    }

    public void setCurrentClient(ClientDb currentClient) {
        this.currentClient = currentClient;
    }

    public Map<Integer, Integer> getPercentage() {
        return percentage;
    }

    public void setPercentage(Map<Integer, Integer> percentage) {
        this.percentage = percentage;
    }
}
