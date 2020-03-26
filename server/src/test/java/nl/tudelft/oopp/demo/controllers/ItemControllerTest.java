package nl.tudelft.oopp.demo.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.demo.entities.Item;
import nl.tudelft.oopp.demo.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

@DataJpaTest
public class ItemControllerTest {
    private Item i1;
    private Item i2;
    private Item i3;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemController itemController;

    /**
     * Sets up the items, so that they can be used later in the tests.
     */
    @BeforeEach
    public void save() {
        i1 = new Item("projector");
        i2 = new Item("blackboard");
        i3 = new Item("computer");
    }

    @Test
    public void testLoadController() {
        assertThat(itemController).isNotNull();
    }

    @Test
    public void testGetAllItems() {
        List<Item> expectedList = new ArrayList<>(List.of(i1, i2, i3));
        when(itemRepository.findAll()).thenReturn(expectedList);
        List<Item> actualList = itemController.getAllItems();

        assertEquals(expectedList, actualList);
    }

    @Test
    public void testGetItemById() {
        Optional<Item> optionalItem = Optional.of(i1);
        ResponseEntity<Item> entity = ResponseEntity.of(optionalItem);

        when(itemRepository.findById(i1.getId())).thenReturn(optionalItem);
        assertEquals(entity, itemController.getItemById(i1.getId()));
    }

    @Test
    public void testGetItemByName() {
        Optional<Item> optionalItem = Optional.of(i2);
        ResponseEntity<Item> entity = ResponseEntity.of(optionalItem);

        when(itemRepository.findByName(i2.getName())).thenReturn(optionalItem);
        assertEquals(entity, itemController.getItemByName(i2.getName()));
    }

    @Test
    public void testCreateItem() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        Item i4 = new Item("speakers");
        Optional<Item> optionalItem = Optional.of(i4);
        ResponseEntity<Item> entity = ResponseEntity.of(optionalItem);

        when(itemRepository.save(i4)).thenReturn(i4);

        assertEquals(i4,
            itemController.createItem(i4, uriComponentsBuilder).getBody());
    }

    @Test
    public void testUpdateItem() {
        Item newItem = new Item("speakers");
        UriComponentsBuilder u1 = UriComponentsBuilder.newInstance();
        Optional<Item> optionalItem = Optional.of(i1);
        ResponseEntity<Item> entity = ResponseEntity.of(optionalItem);
        Optional<Item> newI = Optional.of(newItem);
        ResponseEntity<Item> newE = ResponseEntity.of(newI);

        when(itemRepository.save(newItem)).thenReturn(newItem);
        when(itemRepository.findById(i1.getId())).thenReturn(optionalItem);

        assertEquals(newE.getBody(),
            itemController.updateItem(i1.getId(), newItem).getBody());
    }


    @Test
    public void testDeleteItem() {
        List<Item> actualList = new ArrayList<Item>(List.of(i1, i3));
        List<Item> expectedList = new ArrayList<Item>(List.of(i1, i2, i3));

        Optional<Item> optionalItem = Optional.of(i2);
        ResponseEntity<Item> itemResponseEntity = ResponseEntity.of(optionalItem);

        itemController.deleteItem(i2.getId());

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                actualList.remove(1);
                return null;
            }
        }).when(itemRepository).deleteById(i2.getId());

        when(itemRepository.findAll()).thenReturn(expectedList);
        assertEquals(expectedList, itemController.getAllItems());
    }
}
