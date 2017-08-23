package ru.tonyappl.cryptoman.models;

import com.google.gson.annotations.SerializedName;

public class Value {
    private String id;
    private String name;
    private String symbol;

    @SerializedName("price_usd")
    private String priceUsd;

    public Value(String id, String name, String symbol, String priceUsd) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.priceUsd = priceUsd;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public void setPriceUsd(String priceUsd) {
        this.priceUsd = priceUsd;
    }


}
