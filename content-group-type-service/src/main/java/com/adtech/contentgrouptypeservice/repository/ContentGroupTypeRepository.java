
package com.adtech.contentgrouptypeservice.repository;

import com.adtech.contentgrouptypeservice.model.ContentGroupTypeEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ContentGroupTypeRepository
        extends ReactiveCrudRepository<ContentGroupTypeEntity, Integer> {

    // 1️⃣ Default list with sorting
    Flux<ContentGroupTypeEntity> findAll(Sort sort);

    // 2️⃣ Search by name with sorting
    Flux<ContentGroupTypeEntity> findByNameContainingIgnoreCase(String name, Sort sort);
}