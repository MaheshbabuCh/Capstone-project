package com.example.capstoneproject.dtos;

public class ImageUrlFilter {
    @Override
    public boolean equals(Object obj) {
        // Exclude imageUrl if it is null
        return obj == null;
    }
}
