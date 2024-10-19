package com.example.platenwinkel.controllers;

//
//import com.example.platenwinkel.models.Customer;
//
//import com.example.platenwinkel.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/customers")
//public class CustomerController {
////    private final CustomerService customerService;
////
////    @Autowired
////    public CustomerController(CustomerService customerService) {
////        this.customerService = customerService;
////    }
////
////    // Een klant aanmaken
////    @PostMapping
////    public Customer createCustomer(@RequestBody Customer customer) {
////        return customerService.createCustomer(customer);
////    }
////
////    // Alle klanten ophalen
////    @GetMapping
////    public List<Customer> getAllCustomers() {
////        return customerService.getAllCustomers();
////    }
////
////    // Een specifieke klant ophalen
////    @GetMapping("/{id}")
////    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
////        return customerService.getCustomerById(id)
////                .map(ResponseEntity::ok)
////                .orElse(ResponseEntity.notFound().build());
////    }
////
////    // Klant updaten
////    @PutMapping("/{id}")
////    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
////        try {
////            Customer updatedCustomer = customerService.updateCustomer(id, customer);
////            return ResponseEntity.ok(updatedCustomer);
////        } catch (RuntimeException e) {
////            return ResponseEntity.notFound().build();
////        }
////    }
////
////    // Klant verwijderen
////    @DeleteMapping("/{id}")
////    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
////        customerService.deleteCustomer(id);
////        return ResponseEntity.noContent().build();
////    }
//}
