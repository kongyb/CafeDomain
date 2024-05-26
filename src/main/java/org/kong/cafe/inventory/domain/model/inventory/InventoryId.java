package org.kong.cafe.inventory.domain.model.inventory;

import java.util.Date;

public class InventoryId {

    private Long stockId;
    private Date inDate;

    public InventoryId(Long stockId) {
        this.stockId = stockId;
        this.inDate = new Date();
    }

    public Long getStockId() {
        return stockId;
    }

    public Date getInDate() {
        return inDate;
    }
}
