package io.spring.microservice.template.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CustomerDto(@JsonProperty("firstName") @NotBlank String firstName,
                          @JsonProperty("lastName") @NotBlank String lastName) {
}
