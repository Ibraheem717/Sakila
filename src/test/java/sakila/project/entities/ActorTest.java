package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ActorTest {

    private Actor actor;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this);

        // Create an instance of the Actor class
        actor = new Actor();
    }

    @Test
    void testActorId() {
        // Set and retrieve actor_id
        actor.setActor_id((short) 1);
        assertEquals((short)1, actor.getActor_id());
    }

    @Test
    void testFirstName() {
        // Set and retrieve first_name
        actor.setFirst_name("John");
        assertEquals("John", actor.getFirst_name());
    }

    @Test
    void testLastName() {
        // Set and retrieve last_name
        actor.setLast_name("Doe");
        assertEquals("Doe", actor.getLast_name());
    }

    @Test
    void testLastUpdate() {
        // Ensure last_update is set when setLast_update() is called
        actor.setLast_update();
        // Assert that last_update is not null (timestamp should be set)
        assertNotNull(actor.getLast_update());
    }
}
