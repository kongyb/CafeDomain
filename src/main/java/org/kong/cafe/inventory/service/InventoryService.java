package org.kong.cafe.inventory.service;

import org.kong.cafe.inventory.domain.model.inventory.Inventory;
import org.kong.cafe.inventory.domain.model.inventory.InventoryId;

import java.util.List;

public interface InventoryService {

    /*
    * 전체 재고 조회
    * */
    public List<Inventory> searchInventoryAll();
    /*
    * 재료의 Id로 재고 조회
    *  */
    public List<Inventory> searchInventoryByStockId(Long stockId);
    /*
    * 재고의 Id로 특정 재고 조회
    * */
    public Inventory searchInventoryByInventoryId(InventoryId inventoryId);

    // 재고 충전
    // 어떤 재료를 얼마에 몇개를 샀는지
    public InventoryId restockInventory(Long stockId, int count, int purchasePrice);
    // 재고 폐기
    public InventoryId disposeInventory(InventoryId inventoryId);
    // 재고 사용
    public void useInventory(Long stockId, int count);


}
