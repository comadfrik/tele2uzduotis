package com.gildedrose.service;

import com.gildedrose.exception.ItemAlreadyExistsException;
import com.gildedrose.exception.ItemInvalidException;
import com.gildedrose.model.Item;
import com.gildedrose.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private ItemRepository itemRepository;
    @Autowired
    private QualityUpdate qualityUpdate;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    public Item updateItem(Item item) {
        if(itemRepository.existsByName(item)){
            itemRepository.updateItem(item);
        }
        return item;
    }

    public List<Item> getAllItems() {
        return itemRepository.getAllItems();
    }

    public Item addItem(Item item) {
        if(itemRepository.existsByName(item)){
            throw new ItemAlreadyExistsException("Item already exists: " + item.name);
        }
        if (item == null || item.name == null || item.name.isBlank()) {
            throw new ItemInvalidException("Item name is required");
        }
        if (item.sellIn < 0) {
            throw new ItemInvalidException("sellIn must be >= 0");
        }
        if (item.quality < 0 || item.quality > 50) {
            throw new ItemInvalidException("Quality must be between 0 and 50");
        }
        itemRepository.save(item);
        return item;
    }

    public void createDummy() {
        itemRepository.createDummyItem();
    }

    public void deleteItem(String name) {
        itemRepository.deleteItem(name);
    }

    public Item getByName(String name) {
        return itemRepository.getByName(name);
    }

    public void nextDay() {
        List<Item> allItems = itemRepository.getAllItems();
        for(Item item : allItems){
            qualityUpdate.updateItem(item);
        }

    }
}
