package com.udd.udd.service;
import com.udd.udd.dto.SimpleSearchDTO;
import com.udd.udd.model.Applicant;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

public class QueryBuilderService {

    @Autowired
    private static ElasticsearchRestTemplate elasticsearchRestTemplate;


    public static NativeSearchQuery buildQueryApplicant(SimpleSearchDTO dto) {
        String errorMessage = "";
        if (dto.getContent() == null || dto.getContent().equals("")) {
            errorMessage += "Field is empty";
        }
        if (dto.getContent() == null) {
            if (!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value is empty";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        return new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(dto.getContent())
                        .field("firstName")
                        .field("lastName")
                        .type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(20).numOfFragments(1).preTags("<b>").postTags("</b>"))
                .build();
    }



    public static NativeSearchQuery buildQueryEducation(SimpleSearchDTO dto) {
        String errorMessage = "";
        if (dto.getContent() == null || dto.getContent().equals("")) {
            errorMessage += "Field is empty";
        }
        if (dto.getContent() == null) {
            if (!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value is empty";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        return new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(dto.getContent())
                        .field("education")
                )
                .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(40).preTags("<b>").postTags("</b>"))
                .build();
    }

    public static NativeSearchQuery buildQueryCV(SimpleSearchDTO dto) {
        String errorMessage = "";
        if (dto.getContent() == null || dto.getContent().equals("")) {
            errorMessage += "Field is empty";
        }
        if (dto.getContent() == null) {
            if (!errorMessage.equals("")) errorMessage += "\n";
            errorMessage += "Value is empty";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        return new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(dto.getContent())
                        .field("cvContent")
                )
                .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(40).preTags("<b>").postTags("</b>"))
                .build();
    }

}
