package sakila.project.Controllers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import sakila.project.Repository.addressRepository;
import sakila.project.Repository.cityRepository;
import sakila.project.Repository.customerRepository;
import sakila.project.Repository.inventoryRepository;
import sakila.project.Repository.paymentRepository;
import sakila.project.Repository.rentalRepository;
import sakila.project.Repository.staffRepository;
import sakila.project.Repository.storeRepository;
import sakila.project.entities.Address;
import sakila.project.entities.City;

@RestController 
@RequestMapping(path="/address") 
@CrossOrigin(origins = "http://localhost:3000")
public class addressController {
    @Autowired 
    private addressRepository addressRepo;
    @Autowired 
    private cityRepository cityRepo;
    @Autowired 
    private storeRepository storeRepo;
    @Autowired 
    private staffRepository staffRepo;
    @Autowired 
    private customerRepository customerRepo;
    @Autowired 
    private paymentRepository paymentRepo;
    @Autowired 
    private rentalRepository rentalRepo;
    @Autowired 
    private inventoryRepository inventoryRepo;
    @Autowired
    private ObjectMapper objectMapper;
    @PostMapping(path="/add") 
    public @ResponseBody HashMap<String, String> addNewAddress (@RequestBody HashMap<String, String> infomation) {
        String address = infomation.get("address");
        String address2 = infomation.get("address2");
        String district = infomation.get("district");
        String postal_code = infomation.get("postal_code");
        String phone = infomation.get("phone");
        Short city_id = cityRepo.SearchCityName(infomation.get("city")).getCity_id();
        if ( this.getAddressAll(address, address2, district, city_id, postal_code, phone) !=null ) 
            return new HashMap<>(){{put("output", "Address already exist"); }};
        Address newAddress = new Address(null, address, address2, district, city_id, postal_code, phone, null);
        newAddress.setLast_update();
        addressRepo.save(newAddress);
        return new HashMap<>(){{put("output", "Saved"); }};
    }
    @PutMapping(path="/update") 
    public @ResponseBody HashMap<String, String> updateUser (@RequestBody HashMap<String, Object> infomation) {
        System.out.println("start");

        String address = (String) infomation.get("address");
        String address2 = (String) infomation.get("address2");
        String district = (String) infomation.get("district");
        String postal_code = (String) infomation.get("postal_code");
        String phone = (String) infomation.get("phone");
        Short city_id = this.checkIfCityExist((String)infomation.get("city"));
        if (city_id==null)
            return new HashMap<>(){{put("output", "City doesn't exist"); }};
        System.out.println("john");
        Address newInformation = objectMapper.convertValue(infomation.get("newAddress"), Address.class);
        newInformation.setCity_id(this.checkIfCityExist((String)infomation.get("newCity")));
        if (newInformation.getCity_id()==null) {
            return new HashMap<>(){{put("output", "City doesn't exist"); }};
        }
        System.out.println(newInformation);
        Address SearchedAddress = this.getAddressAll(address, address2, district, city_id, postal_code, phone);
        Address newAddress = this.getAddressAll(newInformation.getAddress(), newInformation.getAddress2(), newInformation.getDistrict(), newInformation.getCity_id(), newInformation.getPostal_code(), newInformation.getPhone());
        System.out.println("bob");
        System.out.println(SearchedAddress);
        if (SearchedAddress!=null && newAddress==null) {
            Short addressHolder = SearchedAddress.getAddress_id();
            SearchedAddress = newInformation;
            SearchedAddress.setAddress_id(addressHolder);
            SearchedAddress.setCity_id(city_id);
            SearchedAddress.setLast_update();
            addressRepo.save(SearchedAddress);
            return new HashMap<>(){{put("output", "Changed"); }};
        }
        return new HashMap<>(){{put("output", "Address already exist"); }};
    }
    @DeleteMapping(path="/delete") 
    public @ResponseBody HashMap<String, String> deleteAddress (@RequestBody HashMap<String, Object> information) {
        Address address = objectMapper.convertValue(information, Address.class);
        address.setCity_id(this.checkIfCityExist((String) information.get("city")));
        if (address.getCity_id() == null)
            return new HashMap<>(){{put("output", "City doesn't exist"); }};
        Address delAdress = this.getAddressAll(address.getAddress(), address.getAddress2(), address.getDistrict(), address.getCity_id(), address.getPostal_code(), address.getPhone());
        if (delAdress!=null) {
            Short addressID = delAdress.getAddress_id();
            System.out.println(address);

            for (Byte i : staffRepo.FindStaffIDByAddressID(addressID)) {
                storeRepo.DeleteByStaffID(i);
            }


            for (Byte i : storeRepo.FindStoreIDByAddressID(addressID)) {
                for (Short j : customerRepo.FindCustomerIDByStoreID(i)) {
                    paymentRepo.DeleteByCustomerID(j);
                }
                customerRepo.DeleteByStoreID(i);
                for (Byte j : staffRepo.FindStaffIDByStoreID(i)) {
                    paymentRepo.DeleteByStaffID(j);
                    rentalRepo.DeleteByStaffID(j);
                }
                for (Integer j : inventoryRepo.FindInventoryIDByStoreID(i)) {
                    rentalRepo.DeleteByInventoryID(j);
                }
                inventoryRepo.DeleteByStoreID(i);
                staffRepo.DeleteByStoreID(i);
            }

            
            System.out.println(addressID);
            staffRepo.DeleteByAddressID(addressID);
            storeRepo.DeleteByAddressID(addressID);
            customerRepo.DeleteByAddressID(addressID);
            addressRepo.delete(delAdress);
        return new HashMap<>(){{put("output", "Deleted"); }};
        }
        return new HashMap<>(){{put("output", "Address doesn't exist"); }};
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
        if (searchedAddress!=null)
            return new HashMap<>(){{
                put("address_id", searchedAddress.getAddress_id());
                put("address", searchedAddress.getAddress());
                put("address2", searchedAddress.getAddress2());
                put("district", searchedAddress.getDistrict());
                put("city_id", (cityRepo.findById(searchedAddress.getCity_id())) );
                put("postal_code", searchedAddress.getPostal_code());
                put("phone", searchedAddress.getPhone());
            }};
        return new HashMap<>(){{put("output", "Address doesn't exist"); }};
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
        if (address2.equals("") && postal_code.equals("")) 
            return this.getAddress(address, district, city_id, phone);
        else if (address2.equals(""))
            return this.getAddressPostCode(address, district, city_id, postal_code, phone);
        else if (postal_code.equals(""))
            return this.getAddressWithAddress2(address, address2, district, city_id, phone);
        else
            return (addressRepo.SearchAddressAll(address.toUpperCase(), address2.toUpperCase(), district.toUpperCase(), city_id, postal_code.toUpperCase(), phone)) ;
    }
    @GetMapping(path="/get")
    public @ResponseBody HashMap<String, Object> getJSONAddressAll(@RequestParam String address, @RequestParam String address2, @RequestParam String district, @RequestParam String city, @RequestParam String postal_code, @RequestParam String phone) {
        Short city_id = checkIfCityExist(city);
        if (city_id!=null)
            return returnAddress(getAddressAll(address, address2, district, city_id, postal_code, phone));
        return new HashMap<>(){{put("output", "City doesn't exist"); }};
    }
}