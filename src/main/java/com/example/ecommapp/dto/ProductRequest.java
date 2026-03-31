package com.example.ecommapp.dto;

import com.example.ecommapp.model.EmbeddedCategory;
import com.example.ecommapp.model.ProductVariant;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class ProductRequest {

    @NotBlank(message = "Product name is required")
    private String name;

    private String description;

    private String brand;

    private String companyId;

    private EmbeddedCategory category;

    private String status;

    private List<ProductVariant> variants;

    private List<String> images;
}