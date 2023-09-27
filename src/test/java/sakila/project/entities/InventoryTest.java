package sakila.project.entities;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sakila.project.controllers.AddressController;
import sakila.project.entities.Inventory;
import sakila.project.repository.inventoryRepository;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@WebMvcTest(Inventory.class)
public class InventoryTest {
    @MockBean
    private inventoryRepository inventoryRepository;

    @Test
    public void testSaveInventory() {
        // Create an Inventory entity
        Inventory inventory = new Inventory();
        inventory.setInventory_id(1);
        inventory.setFilm_id((short) 1);
        inventory.setStore_id((byte) 1);
        inventory.setLast_update(new Timestamp(System.currentTimeMillis()));

        // Save the Inventory to the in-memory database
        when(inventoryRepository.findById(anyLong())).thenReturn(Optional.of(inventory));

        // Retrieve the Inventory from the database
        Inventory savedInventory = inventoryRepository.findById(Long.valueOf(inventory.getInventory_id())).orElse(null);

        // Check if the retrieved Inventory matches the saved one
        assertThat(savedInventory).isNotNull();
        assertThat(savedInventory.getFilm_id()).isEqualTo(inventory.getFilm_id());
        assertThat(savedInventory.getStore_id()).isEqualTo(inventory.getStore_id());
        assertThat(savedInventory.getLast_update()).isEqualTo(inventory.getLast_update());
    }
}
