package com.stockmarket.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ShareTest {

    @Test
    void testCreateShare() {
        Share share = new Share("AAPL", "Apple", 100.0);
        assertEquals("AAPL", share.getSymbol());
        assertEquals("Apple", share.getName());
        assertEquals(100.0, share.getBasePrice(), 0.001);
    }

    @Test
    void testCalculateMarketValue() {
        Share share = new Share("MSFT", "Microsoft", 100.0);
        // 100 * 10 = 1000
        double value = share.calculateMarketValue(10);
        assertEquals(1000.0, value, 0.001);
    }

    @Test
    void testCalculateTransactionCost() {
        Share share = new Share("GOOGL", "Google", 200.0);
        // (200 * 1) + 5 = 205
        double cost = share.calculateTransactionCost(1);
        assertEquals(205.0, cost, 0.001);
    }

    @Test
    void testTransactionCostIncludesFee() {
        Share share = new Share("TSLA", "Tesla", 500.0);
        // (500 * 100) + 5 = 50005
        double cost = share.calculateTransactionCost(100);
        assertEquals(50005.0, cost, 0.001);
    }

    @Test
    void testSymbolCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Share(null, "Apple", 100.0)
        );
    }

    @Test
    void testSymbolCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
            new Share("", "Apple", 100.0)
        );
    }

    @Test
    void testNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Share("AAPL", null, 100.0)
        );
    }

    @Test
    void testNameCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
            new Share("AAPL", "", 100.0)
        );
    }

    @Test
    void testPriceCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () ->
            new Share("AAPL", "Apple", -50.0)
        );
    }

    @Test
    void testPriceCannotBeZero() {
        assertThrows(IllegalArgumentException.class, () ->
            new Share("AAPL", "Apple", 0.0)
        );
    }
}
