package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.Store;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class StoreTest {

    private Store store;

    @BeforeEach
    void setUp() {
        store = new Store();
    }

    @Test
    void testStoreId() {
        store.setStore_id((byte) 1);
        assertEquals((byte) 1, store.getStore_id());
    }

    @Test
    void testManagerStoreId() {
        store.setManager_store_id((byte) 2);
        assertEquals((byte) 2, store.getManager_store_id());
    }

    @Test
    void testAddressId() {
        store.setAddress_id((short) 3);
        assertEquals((short) 3, store.getAddress_id());
    }

    @Test
    void testLastUpdate() {
        LocalDateTime currentTime = LocalDateTime.now();
        store.setLast_update(Timestamp.valueOf(currentTime));
        assertNotNull(store.getLast_update());
    }
}
