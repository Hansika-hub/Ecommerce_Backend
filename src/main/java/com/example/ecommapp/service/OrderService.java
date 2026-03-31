package com.example.ecommapp.service;

import com.example.ecommapp.dto.OrderRequest;
import com.example.ecommapp.model.Order;
import com.example.ecommapp.model.OrderItem;
import com.example.ecommapp.model.Product;
import com.example.ecommapp.model.ProductVariant;
import com.example.ecommapp.repository.commerce.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public Order placeOrder(String userId, OrderRequest request) {

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new RuntimeException("Order must have at least one item");
        }

        // Verify stock for each item and deduct atomically
        for (OrderItem item : request.getItems()) {
            Product product = productService.getProductById(item.getProductId());

            ProductVariant matchingVariant = product.getVariants().stream()
                    .filter(v -> v.getVariantId().equals(item.getVariantId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Variant not found for Product: " + product.getName()));

            if (matchingVariant.getStock() < item.getQty()) {
                throw new RuntimeException("Insufficient stock for variant: " + matchingVariant.getColor() + " - "
                        + matchingVariant.getSize());
            }

            // Deduct stock via exact query required by assignment
            productService.deductVariantStock(product.getId(), matchingVariant.getVariantId(), item.getQty());
        }

        // Create order snapshot
        Order order = new Order();
        order.setUserId(userId);
        order.setItems(request.getItems());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus("Placed"); // Placed / Shipped / Delivered / Cancelled
        order.setShippingAddress(request.getShippingAddress());
        order.setOrderDate(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // Spec: User orders: db.orders.find({ userId: "U100" }).sort({ orderDate: -1 })
    public List<Order> getMyOrders(String userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order updateStatus(String orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
}