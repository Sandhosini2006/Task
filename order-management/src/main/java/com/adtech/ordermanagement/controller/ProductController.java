//package com.adtech.ordermanagement.controller;
//
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//import com.adtech.ordermanagement.entity.Product;
//import com.adtech.ordermanagement.service.ProductService;
//
//@RestController
//@RequestMapping("/products")
//public class ProductController {
//
//    private final ProductService productService;
//
//    public ProductController(ProductService productService) {
//        this.productService = productService;
//    }
//
//    @PostMapping
//    public Product createProduct(@RequestBody Product product) {
//        return productService.save(product);
//    }
//
//    @GetMapping
//    public List<Product> getAllProducts() {
//        return productService.getAll();
//    }
//    @PutMapping("/{id}/price")
//    public Product updatePrice(@PathVariable Long id,
//                               @RequestParam Double price) {
//
//        return productService.updatePrice(id, price);
//    }
//    @DeleteMapping("/{id}")
//    public String deleteProduct(@PathVariable Long id) {
//        productService.deleteProduct(id);
//        return "Product deleted successfully";
//    }
//
//}
package com.adtech.ordermanagement.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.adtech.ordermanagement.entity.Product;
import com.adtech.ordermanagement.service.ProductService;
import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }
    @GetMapping("/debug")
    public String debug(Authentication authentication) {
        return authentication.getAuthorities().toString();
    }
    // ADMIN + USER
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAll();
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/price")
    public Product updatePrice(@PathVariable Long id,
                               @RequestParam Double price) {
        return productService.updatePrice(id, price);
    }

    // ADMIN only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "Product deleted successfully";
    }
}
