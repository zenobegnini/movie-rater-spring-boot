package it.intesys.movierater.app;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Override
    List<Movie> findAll();
}
