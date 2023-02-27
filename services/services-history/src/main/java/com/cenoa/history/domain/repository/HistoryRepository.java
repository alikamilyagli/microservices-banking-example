package com.cenoa.history.domain.repository;

import com.cenoa.history.domain.document.History;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HistoryRepository extends MongoRepository<History, String> {

    List<History> findAll(Sort sort);

}
