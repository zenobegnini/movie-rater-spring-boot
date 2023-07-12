package it.intesys.movierater.app.service;

import it.intesys.movierater.app.ActorCareer;
import it.intesys.movierater.app.dto.MovieDTO;
import it.intesys.movierater.app.mapper.MovieMapper;
import it.intesys.movierater.app.domain.Movie;
import it.intesys.movierater.app.repository.MovieRepository;
import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final Logger logger = LoggerFactory.getLogger(MovieService.class);
    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public Pair<MovieDTO, MovieDTO> get2RandomMovies() {
        Random random = new Random();
        List<MovieDTO> movieDTOList= movieRepository.findAll().stream().map(movieMapper::toDTO).collect(Collectors.toList());
        MovieDTO movieDTO1 = movieDTOList.get(random.nextInt((movieDTOList.size() - 1) - 0 + 1) + 0);
        movieDTOList.remove(movieDTO1);
        MovieDTO movieDTO2 = movieDTOList.get(random.nextInt((movieDTOList.size() - 1) - 0 + 1) + 0);

        return Pair.with(
                new MovieDTO(movieDTO1.getId(), movieDTO1.getTitle(), movieDTO1.getDirector()),
                new MovieDTO(movieDTO2.getId(), movieDTO2.getTitle(), movieDTO2.getDirector()));
    }

    public Long getMovieCount() {

        return (Long.valueOf(movieRepository.findAll().size()));

    }

    public void vote(Long movieId) {
        Movie movie = movieRepository.findById(movieId.intValue()).get();
        movie.setVotes(movie.getVotes() + 1 );
        movieRepository.save(movie);
        logger.info("Add vote for movie {}", movieId);
        logger.info("this is the vote of the film: {}", movieRepository.findById(movieId.intValue()).get().getVotes());
    }


    public List<MovieDTO> getAllMovies(){
        return movieRepository.findAll().stream().map(movieMapper::toDTO).collect(Collectors.toList());
    }
    public int getTotalVotes(){
        int votes = 0;
        for(MovieDTO movie : getAllMovies()){
            votes = votes + movie.getVotes();
        }
        return votes;
    }

    public MovieDTO getMovie(Integer id){
        return movieMapper.toDTO(movieRepository.findById(id).get());
    }
    public List<MovieDTO> getMoviesById(List<Integer> moviesId){
            return movieRepository.findMoviesByIdIn(moviesId).stream().map(movieMapper::toDTO).collect(Collectors.toList());
    }
    public void deleteActorColumn(){
        for (MovieDTO movie :getAllMovies()) {
            movie.setActors(" ");
            movieRepository.save(movieMapper.toEntity(movie));
        }
    }

}
