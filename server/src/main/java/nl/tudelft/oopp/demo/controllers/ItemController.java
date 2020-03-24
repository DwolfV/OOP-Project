package nl.tudelft.oopp.demo.controllers;

import java.util.List;
import javax.validation.Valid;
import nl.tudelft.oopp.demo.entities.Item;
import nl.tudelft.oopp.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/item")
public class ItemController {

    @Autowired
    ItemRepository itemRepository;

    /**
     * GET endpoint to retrieve a list of all items.
     *
     * @return a list of all items
     */
    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    /**
     * GET endpoint to retrieve a an item by its id.
     *
     * @param id - the id of the item that the user is looking for
     * @return an item
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable long id) {
        return itemRepository.findById(id).map(item -> ResponseEntity.ok(item)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET endpoint to retrieve a an item by its name.
     * @param name - the name of the item that we are looking for
     * @return an Item
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Item> getItemByName(@PathVariable String name) {
        return itemRepository.findByName(name).map(item -> ResponseEntity.ok(item)
        ).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * POST endpoint to add a new item to the database.
     *
     * @param item - the item that needs to be added
     * @param uri - the path through which we retrieve the new item
     * @return the newly created item
     */
    @PostMapping(value = "/add", consumes = {"application/json"})
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item, UriComponentsBuilder uri) {
        itemRepository.save(item);
        UriComponents uriComponents = uri.path("/item/id/{id}").buildAndExpand(item.getId());
        return ResponseEntity.created(uriComponents.toUri()).body(item);
    }

    /**
     * PUT endpoint to update the details of an item
     *
     * @param id - the id of the item that is going to be modified
     * @param newItem - the item with the updated details
     * @return - the 200 status http code and the updated object if the update succeeded, 404 if the item was not found
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable long id,
                                                   @RequestBody Item newItem) {
        return itemRepository.findById(id).map(item -> {
            item.setName(newItem.getName());

            return new ResponseEntity<>(itemRepository.save(item), HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE endpoint to delete an Item from the database.
     *
     * @param id - the id of the Item that is to be deleted
     * @return a message and the 200 status code if the delete was successful, 404 if the item was not found
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteItem(@PathVariable long id) {
        return itemRepository.findById(id).map(item -> {
            itemRepository.delete(item);
            return new ResponseEntity("The item has been deleted successfully", HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
