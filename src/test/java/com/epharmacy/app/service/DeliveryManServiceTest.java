package com.epharmacy.app.service;

import com.epharmacy.app.model.DeliveryMan;
import com.epharmacy.app.repository.DeliveryManRepository;
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

class DeliveryManServiceTest {

    @Mock
    private DeliveryManRepository repository;

    @InjectMocks
    private DeliveryManService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Arrange
        Long deliveryManId = 1L;
        DeliveryMan deliveryMan = new DeliveryMan();
        when(repository.findById(deliveryManId)).thenReturn(Optional.of(deliveryMan));

        // Act
        Optional<DeliveryMan> result = service.findById(deliveryManId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(deliveryMan, result.get());
        verify(repository, times(1)).findById(deliveryManId);
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        List<DeliveryMan> deliveryMen = Arrays.asList(
                new DeliveryMan(),
                new DeliveryMan(),
                new DeliveryMan()
        );
        when(repository.findAll()).thenReturn(deliveryMen);

        // Act
        List<DeliveryMan> result = service.getAllCategories();

        // Assert
        assertEquals(deliveryMen, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void testSave() {
        // Arrange
        DeliveryMan deliveryMan = new DeliveryMan();
        when(repository.save(deliveryMan)).thenReturn(deliveryMan);

        // Act
        DeliveryMan result = service.save(deliveryMan);

        // Assert
        assertEquals(deliveryMan, result);
        verify(repository, times(1)).save(deliveryMan);
    }

    @Test
    void testDelete() {
        // Arrange
        DeliveryMan deliveryMan = new DeliveryMan();

        // Act
        service.delete(deliveryMan);

        // Assert
        verify(repository, times(1)).delete(deliveryMan);
    }

    @Test
    void testDeleteAll() {
        // Arrange
        List<DeliveryMan> deliveryMen = Arrays.asList(
                new DeliveryMan(),
                new DeliveryMan(),
                new DeliveryMan()
        );

        // Act
        service.delete(deliveryMen);

        // Assert
        verify(repository, times(1)).deleteAll(deliveryMen);
    }

    @Test
    void testDeleteById() {
        // Arrange
        Long deliveryManId = 1L;

        // Act
        service.deleteById(deliveryManId);

        // Assert
        verify(repository, times(1)).deleteById(deliveryManId);
    }
}