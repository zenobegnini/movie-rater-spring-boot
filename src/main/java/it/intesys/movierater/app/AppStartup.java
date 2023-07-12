package it.intesys.movierater.app;

import it.intesys.movierater.app.service.ActorService;
import it.intesys.movierater.app.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppStartup {

    private final Logger log = LoggerFactory.getLogger(AppStartup.class);
    private final ActorService actorService;

    public AppStartup(ActorService actorService) {
        this.actorService = actorService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void calculateActorsWithLongestCareer() {
        List<String> actorsWithLongestCareer = actorService.getActorsWithLongestCareers();

        log.info("The top 3 actors for the longest career: \n 1- {} \n 2- {}\n 3- {}",actorsWithLongestCareer.get(0), actorsWithLongestCareer.get(1), actorsWithLongestCareer.get(2) );
    }
}
