package com.udd.udd.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Document(indexName = "applicant")
@Setting(settingPath = "static/es-settings.json")
public class Applicant {

    @Id
    @Field(type = FieldType.Keyword, index = false, store = true)
    private String id;

    @Field(type = FieldType.Text, store = true) //, analyzer = "serbian",
    private String firstName;

    @Field(type = FieldType.Text, store = true) //, analyzer = "serbian"
    private String lastName;

    @Field(type = FieldType.Text, store = true)
    private String education;

    @GeoPointField
    private GeoPoint location;

    @Field(type = FieldType.Text, store = true)
    private String cvContent;

    @Field(type = FieldType.Text, store = true)
    private String clContent;
}
