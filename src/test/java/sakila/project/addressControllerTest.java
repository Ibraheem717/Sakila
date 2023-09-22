package sakila.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.bind.annotation.RequestParam;
import sakila.project.Controllers.addressController;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(addressController.class)
class addressControllerTest {
    @MockBean
    private addressController addressCon;
    private HashMap<String , Object> setAddressObject(String address, String address2, String district, String city, String postal_code, String phone) {
        HashMap<String , Object> getOutput = new HashMap<>();
        getOutput.put("address", address);
        getOutput.put("address2", address2);
        getOutput.put("district", district);
        getOutput.put("city", city);
        getOutput.put("postcode", postal_code);
        getOutput.put("phone", phone);
        return getOutput;
    }

    private HashMap<String , String> setAddressString(String address, String address2, String district, String city, String postal_code, String phone) {
        HashMap<String , String> getOutput = new HashMap<>();
        getOutput.put("address", address);
        getOutput.put("address2", address2);
        getOutput.put("district", district);
        getOutput.put("city", city);
        getOutput.put("postcode", postal_code);
        getOutput.put("phone", phone);
        return getOutput;
    }


    @Test
    public void testGetUser() throws Exception {
        HashMap<String , Object> getOutput = setAddressObject("47 MySakila Drive","", "Alberta","Lethbridge","","");

        when(addressCon.getJSONAddressAll("47 MySakila Drive","","Alberta","Lethbridge","",""))
                .thenReturn(getOutput);
    }

    @Test
    public void testAddUser() {
        HashMap<String , String> getOutputString =
                setAddressString("Temp", "", "Essex", "house", "", "000");
        HashMap<String , Object> getOutputObject =
                setAddressObject("Temp", "", "Essex", "house", "", "000");
        HashMap<String , String> output = new HashMap<>();
        output.put("output", "Saved");
        HashMap<String , String> delOutput = new HashMap<>();
        delOutput.put("name", "Doe");

        when(addressCon.addNewAddress(getOutputString))
                .thenReturn(output);

        output.put("output", "Address already exist");
        when(addressCon.addNewAddress(getOutputString))
                .thenReturn(output);

        when(addressCon.getJSONAddressAll("Temp", "", "Essex", "house", "", "000"))
                .thenReturn(getOutputObject);

        HashMap<String , String> newOutput = new HashMap<>();
        newOutput.put("address", "address");
        newOutput.put("address2", "address2");
        newOutput.put("district", "district");
        newOutput.put("city", "house");
        newOutput.put("postcode", "postal_code");
        newOutput.put("phone", "phone");

        HashMap<String , Object> changeOutput =
                setAddressObject("Temp", "", "Essex", "house", "", "000");
        changeOutput.put("newAddress", newOutput);
        output.put("output", "Saved");
        when(addressCon.updateAddress(changeOutput))
                .thenReturn(output);
        output.put("output", "Address already exist");
        when(addressCon.updateAddress(changeOutput))
                .thenReturn(output);

    }
}