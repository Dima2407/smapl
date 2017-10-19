package com.smapl_android.net.requests;

import com.google.gson.annotations.SerializedName;

public class MoneyWithdrawRequest {

    @SerializedName("amount")
    private double amount;

    @SerializedName("card")
    private String card;

    @SerializedName("bank")
    private String bank;

    public MoneyWithdrawRequest(double amount, String card, String bank) {
        this.amount = amount;
        this.card = card;
        this.bank = bank;
    }
}
