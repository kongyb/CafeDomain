package org.kong.cafe.inventory.service;

import org.kong.cafe.inventory.domain.InventoryRepository;
import org.kong.cafe.inventory.domain.model.inventory.Inventory;
import org.kong.cafe.inventory.domain.model.inventory.InventoryId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService{

    InventoryRepository inventoryRepository;

    @Override
    public List<Inventory> searchInventoryAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public List<Inventory> searchInventoryByStockId(Long stockId) {
        return inventoryRepository.findByStockId(stockId);
    }

    @Override
    public Inventory searchInventoryByInventoryId(Long inventoryId) {
        return inventoryRepository.findByInventoryId(inventoryId);
    }

    @Override
    public void restockInventory(Long stockId, int quantity, int purchasePrice) {
        InventoryId inventoryId = new InventoryId(stockId);
        Inventory inventory = new Inventory(inventoryId, quantity, purchasePrice);
        inventoryRepository.save(inventory);
        return;
    }

    @Override
    public void disposeInventory(Long inventoryId) {
        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId);
        inventory.dispose();
        inventoryRepository.save(inventory);
        return;
    }

    @Override
    public void useInventory(Long inventoryId, int count) {
        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId);
        inventory.use(count);
        inventoryRepository.save(inventory);
    }
}
