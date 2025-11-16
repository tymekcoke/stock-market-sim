package com.stockmarket;

public class Stock {

    // Pola private final - enkapsulacja + niezmienność
    private final String symbol; // Unikalny symbol
    private final String name; // Nazwa firmy
    private final double initialPrice; // Cena początkowa

    // Konstruktor - inicjalizuje wszystkie pola z walidacją
    public Stock(String symbol, String name, double initialPrice) {
        if (symbol == null || symbol.isEmpty()) {
            throw new IllegalArgumentException("Symbol nie może być pusty");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Nazwa nie może być pusta");
        }
        if (initialPrice <= 0) {
            throw new IllegalArgumentException("Cena musi być dodatnia");
        }

        this.symbol = symbol;
        this.name = name;
        this.initialPrice = initialPrice;
    }

    // Getter - zwraca symbol
    public String getSymbol() {
        return symbol;
    }

    // Getter - zwraca nazwę
    public String getName() {
        return name;
    }

    // Getter - zwraca cenę początkową
    public double getInitialPrice() {
        return initialPrice;
    }

    // Nadpisanie equals() - porównuje akcje po symbolu
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        Stock other = (Stock) obj;
        return this.symbol.equals(other.symbol);
    }

    // Nadpisanie hashCode() - musi być zgodny z equals()
    @Override
    public int hashCode() {
        return symbol.hashCode();
    }
}
