package org.kong.cafe.inventory.infra;

import org.kong.cafe.inventory.domain.InventoryRepository;
import org.kong.cafe.inventory.domain.model.inventory.Inventory;
import org.kong.cafe.inventory.domain.model.inventory.InventoryId;
import org.kong.cafe.inventory.domain.model.stock.Stock;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class MemoryInventoryRepository implements InventoryRepository{

    private final HashMap<InventoryId, Inventory> inventoryMap;
    private final HashMap<Long, Stock> stockMap;

    public MemoryInventoryRepository() {
        this.inventoryMap = new HashMap<>();
        this.stockMap = new HashMap<>();
    }

    @Override
    public Inventory findByInventoryId(InventoryId inventoryId) {
        return inventoryMap.getOrDefault(inventoryId, null);
    }

    @Override
    public List<Inventory> findByStockId(Long stockId) {
        List<Inventory> inventoryList = new ArrayList<>();

        for (Inventory inventory : inventoryMap.values()) {
            if (stockId.equals(inventory.getStockId())) {
                inventoryList.add(inventory);
            }
        }

        return inventoryList;
    }

    @Override
    public List<Inventory> findByStockName(String stockName) {
        return null;
    }

    @Override
    public List<Inventory> findAll() {
        return new ArrayList<>(inventoryMap.values());
    }

    @Override
    public InventoryId save(Inventory inventory) {
        inventoryMap.put(inventory.getInventoryId(), inventory);
        return inventory.getInventoryId();
    }
}
