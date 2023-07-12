package it.intesys.movierater.app;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

// Classe per memorizzare il nome dell'attore e la durata della carriera

public class ActorCareer {
    private Integer actor;
    private int careerDuration;

    public ActorCareer(Integer actor, int careerDuration) {
        this.actor = actor;
        this.careerDuration = careerDuration;
    }

    public Integer getActor() {
        return actor;
    }

    public int getCareerDuration() {
        return careerDuration;
    }
}
