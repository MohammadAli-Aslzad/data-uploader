package com.aslzad.datauploader.service;

import com.aslzad.datauploader.dto.DataItemDto;
import com.aslzad.datauploader.exception.CsvValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service
public class CsvParserServiceImpl implements CsvParserService {

    @Value("${file.csv.empty}")
    private String csvFileIsEmpty;

    @Value("${file.csv.invalid}")
    private String csvFileIsInvalid;


    @Override
    public List<String[]> parseCsv(MultipartFile file, List<String> expectedColumns) throws IOException, CsvValidationException {
         List<String[]> csvData = new ArrayList<>();
        List<String> headers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String headerLine = reader.readLine();
            if (headerLine == null){
                throw new CsvValidationException(csvFileIsEmpty);
            }

            headers = Arrays.asList(headerLine.split(","));

            HashSet<String> headersSet = new HashSet<>(headers);

            if (!headersSet.containsAll(expectedColumns)){
                throw new CsvValidationException(csvFileIsInvalid);
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                csvData.add(columns);
            }
        }

        return csvData;
    }

    @Override
    public List<DataItemDto> parseCsvWithDto(MultipartFile file) throws IOException, CsvValidationException {
        List<DataItemDto> csvData = new ArrayList<>();
        List<String> headers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            String headerLine = reader.readLine();
            if (headerLine == null){
                throw new CsvValidationException(csvFileIsEmpty);
            }

            headers = Arrays.asList(headerLine.split(","));

            String line;
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split("\",\"");
                columns[0] = columns[0].substring(1);
                columns[columns.length - 1] = columns[columns.length - 1].substring(0, columns[columns.length - 1].length() - 1);
                var row = convertRowToDataItemDto(columns,headers);
                csvData.add(row);
            }
        }

        return csvData;
    }

    public DataItemDto convertRowToDataItemDto(String[] columns, List<String> headers) throws CsvValidationException{
        DataItemDto dataItem = new DataItemDto();
        int index = 0;

        if (columns.length != headers.size()){
            throw new CsvValidationException(csvFileIsInvalid);
        }

        for (String column : columns){
                switch (headers.get(index)){
                    case "\"source\"":
                        dataItem.setSource(column);
                        break;
                    case "\"codeListCode\"":
                        dataItem.setCodeListCode(column);
                        break;
                    case "\"code\"":
                        dataItem.setCode(column);
                        break;
                    case "\"displayValue\"":
                        dataItem.setDisplayValue(column);
                        break;
                    case "\"longDescription\"":
                        dataItem.setLongDescription(column);
                        break;
                    case "\"fromDate\"":
                        dataItem.setFromDate(column);
                        break;
                    case "\"toDate\"":
                        dataItem.setToDate(column);
                        break;
                    case "\"sortingPriority\"":
                        dataItem.setSortingPriority(column);
                        break;
                    default:
                        throw new CsvValidationException(csvFileIsInvalid);
                }

                index++;
        }

        return dataItem;
    }
}
