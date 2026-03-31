package com.example.ecommapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductVariant {
    private String variantId;
    private String size;
    private String color;
    private String model;
    private int stock;
    private double price;
}
