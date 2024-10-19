package com.example.platenwinkel.dtos.mapper;

import com.example.platenwinkel.dtos.input.OrderInputDto;
import com.example.platenwinkel.dtos.output.OrderOutputDto;

import com.example.platenwinkel.models.Order;

import java.util.Map;
import java.util.stream.Collectors;

public class OrderMapper {

    // ---------------Dit sit in de mapper fromInputDtoToModel
    public static Order fromInputDToOrder(OrderInputDto orderInputDto) {
        Order order = new Order();

        order.setOrderDate(orderInputDto.orderDate);
        order.setShippingCost(orderInputDto.shippingCost);
        order.setPaymentStatus(orderInputDto.paymentStatus);
        order.setDeliveryStatus(orderInputDto.deliveryStatus);
        order.setShippingAdress(orderInputDto.shippingAdress);

        return order;

    }


    public static OrderOutputDto fromOrderToOutputDto(Order order) {
        OrderOutputDto dto = new OrderOutputDto();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setShippingCost(order.getShippingCost());
        dto.setPaymentStatus(order.getPaymentStatus());
        dto.setDeliveryStatus(order.getDeliveryStatus());
        dto.setShippingAdress(order.getShippingAdress());

        dto.setItems(order.getItems().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().getId(),
                        Map.Entry::getValue
                )));
        return dto;
    }
}
