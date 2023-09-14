package sakila.project;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sakila.project.Repository.actorRepository;
import sakila.project.entities.Actor;


@RestController // This means that this class is a Controller
@RequestMapping(path="/actor") // This means URL's start with /demo (after Application path)
@CrossOrigin(origins = "http://localhost:3000")
public class MainController {
    @Autowired 
    private actorRepository actorRepository;

    @PostMapping(path="/add") 
    public @ResponseBody String addNewUser (@RequestBody Actor infomation) {
        Actor n = infomation;
        n.setLast_update();
        if (actorRepository.findByFirstName(infomation.getFirst_name(), infomation.getLast_name()) != null) {
            return "User already exist";
        }
        actorRepository.save(n);
        return "Saved";
    }
    @PutMapping(path="/update") 
    public @ResponseBody String updateUser (@RequestBody HashMap infomation) {
        String forename = (String) infomation.get("forename");
        String surname = (String) infomation.get("surname");
        Actor newInformation = (Actor) infomation.get("actor");
        Actor actor = actorRepository.findByFirstName(forename, surname);
        if (actor!=null) {
            actor = newInformation;
            actor.setLast_update();
            actorRepository.save(actor);
            return "Changed";
        }
        return "User doesn't exist";
    }
    @DeleteMapping(path="/delete") 
    public @ResponseBody String deleteUser (@RequestBody Actor actor) {
        Actor delActor = actorRepository.findByFirstName(actor.getFirst_name(),actor.getLast_name());
        if (delActor!=null) {
            actorRepository.delete(delActor);
            return "Deleted";
        }
        return "User doesn't exist";
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Actor> getAllUsers() {
        return actorRepository.findAll();
    }
    @GetMapping(path="/all")
    public @ResponseBody String getUsers(@RequestParam String forename, @RequestParam String surname ) {
        Actor actor = actorRepository.findByFirstName(forename, surname); 
        if (actor!=null)
            return actor.toString();
        return "User doesn't exist";
    }
}