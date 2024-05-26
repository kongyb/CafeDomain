package org.kong.cafe.inventory.domain;

import org.kong.cafe.inventory.domain.model.inventory.Inventory;

import java.util.List;

public interface InventoryRepository {

    // 재고조회시 stock의 이름으로 찾을것인지, stock의 Id로 찾을것인지
    Inventory findByInventoryId(Long inventoryId);
    List<Inventory> findByStockId(Long StockId);
    List<Inventory> findByStockName(String stockName);
    List<Inventory> findAll();

    // 재고 추가
    public void save(Inventory inventory);
}
