package com.murat.test.controller;

import com.murat.test.entities.CsvData;
import com.murat.test.repository.CsvDataRepository;
import com.murat.test.service.CsvDataService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.murat.test.dto.DataHolder;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/csv")
public class CsvDataController {

    public static int rowCount = 0;
    public static int counter = 1;
    public static String rowName = "Row-";

    @Autowired
    private Function<DataHolder, String> plotFunction;

    private final CsvDataRepository repository;
    private final CsvDataService service;

    public CsvDataController(CsvDataRepository repository, CsvDataService service) {
        this.repository = repository;
        this.service = service;

        rowCount += repository.countByCol6NotNull();
        System.out.println("Row count : "+rowCount);
    }

    @GetMapping("/col6")
    public List<CsvData> getCol6Data() {


        return service.getCol6Data();
    }


    @GetMapping(value = "/plot",produces = "image/svg+xml")
    public ResponseEntity<String> getPlot() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Refresh", "1");

        String svg = "";

        if (counter > rowCount) {
            counter = 1;
        }

        CsvData data = repository
                .findByRowName(rowName+counter);

        if(data == null) {
            System.out.println("No data found "+rowName+counter);
            return new ResponseEntity<>(svg, responseHeaders, HttpStatus.NOT_FOUND);
        }
        else {
            Double y = data.getCol6();


            synchronized (plotFunction) {
                System.out.println(rowName + counter + " : " + y);
                svg = plotFunction.apply(new DataHolder(y));
            }

            counter += 1;

            return new ResponseEntity<String>(svg, responseHeaders, HttpStatus.OK);
        }

    }
}
