package it.intesys.movierater.app.repository;

import it.intesys.movierater.app.domain.ActorMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorMovieRepository extends JpaRepository<ActorMovie, Integer> {
    List<ActorMovie> findByMovie_Id(Integer id);
    List<ActorMovie> findActorMoviesByActor_Id(Integer id);
}
