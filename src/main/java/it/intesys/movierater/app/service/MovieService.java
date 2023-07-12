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

    public List<String> getActorsWithLongestCareers() {
        List<Movie> movies = movieRepository.findAll();

        Map<String, List<Movie>> actorsMoviesMap = new HashMap<>();

        // mappa in cui la chiave è il nome dell'attore e il valore è una lista di film in cui l'attore ha recitato
        for (Movie movie : movies) {
            String[] actors = movie.getActors().split(", ");
            for (String actor : actors) {
                actorsMoviesMap.computeIfAbsent(actor, k -> new ArrayList<>()).add(movie);
            }
        }

        List<ActorCareer> actorCareers = new ArrayList<>();

        // Calcola la durata della carriera per ciascun attore
        for (Map.Entry<String, List<Movie>> entry : actorsMoviesMap.entrySet()) {
            String actor = entry.getKey();
            List<Movie> actorMovies = entry.getValue();

            int minYear = actorMovies.stream().mapToInt(Movie::getYear).min().orElse(0);
            int maxYear = actorMovies.stream().mapToInt(Movie::getYear).max().orElse(0);

            int careerDuration = maxYear - minYear;

            actorCareers.add(new ActorCareer(actor, careerDuration));
        }

        // Ordina gli attori in base alla durata della carriera in ordine decrescente
        actorCareers.sort(Comparator.comparingInt(ActorCareer::getCareerDuration).reversed());

        // Seleziona i primi tre attori con la carriera più lunga
        List<String> topActors = new ArrayList<>();
        for (int i = 0; i < Math.min(actorCareers.size(), 3); i++) {
            topActors.add(actorCareers.get(i).getActor());
        }

        return topActors;
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

}
