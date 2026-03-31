package com.example.ecommapp.controller;

import com.example.ecommapp.dto.ReviewRequest;
import com.example.ecommapp.model.Review;
import com.example.ecommapp.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // Public API to get reviews for a product
    @GetMapping("/public/product/{productId}")
    public ResponseEntity<List<Review>> getProductReviews(@PathVariable String productId) {
        return ResponseEntity.ok(reviewService.getProductReviews(productId));
    }

    // Customer places review
    @PostMapping("/place")
    @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
    public ResponseEntity<Review> placeReview(
            @RequestBody ReviewRequest request,
            Authentication authentication) {
        String userId = authentication.getName(); // Usually email or Id depending on JWT setup
        return ResponseEntity.ok(reviewService.addReview(userId, request));
    }
}
