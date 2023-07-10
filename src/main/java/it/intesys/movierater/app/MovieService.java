package it.intesys.movierater.app;

import org.javatuples.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
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

        return movieRepository.findAll().stream().count();

    }

    public void vote(Long movieId) {
        Movie movie = movieRepository.findById(movieId.intValue()).get();
        movie.setVotes(movie.getVotes() + 1 );
        movieRepository.save(movie);
        logger.info("Add vote for movie {}", movieId);
        logger.info("this is the vote of the film: {}", movieRepository.findById(movieId.intValue()).get().getVotes());
    }
}
