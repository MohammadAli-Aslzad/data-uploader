package com.aslzad.datauploader.dto;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class DataResponseDto {
        private UUID id;
        private String code;
        private String source;
        private String codeListCode;
        private String displayValue;
        private String longDescription;
        private Date  fromDate;
        private Date toDate;
        private String sortingPriority;
}


