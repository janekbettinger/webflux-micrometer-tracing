package com.example.webfluxmicrometertracing.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class SampleController {

  private static final Logger LOGGER = LoggerFactory.getLogger(SampleController.class);

  private final WebClient webClient;

  public SampleController(WebClient webClient) {
    this.webClient = webClient;
  }

  // ✅ as expected
  @GetMapping("/")
  public Mono<Object> root() {
    LOGGER.info("Called /");
    return Mono.just("OK")
        .cast(Object.class)
        .doOnNext(s -> LOGGER.info("Will return {}", s));
  }

  // ✅ as expected
  @GetMapping("/error")
  public Mono<Object> error() {
    LOGGER.info("Called /error");
    return Mono.error(() -> new RuntimeException("ERROR"))
        .doOnError(throwable -> LOGGER.error("Error: {}", throwable.getMessage()))
        .onErrorResume(throwable -> Mono.just(throwable.getMessage()));
  }

  // ✅ as expected
  @GetMapping("/webclient")
  public Mono<Object> webclient() {
    LOGGER.info("Called /webclient");
    // response includes ´Traceparent` "header" in the body
    return webClient.get()
        .uri("https://httpbin.org/headers")
        .retrieve()
        .bodyToMono(Object.class)
        .doOnNext(s -> LOGGER.info("Received {}", s));
  }

  // ✅ as expected
  @GetMapping("/webclient/error")
  public Mono<Object> webclientError() {
    LOGGER.info("Called /webclient/error");
    return webClient.get()
        .uri("https://httpbin.org/status/400")
        .retrieve()
        .bodyToMono(Object.class)
        .doOnError(throwable -> LOGGER.error("Error: {}", throwable.getMessage()))
        .onErrorResume(throwable -> Mono.just(throwable.getMessage()));
  }

  // ✅ as expected
  @GetMapping("/webclient/onStatus/200")
  public Mono<Object> webClientOnStatus200() {
    LOGGER.info("Called /webclient/onStatus/200");
    return webClient.get()
        .uri("https://httpbin.org/status/200")
        .retrieve()
        .onStatus(HttpStatusCode::isError, clientResponse -> {
          LOGGER.error("Called onStatus with status code {}", clientResponse.statusCode().value());
          return Mono.error(new RuntimeException("ERROR"));
        })
        .bodyToMono(Object.class)
        .doOnNext(s -> LOGGER.info("Received {}", s))
        .doOnError(throwable -> LOGGER.error("Error: {}", throwable.getMessage()))
        .onErrorResume(throwable -> Mono.just(throwable.getMessage()));
  }

  // ⚠️ tracing incomplete
  @GetMapping("/webclient/onStatus/400")
  public Mono<Object> webClientOnStatus400() {
    LOGGER.info("Called /webclient/onStatus/400");
    return webClient.get()
        .uri("https://httpbin.org/status/400")
        .retrieve()
        .onStatus(HttpStatusCode::isError, clientResponse -> {
          LOGGER.error("Called onStatus with status code {}", clientResponse.statusCode().value());
          return Mono.error(new RuntimeException("ERROR"));
        })
        .bodyToMono(Object.class)
        .doOnNext(s -> LOGGER.info("Received {}", s))
        .doOnError(throwable -> LOGGER.error("Error: {}", throwable.getMessage()) /* lacks tracing information */)
        .onErrorResume(throwable -> Mono.just(throwable.getMessage()));
  }

  // ✅ as expected
  @GetMapping("/webclient/onStatus/400/fix")
  public Mono<Object> webClientOnStatus400Fix() {
    LOGGER.info("Called /webclient/onStatus/400/fix");
    return webClient.get()
        .uri("https://httpbin.org/status/400")
        .retrieve()
        .onStatus(HttpStatusCode::isError, clientResponse -> {
          LOGGER.error("Called onStatus with status code {}", clientResponse.statusCode().value());
//          return clientResponse.bodyToMono(Object.class).map(s -> new RuntimeException("ERROR"));
//          return clientResponse.createException();
          return clientResponse.createException().map(s -> new RuntimeException("ERROR"));
        })
        .bodyToMono(Object.class)
        .doOnNext(s -> LOGGER.info("Received {}", s))
        .doOnError(throwable -> LOGGER.error("Error: {}", throwable.getMessage()) /* lacks tracing information */)
        .onErrorResume(throwable -> Mono.just(throwable.getMessage()));
  }

}
