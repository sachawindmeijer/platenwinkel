package com.example.platenwinkel.dtos.input;

import com.example.platenwinkel.enumeration.DeliveryStatus;
//import com.example.platenwinkel.models.Customer;
import com.example.platenwinkel.models.LpProduct;
import com.example.platenwinkel.models.Order;
import com.example.platenwinkel.models.User;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class OrderInputDto {

    public Long userId;
    public LocalDate orderDate;
    public Double shippingCost;
    public int paymentStatus;
    public DeliveryStatus deliveryStatus;
    public String shippingAdress;
    public Map<Long, Integer> items; // Mapping product ID's to quantities

    public Order toOrder() {
        User user = new User();
        user.setId(this.userId); // Assuming a valid customer ID is provided

        Map<LpProduct, Integer> productItems = items.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> {
                            LpProduct product = new LpProduct();
                            product.setId(entry.getKey());
                            return product;
                        },
                        Map.Entry::getValue
                ));

        return new Order(user, orderDate, shippingCost, paymentStatus, deliveryStatus, shippingAdress, productItems);
    }

}
