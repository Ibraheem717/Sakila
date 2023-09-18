package sakila.project.Controllers;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sakila.project.Repository.countryRepository;
import sakila.project.entities.Country;

@RestController 
@RequestMapping(path="/country") 
@CrossOrigin(origins = "http://localhost:3000")
public class countryController {
    @Autowired 
    private countryRepository CountryRepository;
    @PostMapping(path="/add") 
    public @ResponseBody String addNewAddress (@RequestBody Country infomation) {
        Country n = infomation;
        n.setLast_update();
        if (CountryRepository.SearchCountry(infomation.getCountry().toUpperCase())!=null)
            return "Country already exists";
        CountryRepository.save(n);
        return "Saved";
    }
    @PutMapping(path="/update") 
    public @ResponseBody String updateCountry (@RequestBody HashMap<String, String> infomation) {
        String country = infomation.get("newCountry");
        Country SerchedCountry = CountryRepository.SearchCountry(infomation.get("country"));
        Country newCountry = CountryRepository.SearchCountry(country);
        if (SerchedCountry!=null && newCountry!=null) {
            SerchedCountry.setLast_update();
            SerchedCountry.setCountry(country);
            CountryRepository.save(SerchedCountry);
            return "Changed";
        }
        return "User doesn't exist";
    }
    @DeleteMapping(path="/delete") 
    public @ResponseBody String deleteUser (@RequestBody String givenCountry) {
        Country delCountry = CountryRepository.SearchCountry(givenCountry.toUpperCase());
        if (delCountry!=null) {
            CountryRepository.delete(delCountry);
            return "Deleted";
        }
        return "User doesn't exist";
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Country> getAllUsers() {
        return CountryRepository.findAll();
    }
    @GetMapping(path="/get")
    public @ResponseBody String getUsers(@RequestParam String givenCountry ) {
        Country country = CountryRepository.SearchCountry(givenCountry.toUpperCase()); 
        if (country!=null)
            return country.toString();
        return "User doesn't exist";
    }
}