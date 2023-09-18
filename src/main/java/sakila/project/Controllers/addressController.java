package sakila.project.Controllers;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sakila.project.Repository.addressRepository;
import sakila.project.Repository.cityRepository;
import sakila.project.entities.Address;

@RestController 
@RequestMapping(path="/address") 
@CrossOrigin(origins = "http://localhost:3000")
public class addressController {
    @Autowired 
    private addressRepository addressRepo;
    @Autowired 
    private cityRepository cityRepo;
    @PostMapping(path="/add") 
    public @ResponseBody String addNewAddress (@RequestBody HashMap<String, String> infomation) {

        String address = infomation.get("address");
        String district = infomation.get("district");
        String phone = infomation.get("phone");
        Short city_id = cityRepo.SearchCityName(infomation.get("city")).getCity_id();


        if ( addressRepo.SearchAddress(address, district, city_id, phone) !=null)
            return "City already exists";
        Address newAddress = new Address(null, address, "bob", district, city_id, "bob", phone, null);
        newAddress.setLast_update();
        System.out.println(infomation.get("addressTwo"));
        System.out.println(newAddress);
        addressRepo.save(newAddress);
        return "Saved";
    }
    // @PutMapping(path="/update") 
    // public @ResponseBody String updateUser (@RequestBody HashMap<String, Object> infomation) {
    //     String postCode = (String) infomation.get("postalCode");
    //     String address = (String) infomation.get("address");
    //     String phoneNumber = (String) infomation.get("address");
    //     Address newInformation = (Address) infomation.get("newAddress");
    //     Address SerchedAddress = addressRepo.SearchAddress(postCode, address, phoneNumber);
    //     if (SerchedAddress!=null) {
    //         // actor = newInformation;
    //         // actor.setLast_update();
    //         // addressRepo.save(actor);
    //         return "Changed";
    //     }
    //     return "User doesn't exist";
    // }
    // @DeleteMapping(path="/delete") 
    // public @ResponseBody String deleteUser (@RequestBody Address actor) {
    //     Address delActor = addressRepo.findByFirstName(actor.getFirst_name(),actor.getLast_name());
    //     if (delActor!=null) {
    //         addressRepo.delete(delActor);
    //         return "Deleted";
    //     }
    //     return "User doesn't exist";
    // }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Address> getAllUsers() {
        return addressRepo.findAll();
    }
    // @GetMapping(path="/all")
    // public @ResponseBody String getUsers(@RequestParam String forename, @RequestParam String surname ) {
    //     Address actor = addressRepo.findByFirstName(forename, surname); 
    //     if (actor!=null)
    //         return actor.toString();
    //     return "User doesn't exist";
    // }
}