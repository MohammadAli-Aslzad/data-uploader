package com.aslzad.datauploader.controller;

import com.aslzad.datauploader.dto.DataItemDto;
import com.aslzad.datauploader.dto.DataResponseDto;
import com.aslzad.datauploader.exception.CsvValidationException;
import com.aslzad.datauploader.response.GenericResponse;
import com.aslzad.datauploader.service.DataService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/data")
public class DataUploadController {

    private final DataService dataService;

    @Value("${file.upload.empty}")
    private String fileUploadEmpty;

    @Value("${file.upload.invalid}")
    private String fileUploadInvalid;

    @Value("${file.upload.successful}")
    private String successfulUpload;
    private GenericResponse<List<DataResponseDto>> response;

    public DataUploadController(DataService dataService) {
        this.dataService = dataService;
    }

    @PostMapping("/upload")
    ResponseEntity<String> uploadCsv(@RequestParam("file") MultipartFile file) throws CsvValidationException, IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body(fileUploadEmpty);
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.endsWith(".csv")) {
            return ResponseEntity.badRequest().body(fileUploadInvalid);
        }

        dataService.UploadCsv(file);

        return ResponseEntity.ok(successfulUpload);
    }

    @DeleteMapping("/")
    ResponseEntity<String> deleteAll() {
        dataService.DeleteAll();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    ResponseEntity<GenericResponse<List<DataResponseDto>>> getAllWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        List<DataResponseDto> dataItemPage = dataService.GetAll(pageable);

        var response = new GenericResponse<List<DataResponseDto>>(dataItemPage);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    ResponseEntity<GenericResponse<List<DataResponseDto>>> getAllWithOutPagination() {
        List<DataResponseDto> dataItemPage = dataService.GetAll();
        var response = new GenericResponse<List<DataResponseDto>>(dataItemPage);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    ResponseEntity<GenericResponse<Optional<DataResponseDto>>> getByCode(@RequestParam String code) {
        var result = dataService.GetByCode(code);
        var response = new GenericResponse<Optional<DataResponseDto>>(result);

        return ResponseEntity.ok(response);
    }
}
