package com.stockmarket.logic;

import com.stockmarket.domain.Asset;

public class Portfolio {

    // Pozycja w portfelu
    private static class AssetHolding {

        Asset asset; // Jakie aktywo
        int quantity; // Ile sztuk

        AssetHolding(Asset asset, int quantity) {
            this.asset = asset;
            this.quantity = quantity;
        }
    }

    // Pola portfela
    private double cash;
    private AssetHolding[] holdings;
    private int holdingsCount;

    // Konstruktor
    public Portfolio(double initialCash) {
        if (initialCash < 0) {
            throw new IllegalArgumentException(
                "Początkowa gotówka nie może być ujemna"
            );
        }

        this.cash = initialCash;
        this.holdings = new AssetHolding[10];
        this.holdingsCount = 0;
    }

    // Dodawanie aktywa do portfela
    public void addAsset(Asset asset, int quantity) {
        if (asset == null) {
            throw new IllegalArgumentException("Aktywo nie może być null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Ilość musi być dodatnia");
        }

        double costOfPurchase = asset.calculateTransactionCost(quantity);

        if (costOfPurchase > cash) {
            throw new IllegalArgumentException(
                "Brak wystarczających środków. Potrzeba: " +
                    costOfPurchase +
                    ", dostępne: " +
                    cash
            );
        }

        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i].asset.equals(asset)) {
                holdings[i].quantity += quantity;
                cash -= costOfPurchase;
                return;
            }
        }

        if (holdingsCount < holdings.length) {
            holdings[holdingsCount] = new AssetHolding(asset, quantity);
            holdingsCount++;
            cash -= costOfPurchase;
        }
    }

    // Oblicza całkowitą wartość w portfelu
    public double calculateAssetsValue() {
        double totalValue = 0.0;
        for (int i = 0; i < holdingsCount; i++) {
            totalValue += holdings[i].asset.calculateMarketValue(
                holdings[i].quantity
            );
        }
        return totalValue;
    }

    // Wartość portfela
    public double calculateTotalValue() {
        return cash + calculateAssetsValue();
    }

    // Gettery
    public double getCash() {
        return cash;
    }

    public int getHoldingsCount() {
        return holdingsCount;
    }

    // Zwraca ilość sztuk danego aktywa
    public int getAssetQuantity(Asset asset) {
        for (int i = 0; i < holdingsCount; i++) {
            if (holdings[i].asset.equals(asset)) {
                return holdings[i].quantity;
            }
        }
        return 0;
    }

    // Wpłaca gotówkę do portfela
    public void depositCash(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Wpłacona kwota musi być dodatnia"
            );
        }
        this.cash += amount;
    }

    // Wypłaca gotówkę z portfela
    public void withdrawCash(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(
                "Wypłacana kwota musi być dodatnia"
            );
        }
        if (amount > this.cash) {
            throw new IllegalArgumentException(
                "Brak wystarczających środków. Żądane: " +
                    amount +
                    ", dostępne: " +
                    cash
            );
        }
        this.cash -= amount;
    }
}
