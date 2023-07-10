package it.intesys.movierater.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppStartup {

    private final Logger log = LoggerFactory.getLogger(AppStartup.class);
    private final MovieService movieService;

    public AppStartup(MovieService movieService) {
        this.movieService = movieService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void calculateActorsWithLongestCareer() {
        List<String> actorsWithLongestCareer = movieService.getActorsWithLongestCareers();

        log.info("The top 3 actors for the longest career: \n 1- {} \n 2- {}\n 3- {}",actorsWithLongestCareer.get(0), actorsWithLongestCareer.get(1), actorsWithLongestCareer.get(2) );
    }
}
