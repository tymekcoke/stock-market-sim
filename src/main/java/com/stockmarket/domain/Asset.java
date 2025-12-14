package com.stockmarket.domain;

public abstract class Asset {

    // Pola
    private final String symbol;
    private String name;
    private double basePrice;

    // Konstruktor
    public Asset(String symbol, String name, double basePrice) {
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("Symbol nie może być pusty");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nazwa nie może być pusta");
        }
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Cena musi być dodatnia");
        }

        this.symbol = symbol;
        this.name = name;
        this.basePrice = basePrice;
    }

    // Gettery
    public String getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public double getBasePrice() {
        return basePrice;
    }

    // Settery
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nazwa nie może być pusta");
        }
        this.name = name;
    }

    public void setBasePrice(double basePrice) {
        if (basePrice <= 0) {
            throw new IllegalArgumentException("Cena musi być dodatnia");
        }
        this.basePrice = basePrice;
    }

    // Metody abstrakcyjne
    // Wartość rynkowa (bez kosztów transakcji)
    public abstract double calculateMarketValue(int quantity);

    // Koszt zakupu (z kosztami transakcji)
    public abstract double calculateTransactionCost(int quantity);

    // Nadpisanie equals()
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Asset other = (Asset) obj;
        return this.symbol.equals(other.symbol);
    }

    // Nadpisanie hashCode()
    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}
