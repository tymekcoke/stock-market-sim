package com.stockmarket.domain;

public class Commodity extends Asset {

    // Koszt przechowywania za jednostkę
    private static final double STORAGE_COST_PER_UNIT = 2.0;

    // Konstruktor
    public Commodity(String symbol, String name, double basePrice) {
        super(symbol, name, basePrice);
    }

    // Implementacja metod abstrakcyjnych
    @Override
    public double calculateMarketValue(int quantity) {
        return getBasePrice() * quantity;
    }

    @Override
    public double calculateTransactionCost(int quantity) {
        double storageCost = STORAGE_COST_PER_UNIT * quantity;
        return calculateMarketValue(quantity) + storageCost;
    }
}
