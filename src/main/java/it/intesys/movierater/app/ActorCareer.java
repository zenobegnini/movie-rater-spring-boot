package it.intesys.movierater.app;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// Classe per memorizzare il nome dell'attore e la durata della carriera

public class ActorCareer {
    private String actor;
    private int careerDuration;

    public ActorCareer(String actor, int careerDuration) {
        this.actor = actor;
        this.careerDuration = careerDuration;
    }

    public String getActor() {
        return actor;
    }

    public int getCareerDuration() {
        return careerDuration;
    }
}
