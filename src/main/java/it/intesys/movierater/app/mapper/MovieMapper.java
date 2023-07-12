package it.intesys.movierater.app.mapper;

import it.intesys.movierater.app.domain.Movie;
import it.intesys.movierater.app.dto.MovieDTO;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {
    public MovieDTO toDTO(Movie movie){
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movie.getId().longValue());
        movieDTO.setDirector(movie.getDirector());
        movieDTO.setTitle(movie.getTitle());
        movieDTO.setVotes(movie.getVotes());
        movieDTO.setActors(movie.getActors());
        movieDTO.setYear(movie.getYear());
        movieDTO.setGenre(movie.getGenre());
        movieDTO.setCountry(movie.getCountry());
        return movieDTO;
    }
    public Movie toEntity(MovieDTO movieDTO){
        Movie movie = new Movie();
        movie.setId(movieDTO.getId().intValue());
        movie.setDirector(movieDTO.getDirector());
        movie.setTitle(movieDTO.getTitle());
        movie.setVotes(movieDTO.getVotes());
        movie.setActors(movieDTO.getActors());
        movie.setYear(movieDTO.getYear());
        movie.setGenre(movieDTO.getGenre());
        movie.setCountry(movieDTO.getCountry());
        return movie;
    }
}
