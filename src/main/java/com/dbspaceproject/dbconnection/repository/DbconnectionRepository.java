package com.dbspaceproject.dbconnection.repository;

import com.dbspaceproject.dbconnection.model.Dbconnection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbconnectionRepository extends MongoRepository<Dbconnection, String> {
}
