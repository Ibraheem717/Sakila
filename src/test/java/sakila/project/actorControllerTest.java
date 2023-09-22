package sakila.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sakila.project.Controllers.actorController;

import java.util.HashMap;

import static org.mockito.Mockito.*;
@WebMvcTest(actorController.class)
public class actorControllerTest {
    @MockBean
    private actorController actorCon;

    @Test
    public void testGetUser() throws Exception {
        HashMap<String , String> getOutput = new HashMap<>();
        getOutput.put("first_name", "THORA");
        getOutput.put("last_name" , "TEMPLE");
        when(actorCon.getUsers("THORA", "TEMPLE"))
                .thenReturn(getOutput);
    }

    @Test
    public void testAddUser() {
        HashMap<String , String> getOutput = new HashMap<>();
        getOutput.put("first_name", "Doctor");
        getOutput.put("last_name" , "Who");
        HashMap<String , String> output = new HashMap<>();
        output.put("output", "Saved");
        HashMap<String , Object> changeOutput = new HashMap<>();
        getOutput.put("forename", "Doctor");
        getOutput.put("surname" , "Who");
        getOutput.put("first_name", "John");
        getOutput.put("last_name" , "Doe");
        HashMap<String , String> delOutput = new HashMap<>();
        delOutput.put("first_name", "John");
        delOutput.put("last_name" , "Doe");

        when(actorCon.addNewUser(getOutput))
                .thenReturn(output);

        when(actorCon.getUsers("Doctor", "Who"))
                .thenReturn(getOutput);

        when(actorCon.updateUser(changeOutput))
                .thenReturn(getOutput);

        output.put("output", "Deleted");
        when(actorCon.deleteUser(delOutput))
                .thenReturn(getOutput);
    }
}
