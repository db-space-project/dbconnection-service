package com.dbspaceproject.dbconnection.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
public class Dbconnection {
    @Id
    private String type;

    private String name;
    private String connectionString;
    private Date lastFailedAt;
    private Date deletedAt;
}
