package com.udd.udd.service;
import com.udd.udd.dto.AdvancedSearchDTO;
import com.udd.udd.dto.AdvancedSearchRequestDTO;
import com.udd.udd.dto.GeoLocationDTO;
import com.udd.udd.dto.SimpleSearchDTO;
import com.udd.udd.model.Applicant;
import com.udd.udd.model.Location;
import com.udd.udd.model.Operator;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

public class QueryBuilderService {

    @Autowired
    private static ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private static LocationService locationService;


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
                )
                .withHighlightFields(
                        new HighlightBuilder.Field("cvContent").fragmentSize(20).numOfFragments(1)
                                .preTags("<b>").postTags("</b>"))
                .build();
    }

    public static NativeSearchQuery buildQueryApplicantPhrase(SimpleSearchDTO dto) {
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
                .withQuery(QueryBuilders.boolQuery()
                        .should(new MultiMatchQueryBuilder(dto.getContent())
                                .field("firstName")
                                .field("lastName")
                                .type(MultiMatchQueryBuilder.Type.PHRASE))
                )
                .withHighlightFields(
                        new HighlightBuilder.Field("cvContent").fragmentSize(20).numOfFragments(1)
                                .preTags("<b>").postTags("</b>"))
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

        if(dto.getPhrase()){
            return new NativeSearchQueryBuilder()
                    .withQuery(matchPhraseQuery("education", dto.getContent())
                    )
                    .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }else{
            return new NativeSearchQueryBuilder()
                    .withQuery(multiMatchQuery(dto.getContent())
                            .field("education")
                    )
                    .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }
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

        if(dto.getPhrase()){
            return new NativeSearchQueryBuilder()
                    .withQuery(matchPhraseQuery("cvContent", dto.getContent())
                    )
                    .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }else{
            return new NativeSearchQueryBuilder()
                    .withQuery(multiMatchQuery(dto.getContent())
                            .field("cvContent")
                    )
                    .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }
    }


    public static NativeSearchQuery buildQuerysearchByCoverLetter(SimpleSearchDTO dto) {
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

        if(dto.getPhrase()){
            return new NativeSearchQueryBuilder()
                    .withQuery(matchPhraseQuery("clContent", dto.getContent())
                    )
                    .withHighlightFields(new HighlightBuilder.Field("clContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }else{
            return new NativeSearchQueryBuilder()
                    .withQuery(multiMatchQuery(dto.getContent())
                            .field("clContent")
                    )
                    .withHighlightFields(new HighlightBuilder.Field("clContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                    .build();
        }
    }


    public static NativeSearchQuery buildQuerysearchByGeoLocation(GeoLocationDTO dto, Location location) throws Exception {
        String errorMessage = "";
        if (dto.getCity() == null || dto.getCity().equals("")) {
            errorMessage += "Field is empty";
        }

        if (dto.getRadius() == null) {
            errorMessage += "Field is empty";
        }

        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }


        GeoDistanceQueryBuilder geoDistanceBuilder = new GeoDistanceQueryBuilder("location")
                .point(location.getLat(), location.getLon())
                .distance(dto.getRadius(),
                        DistanceUnit.parseUnit("km", DistanceUnit.KILOMETERS));

        return new NativeSearchQueryBuilder()
                .withFilter(geoDistanceBuilder)
                .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                .build();
    }


    public static NativeSearchQuery buildQuerysearchAdvanced(List<AdvancedSearchRequestDTO> dto) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        for(AdvancedSearchRequestDTO req: dto){

            if(req.getOp().equals(Operator.AND.toString())) {
                //if(req.isPhrase()){
                    boolQuery.must(QueryBuilders.matchQuery(req.getCriteria(), req.getContent()));
                //} else {
                  //  boolQuery.must(QueryBuilders.matchQuery(formFieldRequest.getName(), formFieldRequest.getValue()));
                //}
            } else {
                //if(req.isPhrase()) {
                    boolQuery.should(QueryBuilders.matchQuery(req.getCriteria(), req.getContent()));
                //} else {
                  //  boolQuery.should(QueryBuilders.matchQuery(formFieldRequest.getName(), formFieldRequest.getValue()));
               // }
            }
        }

        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .highlighterType("plain")
                .field("cvContent")
                .preTags("<b>")
                .postTags("</b>");

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQuery)
                .withHighlightBuilder(highlightBuilder)
                .build();
        System.out.println(searchQuery);

        return searchQuery;
    }



    }
