package com.gildedrose.service;
import com.gildedrose.model.Item;
import org.springframework.stereotype.Component;

@Component
public class QualityUpdate {

    public void updateItem(Item item){

        if (item.name.equals("Sulfuras")) {
            return;
        }

        item.sellIn--;

        switch (item.name) {

            case "Aged Brie":
                increaseQuality(item);
                if (item.sellIn < 0) increaseQuality(item);
                break;

            case "Backstage passes":
                if (item.sellIn < 0) {
                    item.quality = 0;
                } else if (item.sellIn < 5) {
                    increaseQuality(item, 3);
                } else if (item.sellIn < 10) {
                    increaseQuality(item, 2);
                } else {
                    increaseQuality(item);
                }
                break;

            case "Conjured":
                decreaseQuality(item, 2);
                if (item.sellIn < 0) decreaseQuality(item, 2);
                break;

            default:
                decreaseQuality(item);
                if (item.sellIn < 0) decreaseQuality(item);
                break;
        }
    }

    private void increaseQuality(Item item) {
        if (item.quality < 50) item.quality++;
    }

    private void increaseQuality(Item item, int amount) {
        for (int i = 0; i < amount; i++) increaseQuality(item);
    }

    private void decreaseQuality(Item item) {
        if (item.quality > 0) item.quality--;
    }

    private void decreaseQuality(Item item, int amount) {
        for (int i = 0; i < amount; i++) decreaseQuality(item);
    }
}
