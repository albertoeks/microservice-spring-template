package io.spring.microservice.template.transformer;

interface Transformer <T, S> {

    S toDto(T entity);

    T toEntity(S dto);

}
