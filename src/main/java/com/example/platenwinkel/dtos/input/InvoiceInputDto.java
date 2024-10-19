package com.example.platenwinkel.dtos.input;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public class InvoiceInputDto {
    public String invoiceNumber;
    public double VAT;
    public double shippingCost;
    public LocalDate date;
    public Long userId;
    public String userName;
    public List<Long> orderIds;
    public Double totalAmount;

    public List<Long> getOrderIds() {
        return orderIds;
    }

    public Long getUserId() {
        return userId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public double getVAT() {
        return VAT;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public String getUserName() {
        return userName;
    }
}
