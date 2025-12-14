package com.stockmarket.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CurrencyTest {

    @Test
    void testCreateCurrency() {
        Currency currency = new Currency("EUR", "Euro", 100.0);
        assertEquals("EUR", currency.getSymbol());
        assertEquals("Euro", currency.getName());
        assertEquals(100.0, currency.getBasePrice(), 0.001);
    }

    @Test
    void testCalculateMarketValue() {
        Currency currency = new Currency("GBP", "Funt", 100.0);
        // 100 * 10 = 1000
        double value = currency.calculateMarketValue(10);
        assertEquals(1000.0, value, 0.001);
    }

    @Test
    void testCalculateTransactionCost() {
        Currency currency = new Currency("USD", "Dolar", 100.0);
        // (100 * 1) * (1 + 0.02) = 100 * 1.02 = 102
        double cost = currency.calculateTransactionCost(1);
        assertEquals(102.0, cost, 0.001);
    }

    @Test
    void testTransactionCostIncludesSpread() {
        Currency currency = new Currency("JPY", "Jen", 100.0);
        // (100 * 100) * (1 + 0.02) = 10000 * 1.02 = 10200
        double cost = currency.calculateTransactionCost(100);
        assertEquals(10200.0, cost, 0.001);
    }

    @Test
    void testSpreadIsConstantPercentage() {
        Currency currency = new Currency("CHF", "Frank", 100.0);
        double cost5 = currency.calculateTransactionCost(5); // 500 * 1.02 = 510
        double cost10 = currency.calculateTransactionCost(10); // 1000 * 1.02 = 1020

        assertEquals(510.0, cost5, 0.001);
        assertEquals(1020.0, cost10, 0.001);
        assertEquals(2.0, cost10 / cost5, 0.001);
    }

    @Test
    void testSymbolCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
            new Currency("", "Euro", 100.0)
        );
    }

    @Test
    void testNameCannotBeNull() {
        assertThrows(IllegalArgumentException.class, () ->
            new Currency("EUR", null, 100.0)
        );
    }

    @Test
    void testNameCannotBeEmpty() {
        assertThrows(IllegalArgumentException.class, () ->
            new Currency("EUR", "", 100.0)
        );
    }

    @Test
    void testPriceCannotBeNegative() {
        assertThrows(IllegalArgumentException.class, () ->
            new Currency("EUR", "Euro", -50.0)
        );
    }

    @Test
    void testPriceCannotBeZero() {
        assertThrows(IllegalArgumentException.class, () ->
            new Currency("EUR", "Euro", 0.0)
        );
    }
}
