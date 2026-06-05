package com.devpulse.dto;

import com.devpulse.model.InvoiceStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvoiceResponseDto {
    private Long id;
    private String clientName;
    private String clientEmail;
    private BigDecimal amount;
    private LocalDate dueDate;
    private InvoiceStatus status;
    private String description;
    private LocalDateTime createdAt;

    public InvoiceResponseDto(Long id, String clientName, String clientEmail,
                              BigDecimal amount, LocalDate dueDate,
                              InvoiceStatus status, String description,
                              LocalDateTime createdAt) {
        this.id = id;
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public InvoiceStatus getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
