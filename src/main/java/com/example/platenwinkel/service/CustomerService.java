package com.example.platenwinkel.service;
//
//import com.example.platenwinkel.models.Customer;
//import com.example.platenwinkel.repositories.CustomerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class CustomerService{
//
//    private final CustomerRepository customerRepository;
//    @Autowired
//    public CustomerService(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }
//
//    // Klant aanmaken
//    public Customer createCustomer(Customer customer) {
//        return customerRepository.save(customer);
//    }
//
//    // Alle klanten ophalen
//    public List<Customer> getAllCustomers() {
//        return customerRepository.findAll();
//    }
//
//    // Een specifieke klant vinden
//    public Optional<Customer> getCustomerById(Long id) {
//        return customerRepository.findById(id);
//    }
//
//    // Klant updaten
//    public Customer updateCustomer(Long id, Customer updatedCustomer) {
//        return customerRepository.findById(id)
//                .map(customer -> {
//                    customer.setName(updatedCustomer.getName());
//                    customer.setAddress(updatedCustomer.getAddress());
//                    customer.setDateOfBirth(updatedCustomer.getDateOfBirth());
//                    return customerRepository.save(customer);
//                }).orElseThrow(() -> new RuntimeException("Customer not found"));
//    }
//
//    // Klant verwijderen
//    public void deleteCustomer(Long id) {
//        customerRepository.deleteById(id);
//    }
//}
//
