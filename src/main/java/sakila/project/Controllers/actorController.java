package sakila.project.Controllers;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import sakila.project.Repository.actorRepository;
import sakila.project.Repository.filmActorRepository;
import sakila.project.entities.Actor;

@RestController 
@RequestMapping(path="/actor") 
@CrossOrigin(origins = {"http://localhost:8080", "http://localhost:3000"})
public class actorController {
    @Autowired 
    private actorRepository actorRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private filmActorRepository filmActorRepo;
    @PostMapping(path="/add") 
    public @ResponseBody HashMap<String, String> addNewUser (@RequestBody HashMap<String, String> information) {
        Actor n =  objectMapper.convertValue(information, Actor.class);
        if (actorRepository.findByFirstName( n.getFirst_name().toUpperCase(),  n.getLast_name().toUpperCase()) != null) {
            return new HashMap<>(){{put("output","User already exist");}};
        }
        n.setLast_update();
        actorRepository.save(n);
        return new HashMap<>(){{put("output","Saved");}};
    }
    @PutMapping(path = "/update")
    public @ResponseBody HashMap<String, String> updateUser(@RequestBody HashMap<String, Object> information) {
        String forename = (String) information.get("forename");
        String surname = (String) information.get("surname");
        Actor existingActor = actorRepository.findByFirstName(forename.toUpperCase(), surname.toUpperCase());
        if (existingActor != null) {
            Actor newInformation = objectMapper.convertValue(information.get("actor"), Actor.class);
            if (actorRepository.findByFirstName(newInformation.getFirst_name(), newInformation.getLast_name()) != null)
                return new HashMap<>(){{put("output","Name taken");}};
            existingActor.setFirst_name(newInformation.getFirst_name());
            existingActor.setLast_name(newInformation.getLast_name());
            existingActor.setLast_update();            
            actorRepository.save(existingActor);
            return new HashMap<>(){{put("output","Saved");}};
        }
    
        return new HashMap<>(){{put("output","User doesn't exist");}};
    }
    @DeleteMapping(path="/delete") 
    public @ResponseBody HashMap<String, String> deleteUser (@RequestBody HashMap<String, String> actor) {
        Actor delActor = actorRepository.findByFirstName(actor.get("first_name").toUpperCase(),actor.get("last_name").toUpperCase());
        if (delActor!=null) {
            filmActorRepo.DeleteByActorID(delActor.getActor_id());
            actorRepository.delete(delActor);
            return new HashMap<>(){{put("output","Deleted");}};
        }
        return new HashMap<>(){{put("output","User doesn't exist");}};
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Actor> getAllUsers() {
        return actorRepository.findAll();
    }
    @GetMapping(path="/get")
        public @ResponseBody HashMap<String, String> getUsers(@RequestParam String forename, @RequestParam String surname ) {
        Actor actor = actorRepository.findByFirstName(forename.toUpperCase(), surname.toUpperCase()); 
        if (actor!=null) {
            return new HashMap<String, String>() {{
                put("first_name", actor.getFirst_name());
                put("last_name" , actor.getLast_name());
            }};
        }
        return new HashMap<String, String>() {{
            put("outcome" , "User doesn't exist");
        }};
    }
}