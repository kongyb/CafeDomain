package org.kong.cafe.inventory.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kong.cafe.inventory.domain.InventoryRepository;
import org.kong.cafe.inventory.domain.model.inventory.Inventory;
import org.kong.cafe.inventory.infra.MemoryInventoryRepository;

import java.util.List;

class InventoryServiceTest {

    InventoryService inventoryService;
    InventoryRepository inventoryRepository;

    @BeforeEach
    public void init() {
        inventoryRepository = new MemoryInventoryRepository();
        inventoryService = new InventoryServiceImpl(inventoryRepository);
    }

    // 재고충전
    @Test
    public void restockTest() {
        inventoryService.restockInventory(1L, 3, 1000);
        inventoryService.restockInventory(1L, 3, 1000);

        List<Inventory> inventoryList = inventoryRepository.findAll();
        Assertions.assertThat(inventoryList.size()).isEqualTo(2);
    }

    @Test
    public void disposeInventoryTest() {
        inventoryService.restockInventory(1L, 3, 1000);
        inventoryService.restockInventory(2L, 5, 3000);

        List<Inventory> inventoryList = inventoryRepository.findAll();
        for (Inventory inventory : inventoryList) {
            Assertions.assertThat(inventory.isDisposed()).isEqualTo(false);
            inventoryService.disposeInventory(inventory.getInventoryId());
            Assertions.assertThat(inventory.isDisposed()).isEqualTo(true);
        }
    }

    @Test
    public void useInventoryTest() {
        inventoryService.restockInventory(1L, 3, 1000);
        inventoryService.restockInventory(2L, 5, 3000);

        List<Inventory> inventoryList = inventoryRepository.findAll();
        for (Inventory inventory : inventoryList) {
            Assertions.assertThat(inventory.getLeftQuantity()).isEqualTo(inventory.getOriginalQuantity());
            inventoryService.useInventory(inventory.getStockId(), inventory.getLeftQuantity());
            Assertions.assertThat(inventory.getLeftQuantity()).isEqualTo(0);
        }
    }


}