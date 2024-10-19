package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.InvoiceInputDto;
import com.example.platenwinkel.dtos.mapper.InvoiceMapper;
import com.example.platenwinkel.dtos.output.InvoiceOutputDto;

import com.example.platenwinkel.models.Invoice;
import com.example.platenwinkel.models.Order;


import com.example.platenwinkel.models.User;
import com.example.platenwinkel.repositories.InvoiceRepository;
import com.example.platenwinkel.repositories.OrderRepository;
import com.example.platenwinkel.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, UserRepository userRepository, OrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    public List<InvoiceOutputDto> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .map(InvoiceMapper::fromInvoiceToOutputDto)
                .collect(Collectors.toList());
    }

    public InvoiceOutputDto getInvoiceById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
        return InvoiceMapper.fromInvoiceToOutputDto(invoice);
    }

    public InvoiceOutputDto createInvoice(InvoiceInputDto invoiceInputDto) {
        // Validate input
//        validateInput(invoiceInputDto);

        // Retrieve the user by username
        Optional<User> userOptional = Optional.ofNullable(userRepository.findByUsername(invoiceInputDto.getUserName()));
        if (!userOptional.isPresent()) {
            throw new IllegalArgumentException("User not found: " + invoiceInputDto.getUserName());
        }
        User user = userOptional.get();

        // Retrieve associated orders
        List<Order> orders = new ArrayList<>();
        for (Long orderId : invoiceInputDto.getOrderIds()) {
            Optional<Order> orderOptional = orderRepository.findById(orderId);
            if (!orderOptional.isPresent()) {
                throw new IllegalArgumentException("Order not found: " + orderId);
            }
            orders.add(orderOptional.get());
        }

        // Create the invoice and save it
        Invoice invoice = InvoiceMapper.fromInputDtoToModel(invoiceInputDto, user, orders);
        invoice = invoiceRepository.save(invoice);

        // Return the output DTO
        return InvoiceMapper.fromInvoiceToOutputDto(invoice);
    }


    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public InvoiceOutputDto updateInvoice(Long id, InvoiceInputDto inputDto) {
        Invoice existingInvoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        // Haal de klant en orders opnieuw op om te updaten
       User user = userRepository.findById(inputDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        List<Order> orders = inputDto.getOrderIds().stream()
                .map(orderId -> orderRepository.findById(orderId)
                        .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId)))
                .collect(Collectors.toList());

        // Update de bestaande factuur
        existingInvoice.setInvoiceNumber(inputDto.getInvoiceNumber());
        existingInvoice.setVAT(inputDto.getVAT());
        existingInvoice.setShippingCost(inputDto.getShippingCost());
        existingInvoice.setDate(inputDto.getDate());
        existingInvoice.setUser(user);
        existingInvoice.setItems(orders);
        existingInvoice.setTotalAmount(inputDto.getTotalAmount());

        // Sla de updates op
        invoiceRepository.save(existingInvoice);

        return InvoiceMapper.fromInvoiceToOutputDto(existingInvoice);
    }
    public void deleteInvoice(Long id) {
        if (!invoiceRepository.existsById(id)) {
            throw new IllegalArgumentException("Invoice not found");
        }
        invoiceRepository.deleteById(id);
    }

}