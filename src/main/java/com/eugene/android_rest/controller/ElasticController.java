package com.eugene.android_rest.controller;

import com.eugene.android_rest.domain.ElasticResponse;
import com.eugene.android_rest.domain.User;
import com.eugene.android_rest.elasticsearch.services.interfaces.ElasticService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/search")
public class ElasticController {

    private final ElasticService service;

    @Autowired
    public ElasticController(ElasticService service) {
        this.service = service;
    }

    @GetMapping
    public ElasticResponse search(@RequestParam("q") String searchQuery) {
        ElasticResponse response = new ElasticResponse();

        if (searchQuery == null || searchQuery.isEmpty()) {
            return response;
        }

        log.info("Searching for \"{}\" ...", searchQuery);

        response.setUsers(service.search(searchQuery, User.class));

        return response;
    }
}
