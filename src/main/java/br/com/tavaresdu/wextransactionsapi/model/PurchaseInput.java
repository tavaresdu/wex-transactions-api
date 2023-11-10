package br.com.tavaresdu.wextransactionsapi.model;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PurchaseInput {

    @Size(max = 50)
    @NotBlank
    private String description;

    @NotNull
    private LocalDate transactionDate;

    @NotNull
    @Min(0)
    private BigDecimal amount;

    public PurchaseInput() {
    }

    public PurchaseInput(String description, LocalDate transactionDate, BigDecimal amount) {
        this.description = description;
        this.transactionDate = transactionDate;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
