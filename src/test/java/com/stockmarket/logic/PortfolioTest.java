package com.stockmarket.logic;

import static org.junit.jupiter.api.Assertions.*;

import com.stockmarket.domain.Asset;
import com.stockmarket.domain.Commodity;
import com.stockmarket.domain.Currency;
import com.stockmarket.domain.Share;
import org.junit.jupiter.api.Test;

class PortfolioTest {

    @Test
    void testCreatePortfolio() {
        Portfolio portfolio = new Portfolio(5000.0);
        assertEquals(5000.0, portfolio.getCash(), 0.001);
        assertEquals(0, portfolio.getHoldingsCount());
    }

    @Test
    void testAddAsset() {
        Portfolio portfolio = new Portfolio(5000.0);
        Asset share = new Share("AAPL", "Apple", 100.0);
        portfolio.addAsset(share, 10);

        assertEquals(1, portfolio.getHoldingsCount());
        assertEquals(10, portfolio.getAssetQuantity(share));
    }

    @Test
    void testAddMultipleAssets() {
        Portfolio portfolio = new Portfolio(10000.0);
        Asset share = new Share("AAPL", "Apple", 100.0);
        Asset commodity = new Commodity("GOLD", "Złoto", 100.0);
        Asset currency = new Currency("EUR", "Euro", 100.0);

        portfolio.addAsset(share, 10);
        portfolio.addAsset(commodity, 10);
        portfolio.addAsset(currency, 10);

        assertEquals(3, portfolio.getHoldingsCount());
        assertEquals(10, portfolio.getAssetQuantity(share));
        assertEquals(10, portfolio.getAssetQuantity(commodity));
        assertEquals(10, portfolio.getAssetQuantity(currency));
    }

    @Test
    void testAddSameAssetTwice() {
        Portfolio portfolio = new Portfolio(5000.0);
        Asset share = new Share("AAPL", "Apple", 100.0);

        portfolio.addAsset(share, 5);
        portfolio.addAsset(share, 3);

        assertEquals(1, portfolio.getHoldingsCount());
        assertEquals(8, portfolio.getAssetQuantity(share));
    }

    @Test
    void testCalculateAssetsValue() {
        Portfolio portfolio = new Portfolio(10000.0);
        Asset share = new Share("MSFT", "Microsoft", 100.0);
        Asset commodity = new Commodity("CRUDE", "Ropa", 100.0);

        portfolio.addAsset(share, 10); // (100*10) + 5 = 1005
        portfolio.addAsset(commodity, 10); // (100*10) + 20 = 1020

        double assetsValue = portfolio.calculateAssetsValue();
        // 1000 + 1000 = 2000
        assertEquals(2000.0, assetsValue, 0.001);
    }

    @Test
    void testCalculateTotalValue() {
        Portfolio portfolio = new Portfolio(2000.0);
        Asset share = new Share("GOOGL", "Google", 100.0);

        portfolio.addAsset(share, 10); // (100*10) + 5 = 1005

        double totalValue = portfolio.calculateTotalValue();
        // cash = 2000 - 1005 = 995
        // assets (market value) = 1000
        // total = 995 + 1000 = 1995 (strata 5 zł)
        assertEquals(1995.0, totalValue, 0.001);
    }

    @Test
    void testDepositCash() {
        Portfolio portfolio = new Portfolio(1000.0);
        portfolio.depositCash(500.0);
        assertEquals(1500.0, portfolio.getCash(), 0.001);
    }

    @Test
    void testWithdrawCash() {
        Portfolio portfolio = new Portfolio(1000.0);
        portfolio.withdrawCash(300.0);
        assertEquals(700.0, portfolio.getCash(), 0.001);
    }

    @Test
    void testDepositCashNegative() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertThrows(IllegalArgumentException.class, () ->
            portfolio.depositCash(-100.0)
        );
    }

    @Test
    void testDepositCashZero() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertThrows(IllegalArgumentException.class, () ->
            portfolio.depositCash(0.0)
        );
    }

    @Test
    void testWithdrawCashNegative() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertThrows(IllegalArgumentException.class, () ->
            portfolio.withdrawCash(-50.0)
        );
    }

    @Test
    void testWithdrawCashZero() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertThrows(IllegalArgumentException.class, () ->
            portfolio.withdrawCash(0.0)
        );
    }

    @Test
    void testWithdrawCashNotEnoughFunds() {
        Portfolio portfolio = new Portfolio(500.0);
        assertThrows(IllegalArgumentException.class, () ->
            portfolio.withdrawCash(600.0)
        );
    }

    @Test
    void testCannotAddAssetIfNotEnoughCash() {
        Portfolio portfolio = new Portfolio(100.0);
        Asset share = new Share("AAPL", "Apple", 100.0);
        // Koszt: (100 * 3) + 5 = 305 > 100
        assertThrows(IllegalArgumentException.class, () ->
            portfolio.addAsset(share, 3)
        );
    }

    @Test
    void testAssetNull() {
        Portfolio portfolio = new Portfolio(1000.0);
        assertThrows(IllegalArgumentException.class, () ->
            portfolio.addAsset(null, 5)
        );
    }

    @Test
    void testQuantityNegative() {
        Portfolio portfolio = new Portfolio(1000.0);
        Asset share = new Share("AAPL", "Apple", 100.0);
        assertThrows(IllegalArgumentException.class, () ->
            portfolio.addAsset(share, -5)
        );
    }

    @Test
    void testQuantityZero() {
        Portfolio portfolio = new Portfolio(1000.0);
        Asset share = new Share("AAPL", "Apple", 100.0);
        assertThrows(IllegalArgumentException.class, () ->
            portfolio.addAsset(share, 0)
        );
    }

    @Test
    void testInitialCashNegative() {
        assertThrows(IllegalArgumentException.class, () ->
            new Portfolio(-1000.0)
        );
    }

    @Test
    void testGetAssetQuantityNonExistent() {
        Portfolio portfolio = new Portfolio(1000.0);
        Asset share = new Share("AAPL", "Apple", 100.0);
        assertEquals(0, portfolio.getAssetQuantity(share));
    }

    @Test
    void testPolymorphismDifferentValues() {
        Asset share = new Share("POLY1", "Test Share", 100.0);
        Asset commodity = new Commodity("POLY2", "Test Commodity", 100.0);
        Asset currency = new Currency("POLY3", "Test Currency", 100.0);

        double shareMarket = share.calculateMarketValue(10); // 100*10 = 1000
        double commodityMarket = commodity.calculateMarketValue(10); // 100*10 = 1000
        double currencyMarket = currency.calculateMarketValue(10); // 100*10 = 1000

        assertEquals(1000.0, shareMarket, 0.001);
        assertEquals(1000.0, commodityMarket, 0.001);
        assertEquals(1000.0, currencyMarket, 0.001);

        // Koszty transakcji - różne!
        double shareCost = share.calculateTransactionCost(10); // 1000 + 5 = 1005
        double commodityCost = commodity.calculateTransactionCost(10); // 1000 + 20 = 1020
        double currencyCost = currency.calculateTransactionCost(10); // 1000 * 1.02 = 1020

        assertEquals(1005.0, shareCost, 0.001);
        assertEquals(1020.0, commodityCost, 0.001);
        assertEquals(1020.0, currencyCost, 0.001);

        assertNotEquals(shareCost, commodityCost);
        assertNotEquals(shareCost, currencyCost);
    }
}
