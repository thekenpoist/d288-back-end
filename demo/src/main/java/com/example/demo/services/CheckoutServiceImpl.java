package com.example.demo.services;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.CartRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;

import jakarta.transaction.Transactional;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    private CartRepository cartRepository;

    @Autowired
    public CheckoutServiceImpl(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {

        try {

            Cart cart = purchase.getCart();

            String orderTrackingNumber = generateOrderTrackingNumber();
            cart.setOrderTrackingNumber(orderTrackingNumber);

            Set<CartItem> cartItem = purchase.getCartItem();
            cartItem.forEach(item -> cart.add(item));
            cart.setStatus(StatusType.ordered);

            Customer customer = purchase.getCustomer();
            customer.add(cart);
            cartRepository.save(cart);

            if (purchase.getCartItem().isEmpty()) {
                return new PurchaseResponse("You cannot check out an empty cart.");
            }
            else {
                cartRepository.save(cart);
                return new PurchaseResponse(orderTrackingNumber);
            }
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Cart is empty!");
        }

    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }

}
