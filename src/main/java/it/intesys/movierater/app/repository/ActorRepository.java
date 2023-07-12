package it.intesys.movierater.app.repository;

import it.intesys.movierater.app.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    @Override
    Optional<Actor> findById(Integer id);
    List<Actor> findActorsByIdIn(List<Integer> id);
    Optional<Actor> findByNameAndAndSurname(String name, String surname);
}
