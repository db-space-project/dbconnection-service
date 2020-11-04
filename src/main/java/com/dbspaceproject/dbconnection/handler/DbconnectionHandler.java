package com.dbspaceproject.dbconnection.handler;

import com.dbspaceproject.dbconnection.events.DbconnectionCreatedEvent;
import com.dbspaceproject.dbconnection.models.DbconnectionModel;
import com.dbspaceproject.dbconnection.repository.DbconnectionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class DbconnectionHandler {
    private final ApplicationEventPublisher publisher;
    private final DbconnectionRepository dbconnectionRepository;

    public DbconnectionHandler(ApplicationEventPublisher publisher, DbconnectionRepository dbconnectionRepository) {
        this.publisher = publisher;
        this.dbconnectionRepository = dbconnectionRepository;
    }

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getById(ServerRequest request) {
        String dbconnectionId = request.pathVariable("id");
        Mono<DbconnectionModel> organizationModelMono = dbconnectionRepository.findById((dbconnectionId));
        return organizationModelMono
                .flatMap(dbconnectionModel -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(dbconnectionModel))
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> createDbconnection(ServerRequest request) {
        Mono<DbconnectionModel> dbconnectionModelMono = request.bodyToMono(DbconnectionModel.class);
        return dbconnectionModelMono
                .flatMap(dbconnectionModel -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(dbconnectionRepository.save(dbconnectionModel)
                                .doOnSuccess(organization -> this.publisher.publishEvent(new DbconnectionCreatedEvent(organization))), DbconnectionModel.class));
    }
}
