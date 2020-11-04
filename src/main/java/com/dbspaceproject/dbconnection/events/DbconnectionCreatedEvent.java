package com.dbspaceproject.dbconnection.events;

import com.dbspaceproject.dbconnection.models.DbconnectionModel;
import org.springframework.context.ApplicationEvent;

public class DbconnectionCreatedEvent extends ApplicationEvent {

    public DbconnectionCreatedEvent(DbconnectionModel dbconnection) {
        super(dbconnection);
    }
}
