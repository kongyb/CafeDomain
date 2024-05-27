package org.kong.cafe.inventory.domain.model.inventory;


public class Inventory {

    // 식별자
    private InventoryId inventoryId;
    // 최초입고수량
    private int originalQuantity;
    // 남은 수량
    private int leftQuantity;
    // 폐기 여부
    private boolean isDisposed;
    // 구매가
    private int purchasePrice;

    public Inventory(InventoryId inventoryId, int quantity, int purchasePrice) {
        this.inventoryId = inventoryId;
        this.originalQuantity = quantity;
        this.leftQuantity = quantity;
        this.isDisposed = false;
        this.purchasePrice = purchasePrice;
    }

    public int getLeftQuantity() {
        return leftQuantity;
    }

    public int getOriginalQuantity() {
        return originalQuantity;
    }

    public boolean isDisposed() {
        return isDisposed;
    }

    public Long getStockId() {
        return inventoryId == null ? null : inventoryId.getStockId();
    }

    public InventoryId getInventoryId() {
        return inventoryId;
    }

    public void use (int count) {
//        if (this.leftQuantity < count) {
//            throw new Exception();
//        }
        this.leftQuantity -= count;
    }

    public void dispose () {
        this.isDisposed = true;
    }
}
