package com.adtech.contentgrouptypeservice.service;

import com.adtech.contentgrouptypeservice.model.ContentGroupTypeEntity;
import com.adtech.contentgrouptypeservice.repository.ContentGroupTypeRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;

@Service
public class ContentGroupTypeService {

    private final ContentGroupTypeRepository repository;

    public ContentGroupTypeService(ContentGroupTypeRepository repository) {
        this.repository = repository;
    }

    public Flux<ContentGroupTypeEntity> getContentGroupTypes(
            String search,
            String sortBy,
            String sortOrder,
            List<String> include,
            List<String> exclude
    ) {

        Sort.Direction direction =
                "DESC".equalsIgnoreCase(sortOrder)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Sort sort = Sort.by(direction, sortBy);

        Flux<ContentGroupTypeEntity> source;
        if (search != null && !search.isBlank()) {
            source = repository.findByNameContainingIgnoreCase(search, sort);
        } else {
            source = repository.findAll(sort);
        }

        if (include != null && !include.isEmpty()) {
            source = source.filter(entity ->
                    include.contains(entity.getName())
            );
        }

        if (exclude != null && !exclude.isEmpty()) {
            source = source.filter(entity ->
                    !exclude.contains(entity.getName())
            );
        }

        return source;
    }
}