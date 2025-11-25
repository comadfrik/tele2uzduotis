package com.gildedrose.controller;

import com.gildedrose.model.Item;
import com.gildedrose.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/items")
public class GildedRoseController {

    private ItemService itemService;

    public GildedRoseController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("dummy")
    public void createDummyItem(){
        itemService.createDummy();
    }

    @GetMapping("")
    public List<Item> getAllItems(){
       return itemService.getAllItems();
    }

    @GetMapping("{name}")
    public ResponseEntity<Item> getByName(@PathVariable String name){
        return ResponseEntity.ok(itemService.getByName(name));
    }

    @PostMapping("")
    public ResponseEntity<Item> addItem(@RequestBody Item item){
       return ResponseEntity.ok(itemService.addItem(item));
    }

    @PutMapping("")
    public ResponseEntity<Item> updateItem(@RequestBody Item item){
        return ResponseEntity.ok(itemService.updateItem(item));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<?> deleteItem(@PathVariable String name){
        itemService.deleteItem(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/nextday")
    public ResponseEntity<?> nextDay(){
        itemService.nextDay();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
