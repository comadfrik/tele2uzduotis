package com.gildedrose.service;

import com.gildedrose.exception.ItemAlreadyExistsException;
import com.gildedrose.model.Item;
import com.gildedrose.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private QualityUpdate qualityUpdate;

    @InjectMocks
    private ItemService itemService;

    @Test
    void testUpdateItemWhenItemExist() {
        Item item = new Item("thing", 10, 20);

        when(itemRepository.existsByName(item)).thenReturn(true);

        Item result = itemService.updateItem(item);

        verify(itemRepository).updateItem(item);
        assertSame(item, result);
    }

    @Test
    void testUpdateItemWhenDoesNotExist() {
        Item item = new Item("thing", 10, 20);

        when(itemRepository.existsByName(item)).thenReturn(false);

        Item result = itemService.updateItem(item);

        verify(itemRepository, never()).updateItem(any());
        assertSame(item, result);
    }


    @Test
    void testGetAllItems() {
        List<Item> repoItems = Arrays.asList(
            new Item("thing1", 1, 1),
            new Item("thing2", 2, 2)
        );
        when(itemRepository.getAllItems()).thenReturn(repoItems);

        List<Item> result = itemService.getAllItems();

        assertEquals(repoItems, result);
        verify(itemRepository).getAllItems();
    }

    @Test
    void testAddItem() {
        Item item = new Item("thing", 5, 10);

        when(itemRepository.existsByName(item)).thenReturn(false);

        Item result = itemService.addItem(item);

        verify(itemRepository).save(item);
        assertSame(item, result);
    }

    @Test
    void testAddItemWhenAlreadyExistsThrowsItemAlreadyExistsException() {
        Item item = new Item("thing", 5, 10);

        when(itemRepository.existsByName(item)).thenReturn(true);

        assertThrows(ItemAlreadyExistsException.class,
            () -> itemService.addItem(item));

        verify(itemRepository, never()).save(any());
    }


    @Test
    void testDeleteItem() {
        String name = "thing";

        itemService.deleteItem(name);

        verify(itemRepository).deleteItem(name);
    }


    @Test
    void testGetByName() {
        String name = "thing";
        Item item = new Item(name, 5, 10);

        when(itemRepository.getByName(name)).thenReturn(item);

        Item result = itemService.getByName(name);

        assertSame(item, result);
        verify(itemRepository).getByName(name);
    }


    @Test
    void testNextDay() {
        Item item1 = new Item("thing1", 1, 1);
        Item item2 = new Item("thing2", 2, 2);
        List<Item> items = Arrays.asList(item1, item2);

        when(itemRepository.getAllItems()).thenReturn(items);

        itemService.nextDay();

        verify(itemRepository).getAllItems();
        verify(qualityUpdate).updateItem(item1);
        verify(qualityUpdate).updateItem(item2);
        verifyNoMoreInteractions(qualityUpdate);
    }
}
