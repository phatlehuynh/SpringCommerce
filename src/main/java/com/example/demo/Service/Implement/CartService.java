package com.example.demo.Service.Implement;

import com.example.demo.Model.*;
import com.example.demo.Model.Cart;
import com.example.demo.Repository.CartRepository;
import com.example.demo.Repository.ProductRepository;
import com.example.demo.Utilities.CartCreationRequest;
import com.example.demo.Utilities.OrderCreationRequest;
import com.example.demo.Utilities.CartCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService extends BaseService<Cart, CartRepository> {
    @Autowired
    ProductRepository productRepository;
//    public Cart insert(CartCreationRequest cartCreationRequest) throws NoSuchElementException {
//        Cart newCart = cartCreationRequest.getCart();
//        System.out.println(newCart);
//        Map<UUID, Integer> productIdQuantityList = cartCreationRequest.getProductIdQuantityList();
//        for (Map.Entry<UUID, Integer> entry : productIdQuantityList.entrySet()) {
//            UUID productId = entry.getKey();
//            Integer quantity = entry.getValue();
//            System.out.println("++++++++++++++++++++" + productId);
//            System.out.println("++++++++++++++++++++" + quantity);
//        }
//        return repository.save(newCart);
//    }}
    public Cart insert(CartCreationRequest cartCreationRequest) {
        // Tạo đối tượng Cart mới từ cartCreationRequest
        Cart newCart = cartCreationRequest.getCart();
        newCart.setCartProducts(new HashSet<>()); // Khởi tạo danh sách sản phẩm trong giỏ hàng

        // Lấy danh sách sản phẩm và số lượng từ cartCreationRequest
        Map<UUID, Integer> productIdQuantityList = cartCreationRequest.getProductIdQuantityList();

        for (Map.Entry<UUID, Integer> entry : productIdQuantityList.entrySet()) {
            UUID productId = entry.getKey();
            Integer quantity = entry.getValue();

            // Tạo đối tượng CartProduct cho mỗi sản phẩm
            CartProduct cartProduct = new CartProduct();
            cartProduct.setQuantity(quantity);

            // Đặt liên kết giữa Cart và CartProduct
            cartProduct.setCart(newCart);

            // Tạo đối tượng Product và đặt ID của sản phẩm
            Optional<Product> productOptional = productRepository.findById(productId);
            if(productOptional.isPresent()){
                Product product = productOptional.get();
                // Đặt liên kết giữa CartProduct và Product
                cartProduct.setProduct(product);
            } else {
                throw new NoSuchElementException("product have id: " + productId + " is not exists");
            }

            // Thêm CartProduct vào danh sách sản phẩm trong giỏ hàng
            newCart.addCartProduct(cartProduct);
        }

        // Lưu giỏ hàng mới vào cơ sở dữ liệu
        return repository.save(newCart);
    }
}