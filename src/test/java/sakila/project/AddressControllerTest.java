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
import sakila.project.controllers.AddressController;
import sakila.project.repository.addressRepository;
import sakila.project.repository.cityRepository;
import sakila.project.entities.Address;
import sakila.project.entities.City;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(AddressController.class)
class AddressControllerTest {
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
                "\"postal_code\" : \"BA55 3AT\"," +
                "\"phone\" : \"999\"," +
                "\"city\" : \"Day City\"}}";
    @Test
    @DisplayName("Add New Address -- Success")
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
    @DisplayName("Add New Address -- Failed (Address already exist)")
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
    @DisplayName("Add New Address -- Failed (City doesn't exist)")
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
    private Address createAddress(String address,String address2,String district, Short cityId,String postalCode,String phone) {
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
    @DisplayName("Update Address -- Success")
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
    @Test
    @DisplayName("Update Address -- Fail (No City/First)")
    public void testUpdateAddressFailCityFirst() throws Exception {
        when(cityRepo.SearchCityName("Night City")).thenReturn(null);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/address/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATEJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
       String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(1)).SearchCityName(anyString());
        assertTrue(responseJson.contains("\"output\":\"City doesn't exist\""));
    }
    @Test
    @DisplayName("Update Address -- Fail (No City/Second)")
    public void testUpdateAddressFailCitySecond() throws Exception {
        when(cityRepo.SearchCityName("Night City")).thenReturn(new City((short) 1,
                "Night City", (short) 1,null));
        when(cityRepo.SearchCityName("Day City")).thenReturn(null);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/address/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UPDATEJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
       String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(2)).SearchCityName(anyString());
        assertTrue(responseJson.contains("\"output\":\"City doesn't exist\""));
    }
    @Test
    @DisplayName("Update Address -- Fail (Address Doesn't Exist)")
    public void testUpdateAddressFailedAddressNotExist() throws Exception {
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
                "999")).thenReturn(null);
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
        assertTrue(responseJson.contains("\"output\":\"Address doesn't exist\""));
    }
    @Test
    @DisplayName("Update Address -- Fail (Address Already Exist)")
    public void testUpdateAddressFailedAddressAlreadyExist() throws Exception {
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
                "999")).thenReturn(newAdd);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/address/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(UPDATEJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
       String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(2)).SearchCityName(anyString());
        verify(addressRepo, times(2)).SearchAddressAll(anyString(),
                anyString(),anyString(),anyShort(),anyString(),anyString());
        assertTrue(responseJson.contains("\"output\":\"Address already exist\""));
    }
    @Test
    @DisplayName("Get Address -- All Info")
    public void testGetAddressAll() throws Exception {
        when(cityRepo.SearchCityName("Night City")).thenReturn(new City((short) 1,
                "Night City", (short) 1,null));
        when(addressRepo.SearchAddressAll(
                "TOM",
                "DICK",
                "HARRY",
                (short) 1,
                "RICHARD",
                "BOB")).thenReturn(new Address());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/address/get")
                .contentType(MediaType.APPLICATION_JSON)
                .param("address", "Tom")
                .param("address2", "Dick")
                .param("district", "Harry")
                .param("city", "Night City")
                .param("postal_code", "Richard")
                .param("phone", "Bob"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(1)).SearchCityName(anyString());
        verify(addressRepo, times(1)).SearchAddressAll(anyString(),
                anyString(),anyString(),anyShort(),anyString(),anyString());
        assertFalse(responseJson.contains("\"output\":\"City doesn't exist\""));
    }
    @Test
    @DisplayName("Get Address -- Address2 Info")
    public void testGetAddressWithAddress2() throws Exception {
        when(cityRepo.SearchCityName("Night City")).thenReturn(new City((short) 1,
                "Night City", (short) 1,null));
        when(addressRepo.SearchAddressWithAddress2(
                "TOM",
                "DICK",
                "HARRY",
                (short) 1,
                "BOB")).thenReturn(new Address());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/address/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("address", "Tom")
                        .param("address2", "Dick")
                        .param("district", "Harry")
                        .param("city", "Night City")
                        .param("postal_code", "")
                        .param("phone", "Bob"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(1)).SearchCityName(anyString());
        verify(addressRepo, times(1)).SearchAddressWithAddress2(anyString(),
                anyString(),anyString(),anyShort(),anyString());
        assertFalse(responseJson.contains("\"output\":\"City doesn't exist\""));
    }
    @Test
    @DisplayName("Get Address -- Postcode Info")
    public void testGetAddressPostcode() throws Exception {
        when(cityRepo.SearchCityName("Night City")).thenReturn(new City((short) 1,
                "Night City", (short) 1,null));
        when(addressRepo.SearchAddressWithPostCode(
                "TOM",
                "HARRY",
                (short) 1,
                "RICHARD",
                "BOB")).thenReturn(new Address());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/address/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("address", "Tom")
                        .param("address2", "")
                        .param("district", "Harry")
                        .param("city", "Night City")
                        .param("postal_code", "Richard")
                        .param("phone", "Bob"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(1)).SearchCityName(anyString());
        verify(addressRepo, times(1)).SearchAddressWithPostCode(
                anyString(),anyString(),anyShort(),anyString(),anyString());
        assertFalse(responseJson.contains("\"output\":\"City doesn't exist\""));
    }
    @Test
    @DisplayName("Get Address -- Info")
    public void testGetAddress() throws Exception {
        when(cityRepo.SearchCityName("Night City")).thenReturn(new City((short) 1,
                "Night City", (short) 1,null));
        when(addressRepo.SearchAddress(
                "TOM",
                "HARRY",
                (short) 1,
                "BOB")).thenReturn(new Address());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/address/get")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("address", "Tom")
                        .param("address2", "")
                        .param("district", "Harry")
                        .param("city", "Night City")
                        .param("postal_code", "")
                        .param("phone", "Bob"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(1)).SearchCityName(anyString());
        verify(addressRepo, times(1)).SearchAddress(
                anyString(), anyString(),anyShort(),anyString());
        assertFalse(responseJson.contains("\"output\":\"City doesn't exist\""));
    }
    @Test
    @DisplayName("Get Address -- All")
    public void testGetAllAddress() throws Exception {
        when(cityRepo.findById((short) 1)).thenReturn(Optional.of(new City()));
        List<Address> addresses = new ArrayList<>();
        addresses.add(createAddress("TOM",
                "DICK",
                "HARRY",
                (short) 1,
                "RICHARD",
                "BOB"));
        when(addressRepo.findAll()).thenReturn(addresses);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/address/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        verify(cityRepo, times(1)).findById(anyShort());
        verify(addressRepo, times(1)).findAll();
        assertTrue(responseJson.contains("\"address\":\"TOM\",\"address2\":\"DICK\",\"phone\":\"BOB\",\"district\":\"HARRY\",\"address_id\":null,\"postal_code\":\"RICHARD\",\"city_id\":{\"city_id\":null,\"city\":null,\"country_id\":null,\"last_update\":null}"));
    }
}




