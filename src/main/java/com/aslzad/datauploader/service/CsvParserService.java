package com.aslzad.datauploader.service;

import com.aslzad.datauploader.dto.DataItemDto;
import com.aslzad.datauploader.exception.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CsvParserService {
    List<String[]> parseCsv(MultipartFile file, List<String> expectedColumns) throws IOException, CsvValidationException;

    List<DataItemDto> parseCsvWithDto(MultipartFile file) throws IOException, CsvValidationException;
}
