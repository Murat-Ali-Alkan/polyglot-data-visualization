package com.murat.test.repository;

import com.murat.test.entities.CsvData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CsvDataRepository extends MongoRepository<CsvData, String> {
    @Query(value = "{}", fields = "{ 'rowName': 1, 'Col-6': 1 }")
    List<CsvData> findCol6AndRowName();

    Long countByCol6NotNull();




    @Query(value = "{ 'rowName': ?0 }", fields = "{ 'rowName': 1, 'Col-6': 1 }")
    CsvData findByRowName(String rowName);
}
