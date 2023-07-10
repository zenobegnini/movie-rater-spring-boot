package it.intesys.movierater.app.service;

import it.intesys.movierater.app.MovieMapper;
import it.intesys.movierater.app.domain.Actor;
import it.intesys.movierater.app.domain.ActorMovie;
import it.intesys.movierater.app.domain.Movie;
import it.intesys.movierater.app.repository.ActorMovieRepository;
import it.intesys.movierater.app.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ActorService {
    private final MovieService movieService;
    private final MovieMapper movieMapper;
    private final ActorRepository actorRepository;
    private final ActorMovieRepository actorMovieRepository;
    public ActorService(MovieService movieService, MovieMapper movieMapper, ActorRepository actorRepository, ActorMovieRepository actorMovieRepository) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.actorRepository = actorRepository;
        this.actorMovieRepository = actorMovieRepository;
    }
    public void Migration(){
        List<Movie> movies = movieService.getAllMovies().stream().map(movieMapper::toEntity).collect(Collectors.toList());
        Map<Integer, List<Integer>> actorsMoviesMap = new HashMap<>();

        // mappa in cui la chiave è il nome dell'attore e il valore è una lista di film in cui l'attore ha recitato
        for (Movie movie : movies) {
            String[] actors = movie.getActors().split(", ");

            // creo Actor
            for (String actor : actors) {
                Actor newActor = new Actor();
                newActor.setName(actor.split(" ")[0]);
                newActor.setSurname(actor.split(" ")[1]);
                newActor = actorRepository.save(newActor);


                actorsMoviesMap.computeIfAbsent(newActor.getId(), k -> new ArrayList<>()).add(movie.getId());
            }

        }

        for (Integer actorWithMovies: actorsMoviesMap.keySet()) {
            ActorMovie actorMovie = new ActorMovie();
            actorMovie.setActor(actorRepository.findById(actorWithMovies).get());
            // per ogni film di questo attore creo una corrispondenza in ActoMovie
            for (Integer movie: actorsMoviesMap.get(actorWithMovies)) {
                actorMovie.setMovie(movieMapper.toEntity(movieService.getMovie(movie)));
                actorMovieRepository.save(actorMovie);
            }


        }

    }
}