package it.intesys.movierater.app.mapper;

import it.intesys.movierater.app.domain.Actor;
import it.intesys.movierater.app.dto.ActorDTO;
import org.springframework.stereotype.Component;

@Component
public class ActorMapper {
    public ActorDTO toDTO(Actor actor){
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(actor.getId());
        actorDTO.setName(actor.getName());
        actorDTO.setSurname(actor.getSurname());
        return actorDTO;
    }
    public Actor toEntity(ActorDTO actorDTO){
        Actor actor = new Actor();
        actor.setId(actorDTO.getId());
        actor.setName(actorDTO.getName());
        actor.setSurname(actorDTO.getSurname());
        return actor;
    }
}
