package sakila.project.Controllers;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import sakila.project.Repository.actorRepository;
import sakila.project.entities.Actor;

@RestController 
@RequestMapping(path="/actor") 
@CrossOrigin(origins = "http://localhost:3000")
public class actorController {
    @Autowired 
    private actorRepository actorRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @PostMapping(path="/add") 
    public @ResponseBody HashMap<String, String> addNewUser (@RequestBody Actor infomation) {
        Actor n = infomation;
        n.setLast_update();
        if (actorRepository.findByFirstName(infomation.getFirst_name().toUpperCase(), infomation.getLast_name().toUpperCase()) != null) {
            return new HashMap<>(){{put("output","User already exist");}};
        }
        actorRepository.save(n);
        return new HashMap<>(){{put("output","Saved");}};
    }
    @PutMapping(path = "/update")
    public @ResponseBody String updateUser(@RequestBody HashMap<String, Object> information) {
        String forename = (String) information.get("forename");
        String surname = (String) information.get("surname");
        Actor existingActor = actorRepository.findByFirstName(forename.toUpperCase(), surname.toUpperCase());
    
        if (existingActor != null) {
            Actor newInformation = objectMapper.convertValue(information.get("actor"), Actor.class);
            existingActor.setFirst_name(newInformation.getFirst_name());
            existingActor.setLast_name(newInformation.getLast_name());
            existingActor.setLast_update();            
            actorRepository.save(existingActor);
            return "Changed";
        }
    
        return "User doesn't exist";
    }
    
    @DeleteMapping(path="/delete") 
    public @ResponseBody String deleteUser (@RequestBody Actor actor) {
        Actor delActor = actorRepository.findByFirstName(actor.getFirst_name().toUpperCase(),actor.getLast_name().toUpperCase());
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