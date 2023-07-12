package it.intesys.movierater.app.service;

import it.intesys.movierater.app.ActorCareer;
import it.intesys.movierater.app.dto.ActorDTO;
import it.intesys.movierater.app.dto.MovieDTO;
import it.intesys.movierater.app.mapper.ActorMapper;
import it.intesys.movierater.app.mapper.MovieMapper;
import it.intesys.movierater.app.domain.Actor;
import it.intesys.movierater.app.domain.ActorMovie;
import it.intesys.movierater.app.domain.Movie;
import it.intesys.movierater.app.repository.ActorMovieRepository;
import it.intesys.movierater.app.repository.ActorRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActorService {
    private final MovieService movieService;
    private final MovieMapper movieMapper;
    private final ActorRepository actorRepository;
    private final ActorMovieRepository actorMovieRepository;
    private final ActorMapper actorMapper;
    public ActorService(MovieService movieService, MovieMapper movieMapper, ActorRepository actorRepository, ActorMovieRepository actorMovieRepository, ActorMapper actorMapper) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.actorRepository = actorRepository;
        this.actorMovieRepository = actorMovieRepository;
        this.actorMapper = actorMapper;
    }
    public void migration(){
        List<Movie> movies = movieService.getAllMovies().stream().map(movieMapper::toEntity).collect(Collectors.toList());
        Map<Integer, List<Integer>> actorsMoviesMap = new HashMap<>();

        // mappa in cui la chiave è il nome dell'attore e il valore è una lista di film in cui l'attore ha recitato
        for (Movie movie : movies) {
            String[] actors = movie.getActors().split(", ");

            for (String actor : actors) {
                String[] nameParts = actor.split(" ", 2);
                String firstName = nameParts[0];
                String lastName = nameParts.length > 1 ? nameParts[1] : "";

                Optional<Actor> existingActor = actorRepository.findByNameAndAndSurname(firstName, lastName);

                Actor currentActor;
                if (existingActor.isPresent()) {
                    currentActor = existingActor.get();
                } else {
                    currentActor = new Actor();
                    currentActor.setName(firstName);
                    currentActor.setSurname(lastName);
                    currentActor = actorRepository.save(currentActor);
                }

                ActorMovie actorMovie = new ActorMovie();
                actorMovie.setActor(currentActor);
                actorMovie.setMovie(movie);
                actorMovieRepository.save(actorMovie);
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

        movieService.deleteActorColumn();

    }
    public List<ActorDTO> getActors(Integer movie_id){
        List<ActorMovie> actorMovies = actorMovieRepository.findByMovie_Id(movie_id);
        List<Integer> actorsId = new ArrayList<>();
        for (ActorMovie actorMovie: actorMovies) {
            actorsId.add(actorMovie.getActor().getId());
        }
        return actorRepository.findActorsByIdIn(actorsId).stream().map(actorMapper::toDTO).collect(Collectors.toList());
    }
    public ActorDTO getActor(Integer actorId){
        ActorDTO actorDTO = new ActorDTO();
        actorDTO = actorMapper.toDTO(actorRepository.findById(actorId).get());
        return actorDTO;
    }
    public boolean isMigrated(){
        return !actorRepository.findAll().isEmpty();

    }
    public List<MovieDTO> getMovieByActor(Integer actorId){
        List<ActorMovie> actorMovies = actorMovieRepository.findActorMoviesByActor_Id(actorId);
        List<Integer> moviesId = new ArrayList();
        for (ActorMovie actorMovie: actorMovies) {
            moviesId.add(actorMovie.getMovie().getId());
        }
        return movieService.getMoviesById(moviesId);
    }
    public List<String> getActorsWithLongestCareers() {
        List<Movie> movies = movieService.getAllMovies().stream().map(movieMapper::toEntity).collect(Collectors.toList());

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
    public List<ActorDTO> getAllActors(){
        return actorRepository.findAll().stream().map(actorMapper::toDTO).collect(Collectors.toList());
    }

    public String isTopActor(Integer actorId) {
        // ottengo una mappa che ad ogni actorId fa corrispondere la somma dei voti dei suoi film
        Map<Integer, Integer> actorVotes = new HashMap<>();
        for (ActorDTO actor : getAllActors()) {
            int votes = 0;
            for (MovieDTO movie : getMovieByActor(actor.getId())) {
                votes += movie.getVotes();
            }
            actorVotes.put(actor.getId(), votes);
        }

        // ordino in ordine decrescente la mappa
        List<Map.Entry<Integer, Integer>> sortedActorVotes = new ArrayList<>(actorVotes.entrySet());
        sortedActorVotes.sort(Map.Entry.comparingByValue(Comparator.reverseOrder()));

        int counter = 0;
        for (Map.Entry<Integer, Integer> entry : sortedActorVotes) {
            counter++;
            if (entry.getKey().equals(actorId)) {
                if (counter <= 10) {
                    return "Top 10";
                } else if (counter <= 100) {
                    return "Top 100";
                }
            }
        }

        return "";
    }

}
