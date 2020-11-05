package com.dbspaceproject.dbconnection.handler;

import com.dbspaceproject.dbconnection.dto.OrganizationDTO;
import com.dbspaceproject.dbconnection.events.DbconnectionCreatedEvent;
import com.dbspaceproject.dbconnection.exception.DbconnectionException;
import com.dbspaceproject.dbconnection.models.DbconnectionModel;
import com.dbspaceproject.dbconnection.repository.DbconnectionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreakerFactory;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DbconnectionHandler {
    @LoadBalanced
    private final WebClient.Builder loadbalancerWebclient;

    private final ReactiveCircuitBreakerFactory circuitBreakerFactory;
    public final DiscoveryClient discoveryClient;
    private final ApplicationEventPublisher publisher;
    private final DbconnectionRepository dbconnectionRepository;

    static Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    public Mono<ServerResponse> getById(ServerRequest request) {
        log.info("Query with ID: {}", request.pathVariable("id"));

        List<ServiceInstance> list = discoveryClient.getInstances("organization");
        if (list != null && list.size() > 0 ) {
            String organizationService = list.get(0).getUri().toString();
            String organizationURI = UriComponentsBuilder.fromHttpUrl(organizationService + "/organization/5fa3465d71260b46fe7e38bc")
                    .toUriString();
            Mono<OrganizationDTO> customerInfo = loadbalancerWebclient.build()
                    .get()
                    .uri(organizationURI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            clientResponse -> Mono.error(new DbconnectionException(HttpStatus.BAD_REQUEST, "Error retrieving Organization")))
                    .bodyToMono(OrganizationDTO.class);
            customerInfo.subscribe(result -> System.out.println(result.getName()));
        }

        String dbconnectionId = request.pathVariable("id");
        Mono<DbconnectionModel> organizationModelMono = dbconnectionRepository.findById((dbconnectionId));
        Mono<ServerResponse> dbconnectionResponse =  this.circuitBreakerFactory.create("delay").run(organizationModelMono
                .flatMap(dbconnectionModel -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(dbconnectionRepository.save(dbconnectionModel)
                        .doOnSuccess(dbconnection -> this.publisher.publishEvent(new DbconnectionCreatedEvent(dbconnection))), DbconnectionModel.class))
                .switchIfEmpty(notFound));
        return dbconnectionResponse;
    }

    public Mono<ServerResponse> createDbconnection(ServerRequest request) {
        Mono<DbconnectionModel> dbconnectionModelMono = request.bodyToMono(DbconnectionModel.class);
        return dbconnectionModelMono
                .flatMap(dbconnectionModel -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(dbconnectionRepository.save(dbconnectionModel)
                        .doOnSuccess(organization -> this.publisher.publishEvent(new DbconnectionCreatedEvent(organization))), DbconnectionModel.class));
    }
}
