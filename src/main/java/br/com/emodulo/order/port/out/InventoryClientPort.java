package br.com.emodulo.order.port.out;

public interface InventoryClientPort {
    void decreaseStock(String productId, int quantity);
}
