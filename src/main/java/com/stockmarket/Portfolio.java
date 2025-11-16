package com.stockmarket;

public class Portfolio {

    // Klasa zagnieżdżona - pozycja w portfelu (akcja + ilość)
    private static class StockHolding {

        Stock stock; // Jaka akcja
        int quantity; // Ile sztuk

        StockHolding(Stock stock, int quantity) {
            this.stock = stock;
            this.quantity = quantity;
        }
    }

    // Pola portfela
    private double cash; // Pieniądze
    private StockHolding[] holdings; // Tablica na akcje
    private int holdingsCount; // Licznik

    // Konstruktor
    public Portfolio(double initialCash) {
        this.cash = initialCash;
        this.holdings = new StockHolding[10]; // Tworzymy tablicę na 10 elementów
        this.holdingsCount = 0; // Na początku pusty
    }

    // Dodaje akcje do portfela - jeśli już istnieje zwiększa ilość, jeśli nie - dodaje nową pozycję
    public void addStock(Stock stock, int quantity) {
        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i].stock.equals(stock)) {
                holdings[i].quantity += quantity;
                return;
            }
        }

        if (holdingsCount < holdings.length) {
            holdings[holdingsCount] = new StockHolding(stock, quantity);
            holdingsCount++;
        }
    }

    // Oblicza wartość wszystkich akcji (suma: cena * ilość dla każdej pozycji)
    public double calculateStockValue() {
        double totalValue = 0.0;
        for (int i = 0; i < holdingsCount; i++) {
            totalValue +=
                holdings[i].stock.getInitialPrice() * holdings[i].quantity;
        }
        return totalValue;
    }

    // Oblicza całkowitą wartość portfela (gotówka + akcje)
    public double calculateTotalValue() {
        return cash + calculateStockValue();
    }

    // Getter - zwraca gotówkę
    public double getCash() {
        return cash;
    }

    // Getter - zwraca liczbę różnych akcji w portfelu
    public int getHoldingsCount() {
        return holdingsCount;
    }

    // Zwraca ilość sztuk danej akcji lub 0 jeśli jej nie ma
    public int getStockQuantity(Stock stock) {
        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i].stock.equals(stock)) {
                return holdings[i].quantity;
            }
        }
        return 0;
    }
}
