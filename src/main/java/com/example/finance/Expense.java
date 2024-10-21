package com.example.finance;

import java.math.BigDecimal;

public class Expense {
    private int id;
    private String item;
    private BigDecimal amount;

    public Expense(int id, String item, BigDecimal amount) {
        this.id = id;
        this.item = item;
        this.amount = amount;
    }

    // Геттеры
    public int getId() {
        return id;
    }

    public String getItem() {
        return item;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // Сеттеры (если необходимо)
    public void setId(int id) {
        this.id = id;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
