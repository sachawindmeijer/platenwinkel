package com.example.platenwinkel.dtos.mapper;

import com.example.platenwinkel.dtos.input.InvoiceInputDto;

import com.example.platenwinkel.dtos.output.InvoiceOutputDto;

import com.example.platenwinkel.models.Invoice;

import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.models.User;

import java.util.List;
import java.util.stream.Collectors;

public class InvoiceMapper {
    public static Invoice fromInputDtoToModel(InvoiceInputDto invoiceInputDto, User user, List<Order> orders) {
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(invoiceInputDto.invoiceNumber);
        invoice.setVAT(invoiceInputDto.VAT);
        invoice.setShippingCost(invoiceInputDto.shippingCost);
        invoice.setDate(invoiceInputDto.date);
        invoice.setUser(user);
        invoice.setItems(orders);
        invoice.setTotalAmount(invoiceInputDto.totalAmount);
        return invoice;

    }

    public static InvoiceOutputDto fromInvoiceToOutputDto(Invoice invoice) {
        InvoiceOutputDto dto = new InvoiceOutputDto();

        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setVAT(invoice.getVAT());
        dto.setShippingCost(invoice.getShippingCost());
        dto.setDate(invoice.getDate());
        dto.setCustomerName(invoice.getUser().getUsername());
        dto.setOrderIds(invoice.getItems().stream()
                .map(Order::getId)
                .collect(Collectors.toList()));
        dto.setTotalAmount(invoice.getTotalAmount());
        return dto;
    }

}