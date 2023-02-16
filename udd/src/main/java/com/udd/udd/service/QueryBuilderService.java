package com.udd.udd.service;
import com.udd.udd.dto.GeoLocationDTO;
import com.udd.udd.dto.SimpleSearchDTO;
import com.udd.udd.model.Applicant;
import com.udd.udd.model.Location;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

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
                .withQuery(matchPhraseQuery("firstName", dto.getContent()))
                        //.field("firstName")
                        //.field("lastName")
                        //.type(MultiMatchQueryBuilder.Type.BEST_FIELDS))
                .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
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
                .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
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
                .withQuery(matchPhraseQuery("cvContent",dto.getContent())
                        //.field("cvContent")
                )
                .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                .build();
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

        return new NativeSearchQueryBuilder()
                .withQuery(matchPhraseQuery("clContent", dto.getContent())
                        //.field("clContent")
                )
                .withHighlightFields(new HighlightBuilder.Field("clContent").fragmentSize(250).preTags("<b>").postTags("</b>"))
                .build();
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



}
