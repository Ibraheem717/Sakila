package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ActorTest {

    private Actor actor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        actor = new Actor();
    }

    @Test
    void testActorId() {
        actor.setActor_id((short) 1);
        assertEquals((short)1, actor.getActor_id());
    }

    @Test
    void testFirstName() {
        actor.setFirst_name("John");
        assertEquals("John", actor.getFirst_name());
    }

    @Test
    void testLastName() {
        actor.setLast_name("Doe");
        assertEquals("Doe", actor.getLast_name());
    }

    @Test
    void testLastUpdate() {
        actor.setLast_update();
        assertNotNull(actor.getLast_update());
    }
}
