package com.udd.udd.service;

import com.udd.udd.dto.SearchResponseDTO;
import com.udd.udd.model.Applicant;
import org.elasticsearch.action.search.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public List<SearchResponseDTO> simpleSearchApplicant(NativeSearchQuery searchQuery){
        System.out.println(searchQuery.getQuery());
        SearchHits<Applicant> searchHits = elasticsearchRestTemplate.search(searchQuery, Applicant.class, IndexCoordinates.of("applicant"));
        return getSearchResponse(searchHits);
    }

    public List<SearchResponseDTO> simpleSearchEducation(NativeSearchQuery searchQuery){
        System.out.println(searchQuery.getQuery());
        SearchHits<Applicant> searchHits = elasticsearchRestTemplate.search(searchQuery, Applicant.class, IndexCoordinates.of("applicant"));
        return getSearchResponse(searchHits);
    }


    private List<SearchResponseDTO> getSearchResponse(SearchHits<Applicant> searchHits) {
        List<SearchResponseDTO> searchResponses = new ArrayList<>();

        for(SearchHit<Applicant> searchHit : searchHits) {
            SearchResponseDTO searchResponse = new SearchResponseDTO();

            searchResponse.setFirstName(searchHit.getContent().getFirstName());
            searchResponse.setLastName(searchHit.getContent().getLastName());
            searchResponse.setEducation(searchHit.getContent().getEducation());
            //searchResponse.setCvId(searchHit.getContent().getCvId());
            System.out.println(searchHit);
            if (searchHit.getHighlightFields().isEmpty()) {
                searchResponse.setHighlight(searchHit.getContent().getCvContent().substring(0, 20) + "...");
            } else {
                searchResponse.setHighlight("..." + searchHit.getHighlightFields().get("cvContent").get(0) + "...");
            }

            searchResponses.add(searchResponse);
        }

        return searchResponses;
    }

}
