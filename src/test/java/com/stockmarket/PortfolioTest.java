package com.stockmarket;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PortfolioTest {

    // Test: pusty portfel powinien mieć tylko gotówkę, 0 akcji
    @Test
    void shouldCreateEmptyPortfolioWithInitialCash() {
        Portfolio portfolio = new Portfolio(10000.0);

        assertEquals(10000.0, portfolio.getCash(), 0.001);
        assertEquals(0, portfolio.getHoldingsCount());
        assertEquals(10000.0, portfolio.calculateTotalValue(), 0.001);
    }

    // Test: dodanie akcji po raz pierwszy tworzy nową pozycję
    @Test
    void shouldAddStockForFirstTime() {
        Portfolio portfolio = new Portfolio(5000.0);
        Stock apple = new Stock("AAPL", "Apple", 150.0);

        portfolio.addStock(apple, 10);

        assertEquals(1, portfolio.getHoldingsCount()); // 1 pozycja
        assertEquals(10, portfolio.getStockQuantity(apple)); // 10 sztuk
    }

    // Test: dodanie tej samej akcji wielokrotnie sumuje ilości
    @Test
    void shouldAddSameStockMultipleTimes() {
        Portfolio portfolio = new Portfolio(5000.0);
        Stock google = new Stock("GOOGL", "Google", 100.0);

        portfolio.addStock(google, 5);
        portfolio.addStock(google, 3);

        assertEquals(1, portfolio.getHoldingsCount()); // 1
        assertEquals(8, portfolio.getStockQuantity(google)); // 5 + 3 = 8
    }

    // Test: różne akcje tworzą osobne pozycje w portfelu
    @Test
    void shouldAddDifferentStocksAsSeparateHoldings() {
        Portfolio portfolio = new Portfolio(10000.0);
        Stock apple = new Stock("AAPL", "Apple", 150.0);
        Stock google = new Stock("GOOGL", "Google", 100.0);
        Stock microsoft = new Stock("MSFT", "Microsoft", 300.0);

        portfolio.addStock(apple, 10);
        portfolio.addStock(google, 5);
        portfolio.addStock(microsoft, 2);

        assertEquals(3, portfolio.getHoldingsCount()); // 3 różne pozycje
        assertEquals(10, portfolio.getStockQuantity(apple));
        assertEquals(5, portfolio.getStockQuantity(google));
        assertEquals(2, portfolio.getStockQuantity(microsoft));
    }

    // Test: obliczanie wartości akcji (suma: cena * ilość)
    @Test
    void shouldCalculateStockValueCorrectly() {
        Portfolio portfolio = new Portfolio(5000.0);
        Stock apple = new Stock("AAPL", "Apple", 150.0);
        Stock google = new Stock("GOOGL", "Google", 100.0);

        portfolio.addStock(apple, 10); // 150 * 10 = 1500
        portfolio.addStock(google, 5); // 100 * 5 = 500

        double expectedStockValue = (150.0 * 10) + (100.0 * 5); // 2000
        assertEquals(
            expectedStockValue,
            portfolio.calculateStockValue(),
            0.001
        );
    }

    // Test: obliczanie całkowitej wartości (gotówka + akcje)
    @Test
    void shouldCalculateTotalValueCorrectly() {
        Portfolio portfolio = new Portfolio(5000.0);
        Stock apple = new Stock("AAPL", "Apple", 150.0);
        Stock google = new Stock("GOOGL", "Google", 100.0);

        portfolio.addStock(apple, 10); // 1500
        portfolio.addStock(google, 5); // 500

        double expectedTotal = 5000.0 + (150.0 * 10) + (100.0 * 5); // 5000 + 2000 = 7000
        assertEquals(expectedTotal, portfolio.calculateTotalValue(), 0.001);
    }

    // Test: sprawdzanie ilości nieistniejącej akcji powinno zwrócić 0
    @Test
    void shouldReturnZeroForNonExistentStock() {
        Portfolio portfolio = new Portfolio(1000.0);
        Stock tesla = new Stock("TSLA", "Tesla", 700.0);

        assertEquals(0, portfolio.getStockQuantity(tesla)); // Akcji nie ma = 0
    }

    // Test: pusty portfel ma wartość akcji = 0, całkowita wartość = gotówka
    @Test
    void shouldHandleEmptyPortfolioCalculations() {
        Portfolio portfolio = new Portfolio(2000.0);

        assertEquals(0.0, portfolio.calculateStockValue(), 0.001); // Brak akcji = 0
        assertEquals(2000.0, portfolio.calculateTotalValue(), 0.001); // Tylko gotówka
    }

    // Test: akcje z tym samym symbolem (ale różne obiekty) są traktowane jako ta sama pozycja
    @Test
    void shouldAddStocksWithSameSymbolButDifferentObjects() {
        Portfolio portfolio = new Portfolio(5000.0);
        Stock apple1 = new Stock("AAPL", "Apple Inc.", 150.0);
        Stock apple2 = new Stock("AAPL", "Apple Corporation", 155.0); // Inny obiekt, ale TEN SAM symbol

        portfolio.addStock(apple1, 5);
        portfolio.addStock(apple2, 3); // Powinno dodać do istniejącej pozycji

        assertEquals(1, portfolio.getHoldingsCount()); // 1 pozycja
        assertEquals(8, portfolio.getStockQuantity(apple1)); // 5 + 3 = 8
        assertEquals(8, portfolio.getStockQuantity(apple2)); // Ten sam wynik
    }

    // Test: tablica ma max 10 pozycji - dodanie 11-tej nie zadziała
    @Test
    void shouldNotAddStockWhenArrayIsFull() {
        Portfolio portfolio = new Portfolio(50000.0);

        // Zapełnij tablicę 10 różnymi akcjami
        for (int i = 0; i < 10; i++) {
            Stock stock = new Stock("SYM" + i, "Company " + i, 100.0);
            portfolio.addStock(stock, 1);
        }

        assertEquals(10, portfolio.getHoldingsCount()); // 10 pozycji - tablica pełna

        // Próba dodania 11-tej akcji
        Stock extraStock = new Stock("EXTRA", "Extra Company", 50.0);
        portfolio.addStock(extraStock, 5);

        assertEquals(10, portfolio.getHoldingsCount()); // Nadal 10 (nie dodało)
        assertEquals(0, portfolio.getStockQuantity(extraStock)); // Akcji nie ma
    }
}
