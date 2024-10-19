package com.example.platenwinkel.service;

import com.example.platenwinkel.dtos.input.OrderInputDto;
import com.example.platenwinkel.dtos.mapper.LpProductMapper;
import com.example.platenwinkel.dtos.mapper.OrderMapper;
import com.example.platenwinkel.dtos.output.LpProductOutputDto;
import com.example.platenwinkel.dtos.output.OrderOutputDto;
import com.example.platenwinkel.exceptions.RecordNotFoundException;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.repositories.OrderRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderOutputDto> getAllOrders() {
        List<Order> orderlist = orderRepository.findAll();
        List<OrderOutputDto> oderDtoList = new ArrayList<>();

        for (Order orderl : orderlist) {
            OrderOutputDto dto = OrderMapper.fromOrderToOutputDto(orderl);
            oderDtoList.add(dto);
        }
        return oderDtoList;
    }

    public OrderOutputDto getOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            return OrderMapper.fromOrderToOutputDto(order);
        } else {
            throw new RecordNotFoundException("geen lpproduct gevonden");
        }
    }

    public OrderOutputDto createOrder(OrderInputDto orderInputDto) {
        Order o = orderRepository.save(OrderMapper.fromInputDToOrder(orderInputDto));
        o.calculateAndSetShippingCost();
        return OrderMapper.fromOrderToOutputDto(o);
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isPresent()) {
            Order order = existingOrder.get();
            order.setUser(updatedOrder.getUser());
            order.setOrderDate(updatedOrder.getOrderDate());
            order.setItems(updatedOrder.getItems());
            order.calculateAndSetShippingCost();
            order.setPaymentStatus(updatedOrder.getPaymentStatus());
            order.setDeliveryStatus(updatedOrder.getDeliveryStatus());
            order.setShippingAdress(updatedOrder.getShippingAdress());
            return orderRepository.save(order);
        }
        return null; // or throw an exception
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }





}
