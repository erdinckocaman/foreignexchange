package com.tamplan.sample.foreignexchange.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "currency_conversion_result")
public class CurrencyConversionResult {

    private String transactionId;
    private String baseCurrency;
    private String targetCurrency;
    private BigDecimal amount;
    private BigDecimal convertedAmount;
    private LocalDateTime createdAt;

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    @Id
    @Column(name = "transaction_id")
    public String getTransactionId() {
        return transactionId;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    @Column(name = "base_currency")
    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @Column(name = "target_currency")
    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Column(name = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @Column(name = "converted_amount")
    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public void setConvertedAmount(BigDecimal convertedAmount) {
        this.convertedAmount = convertedAmount;
    }

    @Column(name = "created_at")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }


}
