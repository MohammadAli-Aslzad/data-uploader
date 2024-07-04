package com.aslzad.datauploader.service;

import com.aslzad.datauploader.dto.DataItemDto;
import com.aslzad.datauploader.dto.DataResponseDto;
import com.aslzad.datauploader.exception.CsvValidationException;
import com.aslzad.datauploader.model.DataItem;
import com.aslzad.datauploader.repository.DataItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;


@Service
public class DataService {

    private final CsvParserService csvParser;
    private final DataItemRepository dataItemRepository;


    public DataService(CsvParserService csvParser, DataItemRepository dataItemRepository) {
        this.csvParser = csvParser;
        this.dataItemRepository = dataItemRepository;
    }

    public void UploadCsv(MultipartFile file) throws IOException, CsvValidationException {
        var dataItemDtos = csvParser.parseCsvWithDto(file);

        List<DataItem> dataItems = dataItemDtos.stream().map(this::convertDtoToEntity).toList();

        dataItemRepository.saveAll(dataItems);

    }

    public List<DataResponseDto> GetAll() {
        var result = dataItemRepository.findAll();

        return result.stream().map(dataItem -> {
                    var item = new DataResponseDto();
                    DataResponseDto dto = new DataResponseDto();
                    dto.setId(dataItem.getId());
                    dto.setSource(dataItem.getSource());
                    dto.setCodeListCode(dataItem.getCodeListCode());
                    dto.setCode(dataItem.getCode());
                    dto.setDisplayValue(dataItem.getDisplayValue());
                    dto.setLongDescription(dataItem.getLongDescription());
                    dto.setFromDate(dataItem.getFromDate());
                    dto.setToDate(dataItem.getToDate());
                    dto.setSortingPriority(dataItem.getSortingPriority());
                    return dto;
                }
        ).toList();
    }

    public List<DataResponseDto> GetAll(Pageable pageable) {
        var result = dataItemRepository.findAll(pageable);
        return result.stream().map(dataItem -> {
                    var item = new DataResponseDto();
                    DataResponseDto dto = new DataResponseDto();
                    dto.setId(dataItem.getId());
                    dto.setSource(dataItem.getSource());
                    dto.setCodeListCode(dataItem.getCodeListCode());
                    dto.setCode(dataItem.getCode());
                    dto.setDisplayValue(dataItem.getDisplayValue());
                    dto.setLongDescription(dataItem.getLongDescription());
                    dto.setFromDate(dataItem.getFromDate());
                    dto.setToDate(dataItem.getToDate());
                    dto.setSortingPriority(dataItem.getSortingPriority());
                    return dto;
                }
        ).toList();
    }

    public void DeleteAll() {
        dataItemRepository.deleteAll();
    }

    private DataItem convertDtoToEntity(DataItemDto dto) {
        DataItem dataItem = new DataItem(dto.getCode());
        dataItem.setId(dataItem.getId());
        dataItem.setSource(dto.getSource());
        dataItem.setCodeListCode(dto.getCodeListCode());
        dataItem.setCode(dto.getCode());
        dataItem.setDisplayValue(dto.getDisplayValue());
        dataItem.setLongDescription(dto.getLongDescription());
        dataItem.setFromDate(parseDate(dto.getFromDate()));
        dataItem.setToDate(parseDate(dto.getToDate()));
        dataItem.setSortingPriority(dto.getSortingPriority());
        return dataItem;
    }

    public Optional<DataResponseDto> GetByCode(String code) {
        return dataItemRepository.findByCode(code).map(dataItem -> {
            var item = new DataResponseDto();
            DataResponseDto dto = new DataResponseDto();
            dto.setId(dataItem.getId());
            dto.setSource(dataItem.getSource());
            dto.setCodeListCode(dataItem.getCodeListCode());
            dto.setCode(dataItem.getCode());
            dto.setDisplayValue(dataItem.getDisplayValue());
            dto.setLongDescription(dataItem.getLongDescription());
            dto.setFromDate(dataItem.getFromDate());
            dto.setToDate(dataItem.getToDate());
            dto.setSortingPriority(dataItem.getSortingPriority());
            return dto;
        });
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat("dd-MM-yyyy").parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
    }
}
