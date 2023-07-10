package it.intesys.movierater.app;

import it.intesys.movierater.app.domain.Movie;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public MovieDTO toDTO(Movie movie){
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId().longValue());
        movieDTO.setDirector(movie.getDirector());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setVotes(movie.getVotes());
        return movieDTO;
    }
    public Movie toEntity(MovieDTO movieDTO){
        Movie movie = new Movie();
        movie.setId(movieDTO.getId().intValue());
        movie.setDirector(movieDTO.getDirector());
        movie.setTitle(movieDTO.getTitle());
        movie.setVotes(movieDTO.getVotes());
        return movie;
    }
}
