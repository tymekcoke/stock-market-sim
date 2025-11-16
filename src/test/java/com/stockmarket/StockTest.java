package com.stockmarket;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StockTest {

    // Test: sprawdza czy konstruktor i gettery działają poprawnie
    @Test
    void shouldCreateStockWithCorrectValues() {
        Stock stock = new Stock("AAPL", "Apple Inc.", 150.0);

        assertEquals("AAPL", stock.getSymbol());
        assertEquals("Apple Inc.", stock.getName());
        assertEquals(150.0, stock.getInitialPrice(), 0.001); // 0.001 = tolerancja dla double
    }

    // Test: dwie akcje z tym samym symbolem POWINNY być równe
    @Test
    void shouldBeEqualWhenSymbolsAreTheSame() {
        Stock stock1 = new Stock("GOOGL", "Google", 100.0);
        Stock stock2 = new Stock("GOOGL", "Different Name", 200.0); // Różne nazwy i ceny, ale TEN SAM symbol

        assertTrue(stock1.equals(stock2)); // Powinny być równe
    }

    // Test: dwie akcje z różnymi symbolami NIE POWINNY być równe
    @Test
    void shouldNotBeEqualWhenSymbolsAreDifferent() {
        Stock stock1 = new Stock("AAPL", "Apple", 150.0);
        Stock stock2 = new Stock("GOOGL", "Google", 100.0);

        assertFalse(stock1.equals(stock2)); // Powinny być różne
    }

    // Test: akcja porównana z null NIE POWINNA być równa
    @Test
    void shouldNotBeEqualToNull() {
        Stock stock = new Stock("AAPL", "Apple", 150.0);

        assertFalse(stock.equals(null)); // Ochrona przed NullPointerException
    }

    // Test: dwie równe akcje MUSZĄ mieć ten sam hashCode (kontrakt Java)
    @Test
    void shouldHaveSameHashCodeWhenEqual() {
        Stock stock1 = new Stock("MSFT", "Microsoft", 300.0);
        Stock stock2 = new Stock("MSFT", "Microsoft Corp", 310.0); // Ten sam symbol

        assertEquals(stock1.hashCode(), stock2.hashCode()); // hashCode musi być taki sam!
    }

    // Test: akcja porównana sama ze sobą POWINNA być równa
    @Test
    void shouldBeEqualToItself() {
        Stock stock = new Stock("TSLA", "Tesla", 700.0);

        assertTrue(stock.equals(stock));
    }

    // Test: akcja porównana z obiektem innej klasy NIE POWINNA być równa
    @Test
    void shouldNotBeEqualToDifferentClass() {
        Stock stock = new Stock("AMZN", "Amazon", 3000.0);
        String notAStock = "Na pewno nie Stock";

        assertFalse(stock.equals(notAStock)); // Stock != String
    }
}
