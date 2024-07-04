package com.aslzad.datauploader.dto;

import lombok.Data;

@Data
public class DataItemDto {
    private String source;
    private String codeListCode;
    private String code;
    private String displayValue;
    private String longDescription;
    private String fromDate;
    private String toDate;
    private String sortingPriority;
}