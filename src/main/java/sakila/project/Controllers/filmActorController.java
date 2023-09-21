package sakila.project.Controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import sakila.project.Repository.actorRepository;
import sakila.project.Repository.filmActorRepository;
import sakila.project.Repository.filmRepository;
import sakila.project.entities.Actor;
import sakila.project.entities.Film;
import sakila.project.entities.filmActor;

@RestController 
@RequestMapping(path="/filmActor") 
@CrossOrigin(origins = "http://localhost:3000")
public class filmActorController {
    @Autowired 
    private filmActorRepository filmActorRepo;
    @Autowired 
    private filmRepository filmRepo;
    @Autowired 
    private actorRepository actorRepo;
    @GetMapping(path="/all")
    public @ResponseBody Iterable<filmActor> getAllUsers() {
        return filmActorRepo.findAll();
    }
    @GetMapping(path="/get/actor")
    public @ResponseBody Iterable<filmActor> getUsers(@RequestParam String forename, @RequestParam String surname ) {
        Short actorID = actorRepo.findByFirstName(forename, surname).getActor_id();
        System.out.println(actorID);
        return filmActorRepo.findByActorID(actorID);
    }
    @GetMapping(path="/get/bruh")
    public @ResponseBody Iterable<Film> getfi() {
        return filmRepo.findAll();
    }
}