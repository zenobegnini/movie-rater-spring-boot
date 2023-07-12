package it.intesys.movierater.app.controller;

import it.intesys.movierater.app.dto.MovieDTO;
import it.intesys.movierater.app.service.ActorService;
import it.intesys.movierater.app.service.MovieService;
import org.javatuples.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {

    private final MovieService movieService;
    private final ActorService actorService;
    public MovieController(MovieService movieService, ActorService actorService) {
        this.movieService = movieService;
        this.actorService = actorService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Pair<MovieDTO, MovieDTO> randomMovies = movieService.get2RandomMovies();
        model.addAttribute("movie1", randomMovies.getValue0());
        model.addAttribute("movie2", randomMovies.getValue1());
        List<MovieDTO> allMovies = movieService.getAllMovies();
        model.addAttribute("allMovies", allMovies);
        int totalVotes = movieService.getTotalVotes();
        model.addAttribute("totalVotes", totalVotes);
        return "index";
    }

    @ModelAttribute(name="movieCount")
    public Long movieCount() {
        return movieService.getMovieCount();
    }

    @PostMapping("/vote")
    public String submitVote(@ModelAttribute MovieDTO movie) {
        movieService.vote(movie.getId());
        return "redirect:/";
    }

    @GetMapping("/movie/{movieId}")
    public String getMovieDetails(@PathVariable("movieId") Long movieId, Model model) {
        model.addAttribute("movie", movieService.getMovie(movieId.intValue()));
        if(!actorService.isMigrated()){
            actorService.migration();
        }
        model.addAttribute("actors", actorService.getActors(movieId.intValue()));
        return "movie";
    }
}
