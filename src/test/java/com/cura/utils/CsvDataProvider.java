package com.cura.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.testng.annotations.DataProvider;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CsvDataProvider {
    @DataProvider(name = "appointmentData")
    public static Object[][] appointmentData() throws Exception {
        InputStream input = CsvDataProvider.class.getClassLoader()
                .getResourceAsStream("data/appointment-data.csv");

        if (input == null) throw new IllegalStateException("CSV test data not found");

        List<Object[]> rows = new ArrayList<>();

        try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader()
                    .setSkipHeaderRecord(true)
                    .build()
                    .parse(reader);

            for (CSVRecord r : records) {
                rows.add(new Object[]{
                        r.get("facility"),
                        r.get("readmission"),
                        r.get("program"),
                        r.get("visitDate"),
                        r.get("comment")
                });
            }
        }

        return rows.toArray(new Object[0][]);
    }
}
