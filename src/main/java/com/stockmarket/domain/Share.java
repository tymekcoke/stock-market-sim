package com.stockmarket.domain;

public class Share extends Asset {

    // Stała opłata za transakcję
    private static final double MANIPULATION_FEE = 5.0;

    // Konstruktor
    public Share(String symbol, String name, double basePrice) {
        super(symbol, name, basePrice);
    }

    // Implementacja metod abstrakcyjnych
    @Override
    public double calculateMarketValue(int quantity) {
        return getBasePrice() * quantity;
    }

    @Override
    public double calculateTransactionCost(int quantity) {
        return calculateMarketValue(quantity) + MANIPULATION_FEE;
    }
}
