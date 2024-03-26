package io.spring.microservice.template.transformer;

import io.spring.microservice.template.entities.Customer;
import io.spring.microservice.template.models.CustomerDto;

public class CustomerTransformer implements Transformer<Customer, CustomerDto>{
    @Override
    public CustomerDto toDto(Customer entity) {
        return new CustomerDto(entity.getFirstName(), entity.getLastName());
    }

    @Override
    public Customer toEntity(CustomerDto dto) {
        return new Customer(dto.firstName(), dto.lastName());
    }
}
