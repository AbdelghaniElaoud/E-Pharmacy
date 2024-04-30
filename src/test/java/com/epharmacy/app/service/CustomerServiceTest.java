package com.epharmacy.app.service;

import com.epharmacy.app.model.Customer;
import com.epharmacy.app.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerServiceTest {

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Arrange
        Long customerId = 1L;
        Customer customer = new Customer();
        when(repository.findById(customerId)).thenReturn(Optional.of(customer));

        // Act
        Optional<Customer> result = service.findById(customerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(customer, result.get());
        verify(repository, times(1)).findById(customerId);
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        List<Customer> customers = Arrays.asList(
                new Customer(),
                new Customer(),
                new Customer()
        );
        when(repository.findAll()).thenReturn(customers);

        // Act
        List<Customer> result = service.getAllCategories();

        // Assert
        assertEquals(customers, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testSave() {
        // Arrange
        Customer customer = new Customer();
        when(repository.save(customer)).thenReturn(customer);

        // Act
        Customer result = service.save(customer);

        // Assert
        assertEquals(customer, result);
        verify(repository, times(1)).save(customer);
    }

    @Test
    void testDelete() {
        // Arrange
        Customer customer = new Customer();

        // Act
        service.delete(customer);

        // Assert
        verify(repository, times(1)).delete(customer);
    }

    @Test
    void testDeleteAll() {
        // Arrange
        List<Customer> customers = Arrays.asList(
                new Customer(),
                new Customer(),
                new Customer()
        );

        // Act
        service.delete(customers);

        // Assert
        verify(repository, times(1)).deleteAll(customers);
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long customerId = 1L;

        // Act
        service.deleteById(customerId);

        // Assert
        verify(repository, times(1)).deleteById(customerId);
    }
}