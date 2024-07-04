package com.aslzad.datauploader.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Entity
@Table(name = "data_items")
public class DataItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "source")
    private String source;

    @Column(name = "code_list_code")
    private String codeListCode;

    @Column(name = "display_value")
    private String displayValue;

    @Column(name = "long_description")
    private String longDescription;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "from_date")
    private Date  fromDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "sorting_priority")
    private String sortingPriority;

    private DataItem(){
    }

    public DataItem(String code){
        this.code = code;
    }



    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCodeListCode() {
        return codeListCode;
    }

    public void setCodeListCode(String codeListCode) {
        this.codeListCode = codeListCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getSortingPriority() {
        return sortingPriority;
    }

    public void setSortingPriority(String sortingPriority) {
        this.sortingPriority = sortingPriority;
    }
}