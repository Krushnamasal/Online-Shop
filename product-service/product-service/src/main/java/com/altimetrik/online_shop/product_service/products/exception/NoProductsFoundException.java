package com.altimetrik.online_shop.product_service.products.exception;

public class NoProductsFoundException extends RuntimeException {
        public NoProductsFoundException(String message) {
            super(message);
        }
}
