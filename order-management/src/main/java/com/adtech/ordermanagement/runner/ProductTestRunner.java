//package com.adtech.ordermanagement.runner;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import com.adtech.ordermanagement.repository.ProductRepository;
//
//@Component
//public class ProductTestRunner implements CommandLineRunner {
//
//    private final ProductRepository productRepository;
//
//    public ProductTestRunner(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }
//
//    @Override
//    public void run(String... args) {
//
//        System.out.println("---- Products in DB ----");
//        productRepository.findAll().forEach(p -> {
//            System.out.println(
//                    "ID: " + p.getId() +
//                            ", Name: " + p.getName() +
//                            ", Price: " + p.getPrice() +
//                            ", Stock: " + p.getStock()
//            );
//        });
//    }
//}
