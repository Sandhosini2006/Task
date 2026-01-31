package com.adtech.contentgrouptypeservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "fw_content_group_type", schema = "content_presets")
public class ContentGroupTypeEntity {

    @Id
    @Column("content_group_type_id")
    private Integer id;

    @Column("content_group_type_name")
    private String name;

    @Column("created_unix_timestamp")
    private Long createdUnixTimestamp;

    @Column("updated_unix_timestamp")
    private Long updatedUnixTimestamp;

    @Column("ts_vector")
    private String tsVector;

    // getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedUnixTimestamp() {
        return createdUnixTimestamp;
    }

    public void setCreatedUnixTimestamp(Long createdUnixTimestamp) {
        this.createdUnixTimestamp = createdUnixTimestamp;
    }

    public Long getUpdatedUnixTimestamp() {
        return updatedUnixTimestamp;
    }

    public void setUpdatedUnixTimestamp(Long updatedUnixTimestamp) {
        this.updatedUnixTimestamp = updatedUnixTimestamp;
    }

    public String getTsVector() {
        return tsVector;
    }

    public void setTsVector(String tsVector) {
        this.tsVector = tsVector;
    }
}