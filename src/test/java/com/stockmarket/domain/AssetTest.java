package com.stockmarket.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AssetTest {

    @Test
    void testCreateAsset() {
        Asset asset = new Share("AAPL", "Apple", 100.0);
        assertEquals("AAPL", asset.getSymbol());
        assertEquals("Apple", asset.getName());
        assertEquals(100.0, asset.getBasePrice(), 0.001);
    }

    @Test
    void testSetName() {
        Asset asset = new Share("MSFT", "Microsoft", 200.0);
        asset.setName("Microsoft Corporation");
        assertEquals("Microsoft Corporation", asset.getName());
    }

    @Test
    void testSetNameNull() {
        Asset asset = new Share("GOOGL", "Google", 150.0);
        assertThrows(IllegalArgumentException.class, () -> asset.setName(null));
    }

    @Test
    void testSetNameEmpty() {
        Asset asset = new Share("AMZN", "Amazon", 120.0);
        assertThrows(IllegalArgumentException.class, () -> asset.setName(""));
    }

    @Test
    void testSetBasePrice() {
        Asset asset = new Share("TSLA", "Tesla", 500.0);
        asset.setBasePrice(550.0);
        assertEquals(550.0, asset.getBasePrice(), 0.001);
    }

    @Test
    void testSetBasePriceNegative() {
        Asset asset = new Share("FB", "Meta", 300.0);
        assertThrows(IllegalArgumentException.class, () ->
            asset.setBasePrice(-10.0)
        );
    }

    @Test
    void testSetBasePriceZero() {
        Asset asset = new Share("NFLX", "Netflix", 400.0);
        assertThrows(IllegalArgumentException.class, () ->
            asset.setBasePrice(0.0)
        );
    }

    @Test
    void testAssetEquals() {
        Asset asset1 = new Share("AAPL", "Apple", 100.0);
        Asset asset2 = new Share("AAPL", "Apple Inc", 150.0);
        assertTrue(asset1.equals(asset2));
    }

    @Test
    void testAssetNotEqual() {
        Asset asset1 = new Share("AAPL", "Apple", 100.0);
        Asset asset2 = new Share("MSFT", "Microsoft", 100.0);
        assertFalse(asset1.equals(asset2));
    }

    @Test
    void testAssetNull() {
        Asset asset1 = new Share("AAPL", "Apple", 100.0);
        Asset asset2 = null;
        assertFalse(asset1.equals(asset2));
    }

    @Test
    void testAssetHashCode() {
        Asset asset1 = new Share("GOOGL", "Google", 100.0);
        Asset asset2 = new Share("GOOGL", "Google Inc", 150.0);
        assertEquals(asset1.hashCode(), asset2.hashCode());
    }
}
