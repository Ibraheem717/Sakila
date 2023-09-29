package sakila.project.controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import sakila.project.repository.ActorRepository;
import sakila.project.repository.FilmActorRepository;
import sakila.project.repository.FilmRepository;
import sakila.project.entities.Actor;
import static sakila.project.ProjectApplication.*;

@RestController 
@RequestMapping(path="/actor") 
@CrossOrigin(origins = {"https://main.d21mmybmnqen80.amplifyapp.com", "http://localhost:3000"})
public class ActorController {
    @Autowired 
    private ActorRepository actorRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private FilmActorRepository filmActorRepo;
    @Autowired
    private FilmRepository filmRepo;
    private String returnString(String extra) {
        return "Actor " + extra;
    }
    @PostMapping(path="/add") 
    public @ResponseBody Map<String, String> addNewActor (@RequestBody Map<String, String> information) {
        Actor n =  objectMapper.convertValue(information, Actor.class);
        if (actorRepository.findByFirstName( n.getFirst_name().toUpperCase(),  n.getLast_name().toUpperCase()) != null) {
            return returnValue(returnString(EXIST));
        }
        n.setLast_update();
        actorRepository.save(n);
        return returnValue(SAVED);
    }
    @PutMapping(path = "/update")
    public @ResponseBody Map<String, String> updateActor(@RequestBody Map<String, Object> information) {
        String forename = (String) information.get("forename");
        String surname = (String) information.get("surname");
        Actor existingActor = actorRepository.findByFirstName(forename.toUpperCase(), surname.toUpperCase());
        if (existingActor != null) {
            Actor newInformation = objectMapper.convertValue(information.get("actor"), Actor.class);
            if (actorRepository.findByFirstName(newInformation.getFirst_name(), newInformation.getLast_name()) != null)
                return returnValue(returnString(EXIST));
            existingActor.setFirst_name(newInformation.getFirst_name());
            existingActor.setLast_name(newInformation.getLast_name());
            existingActor.setLast_update();            
            actorRepository.save(existingActor);
            return returnValue(SAVED);
        }
    
        return returnValue(returnString(NONEXIST));
    }
    @DeleteMapping(path="/delete") 
    public @ResponseBody Map<String, String> deleteActor (@RequestBody Map<String, String> actor) {
        Actor delActor = actorRepository.findByFirstName(actor.get("first_name").toUpperCase(),actor.get("last_name").toUpperCase());
        if (delActor!=null) {
            filmActorRepo.DeleteByActorID(delActor.getActor_id());
            actorRepository.delete(delActor);
            return returnValue(DELETED);
        }
        return returnValue(returnString(NONEXIST));
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Actor> getAllActors() {
        return actorRepository.findAll();
    }
    @GetMapping(path="/get")
        public @ResponseBody Map<String, String> getActors(@RequestParam String forename, @RequestParam String surname ) {
        Actor actor = actorRepository.findByFirstName(forename.toUpperCase(), surname.toUpperCase());
        Map<String, String> act = new HashMap<>();
        if (actor!=null) {
            act.put("first_name", actor.getFirst_name());
            act.put("last_name" , actor.getLast_name());
        }
        else
            act = returnValue(returnString(NONEXIST));
        return act;
    }
    @GetMapping(path = "/films")
    public @ResponseBody List<Map<String, Object>> getFilms(String firstName, String lastName) {
        Iterable<Short> films = filmActorRepo.getByActorId(actorRepository.findByFirstName(firstName, lastName).getActor_id());
        List<Map<String, Object>> allFilms = new ArrayList<>();
        for (Short filmId : films) {
            allFilms.add(filmRepo.findByFilmId(filmId).getReleventInformation()) ;
        }
        return allFilms;
    } 
}