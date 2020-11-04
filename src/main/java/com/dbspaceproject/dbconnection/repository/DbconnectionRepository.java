package com.dbspaceproject.dbconnection.repository;

import com.dbspaceproject.dbconnection.models.DbconnectionModel;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DbconnectionRepository extends ReactiveMongoRepository<DbconnectionModel, String> {
}
