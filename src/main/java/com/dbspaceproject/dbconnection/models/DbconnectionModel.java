package com.dbspaceproject.dbconnection.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "dbconnection")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DbconnectionModel {
    @Id
    private String id;

    @NotNull
    private String type;

    @NonNull
    private String name;
    private String connectionString;
    private Date lastFailedAt;
    private Date deletedAt;
}
