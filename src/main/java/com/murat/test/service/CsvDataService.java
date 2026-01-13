package com.murat.test.service;

import com.murat.test.entities.CsvData;
import com.murat.test.repository.CsvDataRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvDataService {
    private final CsvDataRepository repository;

    public CsvDataService(CsvDataRepository repository) {
        this.repository = repository;
    }


    public List<CsvData> getCol6Data() {
        return repository.findCol6AndRowName();
    }
}
