package it.intesys.movierater.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Override
    List<Movie> findAll();
    @Override
    Optional<Movie> findById(Integer id);

}
