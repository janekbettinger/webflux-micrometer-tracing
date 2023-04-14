package com.example.webfluxmicrometertracing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

@SpringBootApplication
public class WebfluxMicrometerTracingApplication {

  public static void main(String[] args) {
    Hooks.enableAutomaticContextPropagation();
    SpringApplication.run(WebfluxMicrometerTracingApplication.class, args);
  }

}
