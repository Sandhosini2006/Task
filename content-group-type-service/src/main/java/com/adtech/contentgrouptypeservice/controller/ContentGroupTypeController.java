package com.adtech.contentgrouptypeservice.controller;

import com.adtech.contentgrouptypeservice.model.ContentGroupTypeEntity;
import com.adtech.contentgrouptypeservice.service.ContentGroupTypeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/v1/content-group-types")
public class ContentGroupTypeController {

    private final ContentGroupTypeService service;

    public ContentGroupTypeController(ContentGroupTypeService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<ContentGroupTypeEntity> listContentGroupTypes(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortOrder,
            @RequestParam(required = false) List<String> include,
            @RequestParam(required = false) List<String> exclude
    ) {
        return service.getContentGroupTypes(search, sortBy, sortOrder, include, exclude);
    }
}