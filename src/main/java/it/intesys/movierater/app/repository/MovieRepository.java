package it.intesys.movierater.app.repository;

import it.intesys.movierater.app.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Override
    List<Movie> findAll();
    @Override
    Optional<Movie> findById(Integer id);

}
