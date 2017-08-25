package ru.tonyappl.cryptoman.models;

import com.google.gson.annotations.SerializedName;

public class Value {
    private String id;
    private String name;
    private String symbol;
    private String rank;

    @SerializedName("price_usd")
    private String priceUsd;

    @SerializedName("price_btc")
    private String priceBtc;

    @SerializedName("market_cap_usd")
    private String marketCapUsd;

    @SerializedName("available_supply")
    private String availableSupply;

    @SerializedName("percent_change_1h")
    private String percentChange1h;

    @SerializedName("percent_change_24h")
    private String percentChange24h;

    @SerializedName("percent_change_7d")
    private String percentChange7d;

    private String imageUrl;

    public Value(String id, String name, String symbol, String rank, String priceUsd, String priceBtc, String marketCapUsd, String availableSupply, String percentChange1h, String percentChange24h, String percentChange7d, String imageUrl) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.rank = rank;
        this.priceUsd = priceUsd;
        this.priceBtc = priceBtc;
        this.marketCapUsd = marketCapUsd;
        this.availableSupply = availableSupply;
        this.percentChange1h = percentChange1h;
        this.percentChange24h = percentChange24h;
        this.percentChange7d = percentChange7d;
        this.imageUrl = imageUrl;
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

    public String getRank() {
        return rank;
    }

    public String getPriceUsd() {
        return priceUsd;
    }

    public String getPriceBtc() {
        return priceBtc;
    }

    public String getMarketCapUsd() {
        return marketCapUsd;
    }

    public String getPercentChange1h() {
        return percentChange1h;
    }

    public String getPercentChange24h() {
        return percentChange24h;
    }

    public String getPercentChange7d() {
        return percentChange7d;
    }

    public String getAvailableSupply() {
        return availableSupply;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
