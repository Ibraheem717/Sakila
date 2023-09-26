package sakila.project;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.RequestParam;
import sakila.project.Controllers.addressController;
import sakila.project.Repository.addressRepository;
import sakila.project.Repository.cityRepository;
import sakila.project.entities.Actor;
import sakila.project.entities.Address;
import sakila.project.entities.City;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(addressController.class)
class addressControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private addressRepository addressRepo;
    @MockBean
    private cityRepository cityRepo;
    private final String JSON = "{" +
            "\"address\" : \"Graveyard\"," +
            "\"address2\" : \"BrokeTown\"," +
            "\"district\" : \"Giza\"," +
            "\"postal_code\" : \"BA55 3AT \"," +
            "\"phone\" : \"999\"," +
            "\"city\" : \"Night City\"" +
            "}";
    private final String UPDATEJSON = "{" +
            "\"address\" : \"Graveyard\"," +
            "\"address2\" : \"BrokeTown\"," +
            "\"district\" : \"Giza\"," +
            "\"postal_code\" : \"BA55 3AT\"," +
            "\"phone\" : \"999\"," +
            "\"city\" : \"Night City\"," +
            "\"newCity\" : \"Day City\"," +
            "\"newAddress\" : {" +
                "\"address\" : \"Scrapyard\"," +
                "\"address2\" : \"RichTown\"," +
                "\"district\" : \"Pyramid\"," +
                "\"postal_code\" : \"BA55 3AT \"," +
                "\"phone\" : \"999\"," +
                "\"city\" : \"Day City\"}}";
    @Test
    @DisplayName("Add New User -- Success")
    public void testAddNewAddressSuccess() throws Exception {
        when(cityRepo.SearchCityName(anyString())).thenReturn(new City((short) 1,
                "Dead", (short) 1,null));
        when(addressRepo.SearchAddressAll(anyString(),anyString(),anyString(),anyShort(),
                anyString(),anyString())).thenReturn(null);
        when(addressRepo.save(any(Address.class))).thenReturn(new Address());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/address/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(1)).SearchCityName(anyString());
        verify(addressRepo, times(1)).SearchAddressAll(anyString(),
                anyString(),anyString(),anyShort(),anyString(),anyString());
        verify(addressRepo, times(1)).save(any(Address.class));
        assertTrue(responseJson.contains("\"output\":\"Saved\""));
    }
    @Test
    @DisplayName("Add New User -- Failed (Address already exist)")
    public void testAddNewAddressFailOne() throws Exception {
        when(cityRepo.SearchCityName(anyString())).thenReturn(new City((short) 1,
                "Dead", (short) 1,null));
        when(addressRepo.SearchAddressAll(anyString(),anyString(),anyString(),anyShort(),
                anyString(),anyString())).thenReturn(new Address());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/address/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(1)).SearchCityName(anyString());
        verify(addressRepo, times(1)).SearchAddressAll(anyString(),
                anyString(),anyString(),anyShort(),anyString(),anyString());
        assertTrue(responseJson.contains("\"output\":\"Address already exist\""));
    }
    @Test
    @DisplayName("Add New User -- Failed (City doesn't exist)")
    public void testAddNewAddressFailTwo() throws Exception {
        when(cityRepo.SearchCityName(anyString())).thenReturn(null);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/address/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(1)).SearchCityName(anyString());
        assertTrue(responseJson.contains("\"output\":\"City doesn't exist\""));
    }
    private Address createAddress(String address, String address2, String district, Short cityId, String postalCode, String phone) {
        Address newAddress = new Address();
        newAddress.setAddress(address);
        newAddress.setAddress2(address2);
        newAddress.setDistrict(district);
        newAddress.setCity_id(cityId);
        newAddress.setPostal_code(postalCode);
        newAddress.setPhone(phone);
        return newAddress;
    }
    @Test
    @DisplayName("Update User -- Success")
    public void testUpdateAddressSuccess() throws Exception {
        Address original = createAddress("Graveyard",
                "BrokeTown",
                "Giza",
                (short) 1,
                "BA55 3AT",
                "999");
        Address newAdd = createAddress("Scrapyard",
                "RichTown",
                "Pyramid",
                (short) 2,
                "BA55 3AT",
                "999");
        when(cityRepo.SearchCityName("Night City")).thenReturn(new City((short) 1,
                "Night City", (short) 1,null));
        when(cityRepo.SearchCityName("Day City")).thenReturn(new City((short) 2,
                "Day City", (short) 2,null));
        when(addressRepo.SearchAddressAll(
                "GRAVEYARD",
                "BROKETOWN",
                "GIZA",
                (short) 1,
                "BA55 3AT",
                "999")).thenReturn(original);
        when(addressRepo.SearchAddressAll(
                "SCRAPYARD",
                "RICHTOWN",
                "PYRAMID",
                (short) 2,
                "BA55 3AT",
               "999")).thenReturn(null);


        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/address/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(UPDATEJSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(2)).SearchCityName(anyString());
        verify(addressRepo, times(2)).SearchAddressAll(anyString(),
                anyString(),anyString(),anyShort(),anyString(),anyString());
        assertTrue(responseJson.contains("\"output\":\"Saved\""));
    }


