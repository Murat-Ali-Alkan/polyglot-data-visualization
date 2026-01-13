package com.murat.test.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "data_points")
public class CsvData {
    @Id
    private String id;

    private String rowName;

    @Field("Col-6")
    private Double col6;
}
