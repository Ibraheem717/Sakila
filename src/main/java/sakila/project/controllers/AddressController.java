package sakila.project.controllers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import sakila.project.repository.addressRepository;
import sakila.project.repository.cityRepository;
import sakila.project.entities.Address;
import sakila.project.entities.City;

import static sakila.project.ProjectApplication.*;

@RestController 
@RequestMapping(path="/address") 
@CrossOrigin(origins = "http://localhost:3000")
public class AddressController {
    @Autowired 
    private addressRepository addressRepo;
    @Autowired 
    private cityRepository cityRepo;
    @Autowired
    private ObjectMapper objectMapper;
    private String returnStringAddress(String extra) {
        return "Address " + extra;
    }
    private String returnStringCity() {
        return "City " + NONEXIST;
    }
    @PostMapping(path="/add") 
    public @ResponseBody Map<String, String> addNewAddress (@RequestBody HashMap<String, String> information) {
        String address = information.get("address");
        String address2 = information.get("address2");
        String district = information.get("district");
        String postal_code = information.get("postal_code");
        String phone = information.get("phone");
        Short city_id = this.checkIfCityExist(information.get("city"));
        if (city_id==null)
            return returnValue(returnStringCity());
        if ( this.getAddressAll(address, address2, district, city_id, postal_code, phone) !=null )
            return returnValue(returnStringAddress(EXIST));
        Address newAddress = objectMapper.convertValue(information, Address.class);
        newAddress.setLast_update();
        addressRepo.save(newAddress);
        return returnValue(SAVED);
    }
    @PutMapping(path="/update") 
    public @ResponseBody Map<String, String> updateAddress (@RequestBody HashMap<String, Object> information) {
        String address = (String) information.get("address");
        String address2 = (String) information.get("address2");
        String district = (String) information.get("district");
        String postal_code = (String) information.get("postal_code");
        String phone = (String) information.get("phone");
        Short city_id = this.checkIfCityExist((String)information.get("city"));
        if (city_id==null)
            return returnValue(returnStringCity());
        Address newInformation = objectMapper.convertValue(information.get("newAddress"), Address.class);
        newInformation.setCity_id(this.checkIfCityExist((String)information.get("newCity")));
        if (newInformation.getCity_id()==null) {
            return returnValue(returnStringCity());
        }
        Address SearchedAddress = this.getAddressAll(address, address2, district, city_id, postal_code, phone);
        Address newAddress = this.getAddressAll(newInformation.getAddress(), newInformation.getAddress2(), newInformation.getDistrict(), newInformation.getCity_id(), newInformation.getPostal_code(), newInformation.getPhone());
        if (SearchedAddress!=null && newAddress==null) {
            Short addressHolder = SearchedAddress.getAddress_id();
            SearchedAddress = newInformation;
            SearchedAddress.setAddress_id(addressHolder);
            SearchedAddress.setCity_id(newInformation.getCity_id());
            SearchedAddress.setLast_update();
            addressRepo.save(SearchedAddress);
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
            address.put("address", searchedAddress.getAddress());
            address.put("address2", searchedAddress.getAddress2());
            address.put("district", searchedAddress.getDistrict());
            address.put("city_id", (cityRepo.findById(searchedAddress.getCity_id())));
            address.put("postal_code", searchedAddress.getPostal_code());
            address.put("phone", searchedAddress.getPhone());
        }
        else
            address.put("output", "Address doesn't exist");
        return address;
    }
    private Short checkIfCityExist(String name) {
        City city_id = cityRepo.SearchCityName(name);
        if (city_id==null) 
            return null;
        return city_id.getCity_id();
    }
    private @ResponseBody Address getAddress(@RequestParam String address, String district, Short city_id, String phone) {
        return addressRepo.SearchAddress(address.toUpperCase(), district.toUpperCase(), city_id, phone) ;
    }
    private @ResponseBody Address getAddressWithAddress2(String address, String address2, String district, Short city_id, String phone) {
        return addressRepo.SearchAddressWithAddress2(address.toUpperCase(), address2.toUpperCase(),district.toUpperCase(), city_id, phone) ;
    }
    private @ResponseBody Address getAddressPostCode(String address, String district, Short city_id, String postal_code,String phone) {
        return addressRepo.SearchAddressWithPostCode(address.toUpperCase(), district.toUpperCase() ,city_id, postal_code.toUpperCase(), phone) ;
    }
    private @ResponseBody Address getAddressAll(String address, String address2, String district, Short city_id, String postal_code, String phone) {
        if (address2.isEmpty() && postal_code.isEmpty())
            return this.getAddress(address, district, city_id, phone);
        else if (address2.isEmpty())
            return this.getAddressPostCode(address, district, city_id, postal_code, phone);
        else if (postal_code.isEmpty())
            return this.getAddressWithAddress2(address, address2, district, city_id, phone);
        else
            return (addressRepo.SearchAddressAll(address.toUpperCase(), address2.toUpperCase(), district.toUpperCase(), city_id, postal_code.toUpperCase(), phone)) ;
    }
    @GetMapping(path="/get")
    public @ResponseBody Map<String, ?> getJSONAddressAll(@RequestParam String address, @RequestParam String address2, @RequestParam String district, @RequestParam String city, @RequestParam String postal_code, @RequestParam String phone) {
        Short city_id = checkIfCityExist(city);
        if (city_id!=null)
            return returnAddress(getAddressAll(address, address2, district, city_id, postal_code, phone));
        return returnValue(returnStringCity());
    }
}