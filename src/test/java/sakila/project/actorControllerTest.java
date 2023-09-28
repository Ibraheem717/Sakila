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
import sakila.project.controllers.ActorController;
import sakila.project.repository.FilmActorRepository;
import sakila.project.repository.ActorRepository;
import sakila.project.entities.Actor;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
@WebMvcTest(controllers = ActorController.class)
class actorControllerTest {
    @MockBean
    private ActorRepository actorRepository;
    @MockBean
    private FilmActorRepository filmActorRepo;
    @Autowired
    private MockMvc mvc;
    private final String JSON = "{" +
            "\"first_name\":\"Bob\"," +
            "\"last_name\":\"Builder\"" +
            "}";
    private final String NEWJSON = "{\"forename\":\"Bob\",\"surname\":\"Builder\"," +
            "\"actor\":{\"first_name\":\"Ben\",\"last_name\":\"Door\"}}";

    @Test
    @DisplayName("Add New Actor -- Success")
     void testAddNewActorSuccess() throws Exception {
        when(actorRepository.save(any(Actor.class))).thenReturn(new Actor()); // Simulate a successful save
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/actor/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(actorRepository, times(1)).save(any(Actor.class));
        assertTrue(responseJSON.contains("\"output\":\"Saved\""));
    }
    @Test
    @DisplayName("Add New Actor -- Fail")
     void testAddNewActorFailure() throws Exception {
        when(actorRepository.findByFirstName(anyString(), anyString())).thenReturn(new Actor()); // Actor already exists
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/actor/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        System.out.println("Response JSON: " + responseJSON);
        verify(actorRepository, times(1)).findByFirstName(anyString(), anyString());
        assertTrue(responseJSON.contains("\"output\":\"Actor already exist\""));
    }
    @Test
    @DisplayName("Update Actor -- Success")
     void testUpdateActorSuccess() throws Exception {
        when(actorRepository.findByFirstName(anyString(), anyString())).thenReturn(new Actor()); // Actor exists but with different name
        when(actorRepository.findByFirstName("Ben", "Door")).thenReturn(null); // New name is available
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/actor/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEWJSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(actorRepository, times(2)).findByFirstName(anyString(), anyString());
        verify(actorRepository, times(1)).save(any(Actor.class));
        assertTrue(responseJSON.contains("\"output\":\"Saved\""));
    }

    @Test
    @DisplayName("Update Actor -- Fail (Name Taken)")
     void testUpdateActorNameTaken() throws Exception {
        when(actorRepository.findByFirstName(anyString(), anyString())).thenReturn(new Actor());
        when(actorRepository.findByFirstName("Ben", "Door")).thenReturn(new Actor());
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/actor/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(NEWJSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andReturn();

        // Get the response JSON and assert it
        String responseJSON = result.getResponse().getContentAsString();
        verify(actorRepository, times(2)).findByFirstName(anyString(), anyString());
        assertTrue(responseJSON.contains("\"output\":\"Actor already exist\""));
    }
    @Test
    @DisplayName("Update Actor -- Fail (Actor doesn't exist)")
     void testUpdateActorNotFound() throws Exception {
        when(actorRepository.findByFirstName("Rick", "John")).thenReturn(null);
        String failedNEWJSON = "{\"forename\":\"Rick\",\"surname\":\"John\"," +
                "\"actor\":{\"first_name\":\"Ben\",\"last_name\":\"Door\"}}";
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/actor/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(failedNEWJSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(actorRepository, times(1)).findByFirstName(anyString(), anyString());
        assertTrue(responseJSON.contains("\"output\":\"Actor doesn't exist\""));
    }
    @Test
    @DisplayName("Get Actor -- Success")
     void testGetActorsSuccess() throws Exception {
        Actor user = new Actor();
        user.setFirst_name("Lizzy");
        user.setLast_name("Muguire");
        when(actorRepository.findByFirstName("LIZZY", "MUGUIRE")).thenReturn(user);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/actor/get")
            .param("forename", "Lizzy")
            .param("surname", "Muguire"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(actorRepository, times(1)).findByFirstName("LIZZY", "MUGUIRE");
        assertTrue(responseJSON.contains("\"first_name\":\"Lizzy\""));
        assertTrue(responseJSON.contains("\"last_name\":\"Muguire\""));
    }
    @Test
    @DisplayName("Get Actor -- Failed")
     void testGetActorsActorNotFound() throws Exception {
        when(actorRepository.findByFirstName("Lizzy", "Muguire")).thenReturn(null);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/actor/get")
            .param("forename", "Lizzy")
            .param("surname", "Muguire"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        System.out.println("Response JSON: " + responseJSON);
        verify(actorRepository, times(1)).findByFirstName("LIZZY", "MUGUIRE");
        assertTrue(responseJSON.contains("\"output\":\"Actor doesn't exist\""));
    }
    private Actor createActorWithID() {
        Actor act = createActor("The", "Joker");
        act.setActor_id((short) 1);
        act.setLast_update();
        return act;
    }
    @Test
    @DisplayName("Delete Actor -- Success")
     void testDeleteActorSuccess() throws Exception {
        Actor act = createActorWithID();
        when(actorRepository.findByFirstName("THE", "JOKER")).thenReturn(act);
        doNothing().when(filmActorRepo).DeleteByActorID((short) 1);
        doNothing().when(actorRepository).delete(any(Actor.class));
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/actor/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"first_name\":\"The\",\"last_name\":\"Joker\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(actorRepository, times(1)).findByFirstName("THE", "JOKER");
        verify(filmActorRepo, times(1)).DeleteByActorID((short) 1);
        verify(actorRepository, times(1)).delete(any(Actor.class));
        assertTrue(responseJSON.contains("\"output\":\"Deleted\""));
    }
    @Test
    @DisplayName("Delete Actor -- Failed")
     void testDeleteActorNotFound() throws Exception {
        when(actorRepository.findByFirstName("The", "Joker")).thenReturn(null);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.delete("/actor/delete")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"first_name\":\"The\",\"last_name\":\"Joker\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(actorRepository, times(1)).findByFirstName("THE", "JOKER");
        assertTrue(responseJSON.contains("\"output\":\"Actor doesn't exist\""));
    }
    private Actor createActor(String firstName, String lastName) {
        Actor user = new Actor();
        user.setFirst_name(firstName);
        user.setLast_name(lastName);
        return user;
    }
    @Test
    @DisplayName("Get All Actors")
     void testGetAllActors() throws Exception {
        List<Actor> users = new ArrayList<>();
        users.add(createActor("Bob", "The"));
        users.add(createActor("Builder", "Can"));
        users.add(createActor("We", "Fix"));
        users.add(createActor("It", "No"));
        when(actorRepository.findAll()).thenReturn(users);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/actor/all")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        String responseJSON = result.getResponse().getContentAsString();
        verify(actorRepository, times(1)).findAll();
        assertTrue(responseJSON.contains("\"first_name\":\"Bob\""));
        assertTrue(responseJSON.contains("\"last_name\":\"The\""));
        assertTrue(responseJSON.contains("\"first_name\":\"Builder\""));
        assertTrue(responseJSON.contains("\"last_name\":\"Can\""));
        assertTrue(responseJSON.contains("\"first_name\":\"We\""));
        assertTrue(responseJSON.contains("\"last_name\":\"Fix\""));
        assertTrue(responseJSON.contains("\"first_name\":\"It\""));
        assertTrue(responseJSON.contains("\"last_name\":\"No\""));
    }
}
