package com.dbspaceproject.dbconnection.services;

import com.dbspaceproject.dbconnection.repository.DbconnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbconnectionService {
    @Autowired
    private DbconnectionRepository dbconnectionRepository;
}
