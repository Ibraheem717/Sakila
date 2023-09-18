package sakila.project.Controllers;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sakila.project.Repository.cityRepository;
import sakila.project.Repository.countryRepository;
import sakila.project.entities.City;

@RestController 
@RequestMapping(path="/city") 
@CrossOrigin(origins = "http://localhost:3000")
public class cityController {
    @Autowired 
    private cityRepository cityRepo;
    @Autowired
    private countryRepository countryRepo;
    @PostMapping(path="/add") 
    public @ResponseBody String addNewCity (@RequestBody HashMap<String, String> infomation) {
        String country = infomation.get("country");
        Short country_id = countryRepo.SearchCountry(country).getCountry_id();
        String city = infomation.get("city");
        if (cityRepo.SearchCity(city.toUpperCase(), country_id)!=null)
            return "City already exists";
        City newCity = new City(null, city, country_id, null);
        newCity.setLast_update(null);
        cityRepo.save(newCity);
        return "Saved";
    }
    // @PutMapping(path="/update") 
    // public @ResponseBody String updateCountry (@RequestBody HashMap<String, Object> infomation) {
    //     String country = (String) infomation.get("country");
    //     System.out.println(country);
    //     System.out.println((City)infomation.get("newCountry"));
    //     City newInformation = new City(null, country, null) infomation.get("newCountry");
    //     City SerchedCountry = CityRepository.SearchCountry(country);
    //     if (SerchedCountry!=null) {
    //         newInformation.setLast_update();
    //         CityRepository.save(newInformation);
    //         return "Changed";
    //     }
    //     return "User doesn't exist";
    // }
    // @DeleteMapping(path="/delete") 
    // public @ResponseBody String deleteUser (@RequestBody City actor) {
    //     City delActor = CityRepository.findByFirstName(actor.getFirst_name(),actor.getLast_name());
    //     if (delActor!=null) {
    //         CityRepository.delete(delActor);
    //         return "Deleted";
    //     }
    //     return "User doesn't exist";
    // }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<City> getAllUsers() {
        return cityRepo.findAll();
    }
    // @GetMapping(path="/all")
    // public @ResponseBody String getUsers(@RequestParam String forename, @RequestParam String surname ) {
    //     City actor = CityRepository.findByFirstName(forename, surname); 
    //     if (actor!=null)
    //         return actor.toString();
    //     return "User doesn't exist";
    // }
}