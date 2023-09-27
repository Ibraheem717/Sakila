package sakila.project.controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import sakila.project.repository.AddressRepository;
import sakila.project.repository.CityRepository;
import sakila.project.entities.Address;
import sakila.project.entities.City;

import static sakila.project.ProjectApplication.*;

@RestController 
@RequestMapping(path="/address") 
@CrossOrigin(origins = "http://localhost:3000")
public class AddressController {
    @Autowired 
    private AddressRepository addressRepo;
    @Autowired 
    private CityRepository cityRepo;
    @Autowired
    private ObjectMapper objectMapper;
    private final String ADDRESS = "address";
    private final String ADDRESS2 = "address2";
    private final String DISTRICT = "district";
    private final String POSTCODE = "postal_code";
    private final String PHONE = "phone";
    private final String CITY = "city";

    private String returnStringAddress(String extra) {
        return "Address " + extra;
    }
    private String returnStringCity() {
        return "City " + NONEXIST;
    }
    @PostMapping(path="/add") 
    public @ResponseBody Map<String, String> addNewAddress (@RequestBody Map<String, String> information) {
        String address = information.get(ADDRESS);
        String address2 = information.get(ADDRESS2);
        String district = information.get(DISTRICT);
        String postCode = information.get(POSTCODE);
        String phone = information.get(PHONE);
        Short cityId = this.checkIfCityExist(information.get(CITY));
        if (cityId==null)
            return returnValue(returnStringCity());
        if ( this.getAddressAll(address, address2, district, cityId, postCode, phone) !=null )
            return returnValue(returnStringAddress(EXIST));
        Address newAddress = objectMapper.convertValue(information, Address.class);
        newAddress.setLast_update();
        addressRepo.save(newAddress);
        return returnValue(SAVED);
    }
    @PutMapping(path="/update") 
    public @ResponseBody Map<String, String> updateAddress (@RequestBody Map<String, Object> information) {
        String address = (String) information.get(ADDRESS);
        String address2 = (String) information.get(ADDRESS2);
        String district = (String) information.get(DISTRICT);
        String postCode = (String) information.get(POSTCODE);
        String phone = (String) information.get(PHONE);
        Short cityId = this.checkIfCityExist((String)information.get(CITY));
        if (cityId==null)
            return returnValue(returnStringCity());
        Address newInformation = objectMapper.convertValue(information.get("newAddress"), Address.class);
        newInformation.setCity_id(this.checkIfCityExist((String)information.get("newCity")));
        if (newInformation.getCity_id()==null) {
            return returnValue(returnStringCity());
        }
        Address searchedAddress = this.getAddressAll(address, address2, district, cityId, postCode, phone);
        Address newAddress = this.getAddressAll(newInformation.getAddress(), newInformation.getAddress2(), newInformation.getDistrict(), newInformation.getCity_id(), newInformation.getPostal_code(), newInformation.getPhone());
        if (searchedAddress!=null && newAddress==null) {
            Short addressHolder = searchedAddress.getAddress_id();
            searchedAddress = newInformation;
            searchedAddress.setAddress_id(addressHolder);
            searchedAddress.setCity_id(newInformation.getCity_id());
            searchedAddress.setLast_update();
            addressRepo.save(searchedAddress);
            return returnValue(SAVED);
        }
        if (newAddress!=null)
            return returnValue(returnStringAddress(EXIST));
        return returnValue(returnStringAddress(NONEXIST));

    }
    @GetMapping(path="/all")
    public @ResponseBody List<HashMap<String, Object>> getAddress() {
        List<HashMap<String, Object>> allAddresses = new ArrayList<>();
        for (Address address : addressRepo.findAll()) {
            HashMap<String, Object> information = returnAddress(address);            
            allAddresses.add(information);     
        }
        return allAddresses;
    }
    private HashMap<String, Object> returnAddress(Address searchedAddress) {
        HashMap<String, Object> address = new HashMap<>();
        if (searchedAddress!=null) {
            address.put("address_id", searchedAddress.getAddress_id());
            address.put(ADDRESS, searchedAddress.getAddress());
            address.put(ADDRESS2, searchedAddress.getAddress2());
            address.put(DISTRICT, searchedAddress.getDistrict());
            address.put("city_id", (cityRepo.findById(searchedAddress.getCity_id())));
            address.put(POSTCODE, searchedAddress.getPostal_code());
            address.put(PHONE, searchedAddress.getPhone());
        }
        else
            address.put("output", "Address doesn't exist");
        return address;
    }
    private Short checkIfCityExist(String name) {
        City cityId = cityRepo.SearchCityName(name);
        if (cityId==null) 
            return null;
        return cityId.getCity_id();
    }
    private @ResponseBody Address getAddress(@RequestParam String address, String district, Short cityId, String phone) {
        return addressRepo.SearchAddress(address.toUpperCase(), district.toUpperCase(), cityId, phone) ;
    }
    private @ResponseBody Address getAddressWithAddress2(String address, String address2, String district, Short cityId, String phone) {
        return addressRepo.SearchAddressWithAddress2(address.toUpperCase(), address2.toUpperCase(),district.toUpperCase(), cityId, phone) ;
    }
    private @ResponseBody Address getAddressPostCode(String address, String district, Short cityId, String postCode,String phone) {
        return addressRepo.SearchAddressWithPostCode(address.toUpperCase(), district.toUpperCase() ,cityId, postCode.toUpperCase(), phone) ;
    }
    private @ResponseBody Address getAddressAll(String address, String address2, String district, Short cityId, String postCode, String phone) {
        if (address2.isEmpty() && postCode.isEmpty())
            return this.getAddress(address, district, cityId, phone);
        else if (address2.isEmpty())
            return this.getAddressPostCode(address, district, cityId, postCode, phone);
        else if (postCode.isEmpty())
            return this.getAddressWithAddress2(address, address2, district, cityId, phone);
        else
            return (addressRepo.SearchAddressAll(address.toUpperCase(), address2.toUpperCase(), district.toUpperCase(), cityId, postCode.toUpperCase(), phone)) ;
    }
    @GetMapping(path="/get")
    public @ResponseBody Map<String, ?> getJSONAddressAll(@RequestParam String address, @RequestParam String address2, @RequestParam String district, @RequestParam String city, @RequestParam String postCode, @RequestParam String phone) {
        Short cityId = checkIfCityExist(city);
        if (cityId!=null)
            return returnAddress(getAddressAll(address, address2, district, cityId, postCode, phone));
        return returnValue(returnStringCity());
    }
}