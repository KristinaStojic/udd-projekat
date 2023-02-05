package com.udd.udd.repository;

import com.udd.udd.model.Applicant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface ApplicantRepository extends ElasticsearchRepository<Applicant, String> {
}
