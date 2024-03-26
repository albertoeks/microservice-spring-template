package io.spring.microservice.template.persistence.services;

import io.spring.microservice.template.entities.Customer;
import io.spring.microservice.template.models.CustomerDto;
import io.spring.microservice.template.persistence.repositories.CustomerRepository;
import io.spring.microservice.template.transformer.CustomerTransformer;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final Logger LOG = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(new CustomerTransformer()::toDto).collect(Collectors.toList());
    }

    @Transactional
    public void saveCustomer(CustomerDto customerDto) {
        customerRepository.save(new CustomerTransformer().toEntity(customerDto));
    }

    @Transactional
    public void deleteCustomer(@NonNull Long id) {
        if (customerRepository.existsById(id)) {
            LOG.info("Deleting customer with id {}", id);
            customerRepository.deleteById(id);
        }
        else
            throw new EntityNotFoundException(String.format("Customer id %s not found", id));
    }

    @Transactional
    public void deleteCustomerByLastName(String lastName) {
        List<Customer> customers = customerRepository.findByLastName(lastName);
        if (customers.isEmpty())
            throw new EntityNotFoundException(String.format("Customer name %s not found", lastName));

        LOG.info("Deleting {} customer(s) with last name {}", customers.size(), lastName);
        customerRepository.deleteByLastName(lastName);
    }

}
