package com.stockmarket.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CommodityTest {

    @Test
    void testCreateCommodity() {
        Commodity commodity = new Commodity("GOLD", "Złoto", 100.0);
        assertEquals("GOLD", commodity.getSymbol());
        assertEquals("Złoto", commodity.getName());
        assertEquals(100.0, commodity.getBasePrice(), 0.001);
    }

    @Test
    void testCalculateMarketValue() {
        Commodity commodity = new Commodity("CRUDE", "Ropa", 100.0);
        // 100 * 10 = 1000
        double value = commodity.calculateMarketValue(10);
        assertEquals(1000.0, value, 0.001);
    }

    @Test
    void testCalculateTransactionCost() {
        Commodity commodity = new Commodity("SILVER", "Srebro", 50.0);
        // (50 * 1) + (2.0 * 1) = 50 + 2 = 52
        double cost = commodity.calculateTransactionCost(1);
        assertEquals(52.0, cost, 0.001);
    }

    @Test
    void testTransactionCostIncludesStorage() {
        Commodity commodity = new Commodity("OIL", "Ropa", 80.0);
        // Koszt = (80 * 100) + (2.0 * 100) = 8000 + 200 = 8200
        double cost = commodity.calculateTransactionCost(100);
        assertEquals(8200.0, cost, 0.001);
    }

    @Test
    void testStorageCostIncreasesWithQuantity() {
        Commodity commodity = new Commodity("COPPER", "Miedź", 100.0);
        double cost5 = commodity.calculateTransactionCost(5); // 500 + 10 = 510
        double cost10 = commodity.calculateTransactionCost(10); // 1000 + 20 = 1020

        assertEquals(510.0, cost5, 0.001);
        assertEquals(1020.0, cost10, 0.001);
    }

    @Test
    void testSymbolCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Commodity(null, "Złoto", 100.0)
        );
    }

    @Test
    void testSymbolCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
            new Commodity("", "Złoto", 100.0)
        );
    }

    @Test
    void testNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Commodity("GOLD", null, 100.0)
        );
    }

    @Test
    void testNameCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
            new Commodity("GOLD", "", 100.0)
        );
    }

    @Test
    void testPriceCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () ->
            new Commodity("GOLD", "Złoto", -10.0)
        );
    }

    @Test
    void testPriceCannotBeZero() {
        assertThrows(IllegalArgumentException.class, () ->
            new Commodity("GOLD", "Złoto", 0.0)
        );
    }
}
