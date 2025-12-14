package com.stockmarket.domain;

public class Currency extends Asset {

    // Spread walutowy
    private static final double SPREAD_PERCENTAGE = 0.02; // 2% spreadu

    // Konstruktor
    public Currency(String symbol, String name, double basePrice) {
        super(symbol, name, basePrice);
    }

    // Implementacja metod abstrakcyjnych
    @Override
    public double calculateMarketValue(int quantity) {
        return getBasePrice() * quantity;
    }

    @Override
    public double calculateTransactionCost(int quantity) {
        // Spread procentowy
        return calculateMarketValue(quantity) * (1 + SPREAD_PERCENTAGE);
    }
}
