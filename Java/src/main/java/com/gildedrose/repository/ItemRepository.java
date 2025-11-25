package com.gildedrose.repository;

import com.gildedrose.exception.ItemNotFoundException;
import com.gildedrose.model.Item;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepository {

    private List<Item> items = new ArrayList<>();

    public ItemRepository(List<Item> items) {
        this.items = items;
    }

    public boolean existsByName(Item item) {
        return items.stream().filter(it -> it.name.equals(item.name)).findAny().isPresent();
    }

    public List<Item> getAllItems(){
        return items;
    }

    public void save(Item item) {
        items.add(item);
    }

    public void createDummyItem() {
        Item item = new Item("Potion", 10, 20);
        items.add(item);
    }

    public void updateItem(Item item) {
        Item updatedItem = items.stream().filter(it -> it.name.equals(item.name)).findAny().orElseThrow(() -> new ItemNotFoundException("Item not found: " + item.name));
        updatedItem.sellIn = item.sellIn;
        updatedItem.quality = item.quality;
    }

    public void deleteItem(String name) {
        items.remove(getByName(name));
    }

    public Item getByName(String name) {
        Item singleItem = items.stream().filter(it ->it.name.equals(name)).findFirst()
            .orElseThrow(() -> new ItemNotFoundException("Item not found: " + name));
        return singleItem;
    }
}
