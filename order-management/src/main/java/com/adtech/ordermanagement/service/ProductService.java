//package com.adtech.ordermanagement.service;
//
//import com.adtech.ordermanagement.exception.BusinessException;
//import org.springframework.stereotype.Service;
//import java.util.List;
//import com.adtech.ordermanagement.entity.Product;
//import com.adtech.ordermanagement.repository.ProductRepository;
//
//@Service
//public class ProductService {
//
//    private final ProductRepository productRepository;
//
//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    public Product save(Product product) {
//
//        // Business rule example
//        if (product.getPrice() <= 0) {
//            throw new RuntimeException("Price must be greater than zero");
//        }
//
//        product.setName(product.getName().toUpperCase());
//
//        return productRepository.save(product);
//    }
//
//    public List<Product> getAll() {
//        return productRepository.findAll();
//    }
//    public Product updatePrice(Long id, Double newPrice) {
//
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        if (newPrice <= 0) {
//            throw new RuntimeException("Price must be greater than zero");
//        }
//
//        product.setPrice(newPrice);
//
//        return productRepository.save(product);
//    }
//    public void deleteProduct(Long id) {
//
//        Product product = productRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Product not found"));
//
//        if (product.getStock() > 0) {
//            throw new BusinessException("Cannot delete product with available stock");
//        }
//
//        productRepository.deleteById(id);
//    }
//
//
//}
package com.adtech.ordermanagement.service;

import com.adtech.ordermanagement.exception.BusinessException;
import com.adtech.ordermanagement.entity.Product;
import com.adtech.ordermanagement.repository.ProductRepository;
import com.adtech.ordermanagement.repository.OrderRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ProductService(ProductRepository productRepository,
                          OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    public Product save(Product product) {

        if (product.getPrice() <= 0) {
            throw new RuntimeException("Price must be greater than zero");
        }

        product.setName(product.getName().toUpperCase());

        return productRepository.save(product);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product updatePrice(Long id, Double newPrice) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (newPrice <= 0) {
            throw new RuntimeException("Price must be greater than zero");
        }

        product.setPrice(newPrice);

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Rule 1 — Stock must be zero
        if (product.getStock() > 0) {
            throw new BusinessException("Cannot delete product with available stock");
        }


    }
}
