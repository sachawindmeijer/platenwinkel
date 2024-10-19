package com.example.platenwinkel.controllers;

import com.example.platenwinkel.dtos.input.OrderInputDto;
import com.example.platenwinkel.dtos.mapper.OrderMapper;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.dtos.output.OrderOutputDto;
import com.example.platenwinkel.exceptions.InvalidInputException;
import com.example.platenwinkel.helper.BindingResultHelper;
import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping
    public ResponseEntity<List<OrderOutputDto>>getAllOrders() {
       return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderOutputDto> getOrderById(@PathVariable Long id) {

        OrderOutputDto order = orderService.getOrderById(id);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public ResponseEntity<OrderOutputDto> createOrder(@Valid @RequestBody OrderInputDto orderInputDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InvalidInputException("Somthing went wrong, please check the following fields. " + BindingResultHelper.getErrorMessage(bindingResult));
        }
        OrderOutputDto order= orderService.createOrder(orderInputDto);

        URI uri = URI.create(
                ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .path("/" + order.getId()).toUriString());


        return ResponseEntity.created(uri).body(order);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderOutputDto> updateOrder(@PathVariable Long id, @RequestBody OrderInputDto orderInputDto) {
        Order updatedOrder = orderService.updateOrder(id, orderInputDto.toOrder());
        if (updatedOrder != null) {
            return ResponseEntity.ok(OrderMapper.fromOrderToOutputDto(updatedOrder));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
