package com.example.ecommapp.dto;

import com.example.ecommapp.model.Address;
import com.example.ecommapp.model.OrderItem;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private List<OrderItem> items;
    private Address shippingAddress;
    private double totalAmount;
}
