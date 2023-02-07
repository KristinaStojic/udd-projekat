package com.udd.udd.controller;

import com.udd.udd.dto.SimpleSearchDTO;
import com.udd.udd.service.QueryBuilderService;
import com.udd.udd.service.SearchService;
import org.apache.lucene.util.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search")
//@CrossOrigin(origins = "http://localhost:4200")
public class SearchController {

    @Autowired
    SearchService searchService;

    @PostMapping(value = "/simple")
    public ResponseEntity<?> simpleSearch(@RequestBody SimpleSearchDTO dto) {
        NativeSearchQuery query = QueryBuilderService.buildQuery(dto);

        return new ResponseEntity<>(searchService.simpleSearch(query), HttpStatus.OK);

    }
}
