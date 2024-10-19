package com.example.platenwinkel.models;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

//vertegenwoordigt een volledige factuur
@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    private double VAT;
    private double shippingCost;

//    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    private User user;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Order> items;

    @Column(nullable = false)
    private double totalAmount;

    // Default constructor
    public Invoice(){}

    public Invoice(String invoiceNumber, LocalDate date, User user, Double totalAmount) {
        this.invoiceNumber = invoiceNumber;
        this.date = date;
        this.user = user;
        this.totalAmount = totalAmount;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
    }

    public double getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(double shippingCost) {
        this.shippingCost = shippingCost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Order> getItems() {
        return items;
    }

    public void setItems(List<Order> items) {
        this.items = items;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