//    @Test
//    public void testGetUser() throws Exception {
//        HashMap<String , Object> getOutput = setAddressObject("47 MySakila Drive","", "Alberta","Lethbridge","","");
//
//        when(addressCon.getJSONAddressAll("47 MySakila Drive","","Alberta","Lethbridge","",""))
//                .thenReturn(getOutput);
//    }

//    @Test
//    public void testAddUser() {
//        HashMap<String , String> getOutputString =
//                setAddressString("Temp", "", "Essex", "house", "", "000");
//        HashMap<String , Object> getOutputObject =
//                setAddressObject("Temp", "", "Essex", "house", "", "000");
//        HashMap<String , String> output = new HashMap<>();
//        output.put("output", "Saved");
//        HashMap<String , String> delOutput = new HashMap<>();
//        delOutput.put("name", "Doe");
//
//        when(addressCon.addNewAddress(getOutputString))
//                .thenReturn(output);
//
//        output.put("output", "Address already exist");
//        when(addressCon.addNewAddress(getOutputString))
//                .thenReturn(output);
//
//        when(addressCon.getJSONAddressAll("Temp", "", "Essex", "house", "", "000"))
//                .thenReturn(getOutputObject);
//
//        HashMap<String , String> newOutput = new HashMap<>();
//        newOutput.put("address", "address");
//        newOutput.put("address2", "address2");
//        newOutput.put("district", "district");
//        newOutput.put("city", "house");
//        newOutput.put("postcode", "postal_code");
//        newOutput.put("phone", "phone");
//
//        HashMap<String , Object> changeOutput =
//                setAddressObject("Temp", "", "Essex", "house", "", "000");
//        changeOutput.put("newAddress", newOutput);
//        output.put("output", "Saved");
//        when(addressCon.updateAddress(changeOutput))
//                .thenReturn(output);
//        output.put("output", "Address already exist");
//        when(addressCon.updateAddress(changeOutput))
//                .thenReturn(output);
//
//    }
}

//    private final String JSON = "{" +
//            "\"address\" : \"Graveyard\"," +
//            "\"address2\" : \"BrokeTown\"," +
//            "\"district\" : \"Corpo\"," +
//            ",\"postal_code\" : \"BA55 3AT \"," +
//            "\"phone\" : \"999\"," +
//            "\"city\" : \"Night City\"" +
//            "}";
//    private final String UPDATEJSON = "{" +
//            "\"address\" : \"Graveyard\"," +
//            "\"address2\" : \"BrokeTown\"," +
//            "\"district\" : \"Corpo\"" +"," +
//            "\"postal_code\" : \"BA55 3AT \"," +
//            "\"phone\" : \"999\"," +
//            "\"city\" : \"Night City\"," +
//            "\"newCity\" : \"Nomad\"," +
//            "\"newAddress\" : {" +
//            "\"address\" : \"ScrapYard\"," +
//            "\"address2\" : \"RichTown\"," +
//            "\"district\" : \"Nomad\"" +"," +
//            "\"postal_code\" : \"BE47 L87 \"," +
//            "\"phone\" : \"911\"," +
//            "} ";



