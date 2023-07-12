package it.intesys.movierater.app.controller;

import it.intesys.movierater.app.service.ActorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    @GetMapping("/actor/{actorId}")
    public String getActorDetails(@PathVariable("actorId") Integer actorId, Model model) {
        if(!actorService.isMigrated()){
            actorService.migration();
        }
        model.addAttribute("actor", actorService.getActor(actorId));
        model.addAttribute("movie", actorService.getMovieByActor(actorId));
        return "actor";
    }
}
