package org.kong.cafe.inventory.service;

import org.kong.cafe.inventory.domain.InventoryRepository;
import org.kong.cafe.inventory.domain.model.inventory.Inventory;
import org.kong.cafe.inventory.domain.model.inventory.InventoryId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService{

    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Inventory> searchInventoryAll() {
        return inventoryRepository.findAll();
    }

    @Override
    public List<Inventory> searchInventoryByStockId(Long stockId) {
        return inventoryRepository.findByStockId(stockId);
    }

    @Override
    public Inventory searchInventoryByInventoryId(InventoryId inventoryId) {
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
    public void disposeInventory(InventoryId inventoryId) {
        Inventory inventory = inventoryRepository.findByInventoryId(inventoryId);
        inventory.dispose();
        inventoryRepository.save(inventory);
        return;
    }

    @Override
    public void useInventory(Long stockId, int count) {
        List<Inventory> inventoryList = inventoryRepository.findByStockId(stockId);

        // 현재 재고가 충분한지 확인
        int total = 0;
        for (Inventory inventory : inventoryList) {
            if (inventory.isDisposed()) {
                continue;
            }
            total += inventory.getLeftQuantity();
            if (total >= count) {
                break;
            }
        }

        if (total < count) {
            // 에러 생성??
            return;
        }

        // 재고사용
        for (Inventory inventory : inventoryList) {
            if (inventory.isDisposed()) {
                continue;
            }
            int currCount = Math.min(count, inventory.getLeftQuantity());
            inventory.use(currCount);
            inventoryRepository.save(inventory);
            count -= currCount;
        }


    }
}
