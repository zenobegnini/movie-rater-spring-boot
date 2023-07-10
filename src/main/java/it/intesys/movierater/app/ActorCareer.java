package it.intesys.movierater.app;
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
