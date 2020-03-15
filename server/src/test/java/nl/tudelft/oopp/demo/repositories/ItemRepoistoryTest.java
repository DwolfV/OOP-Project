package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ItemRepoistoryTest {

    private Item i1;
    private Item i2;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    public void save(){
        i1 = new Item("projector");
        i2 = new Item("blackboard");

        itemRepository.save(i1);
        itemRepository.save(i2);
    }

    @Test
    public void testLoadRepository() {
        assertThat(itemRepository).isNotNull();
    }

    @Test
    public void testFindByName() {
        assertEquals(Optional.of(i1), itemRepository.findByName("projector"));
    }
}
