package org.kong.cafe.inventory.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kong.cafe.inventory.domain.InventoryRepository;
import org.kong.cafe.inventory.domain.model.inventory.Inventory;
import org.kong.cafe.inventory.domain.model.inventory.InventoryId;
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

        List<Inventory> inventoryList = inventoryService.searchInventoryAll();
        Assertions.assertThat(inventoryList.size()).isEqualTo(2);
    }

    @Test
    public void disposeInventoryTest() {
        inventoryService.restockInventory(1L, 3, 1000);
        inventoryService.restockInventory(2L, 5, 3000);

        List<Inventory> inventoryList = inventoryService.searchInventoryAll();
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

        List<Inventory> inventoryList = inventoryService.searchInventoryAll();
        for (Inventory inventory : inventoryList) {
            Assertions.assertThat(inventory.getLeftQuantity()).isEqualTo(inventory.getOriginalQuantity());
            inventoryService.useInventory(inventory.getStockId(), inventory.getLeftQuantity());
            Assertions.assertThat(inventory.getLeftQuantity()).isEqualTo(0);
        }
    }

    @Test
    public void useInventoryTest2() {
        InventoryId inventoryId1 = inventoryService.restockInventory(1L, 2, 1000);
        InventoryId inventoryId2 = inventoryService.restockInventory(1L, 1, 1000);

        // 1번 재고를 폐기한후 2개를 사용해야할때, 재고의 남은 수량이 2개보다 적으면 사용하지 않음.
        inventoryService.disposeInventory(inventoryId1);
        inventoryService.useInventory(1L, 2);

        Inventory inventory = inventoryService.searchInventoryByInventoryId(inventoryId2);
        Assertions.assertThat(inventory.getLeftQuantity()).isEqualTo(1);

    }


}