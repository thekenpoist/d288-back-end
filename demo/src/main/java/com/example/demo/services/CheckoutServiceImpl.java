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

        Cart cart = purchase.getCart();

        String orderTrackingNumber = generateOrderTrackingNumber();
        cart.setOrderTrackingNumber(orderTrackingNumber);

        Set<CartItem> cartItem = purchase.getCartItem();
        cartItem.forEach(item -> cart.add(item));

        Customer customer = purchase.getCustomer();
        customer.add(cart);

        cart.setStatus(StatusType.ordered);

        if (purchase.getCartItem().isEmpty() || purchase.getCart() == null || purchase.getCustomer() == null) {
            return new PurchaseResponse("You cannot check out an empty cart.");
        }
        else {
            cartRepository.save(cart);
            return new PurchaseResponse(orderTrackingNumber);
        }

    }

    private String generateOrderTrackingNumber() {

        return UUID.randomUUID().toString();
    }

}
