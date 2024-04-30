package com.epharmacy.app.service;

import com.epharmacy.app.model.Pharmacist;
import com.epharmacy.app.repository.PharmacistRepository;
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

class PharmacistServiceTest {

    @Mock
    private PharmacistRepository repository;

    @InjectMocks
    private PharmacistService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Long id = 1L;
        Pharmacist pharmacist = new Pharmacist();
        when(repository.findById(id)).thenReturn(Optional.of(pharmacist));

        Optional<Pharmacist> result = service.findById(id);

        assertTrue(result.isPresent());
        assertEquals(pharmacist, result.get());
        verify(repository, times(1)).findById(id);
    }

    @Test
    void getAllCategories() {
        List<Pharmacist> pharmacists = Arrays.asList(
                new Pharmacist(),
                new Pharmacist(),
                new Pharmacist()
        );
        when(repository.findAll()).thenReturn(pharmacists);

        List<Pharmacist> result = service.getAllCategories();

        assertEquals(pharmacists, result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void save() {
        Pharmacist pharmacist = new Pharmacist();
        when(repository.save(pharmacist)).thenReturn(pharmacist);

        Pharmacist result = service.save(pharmacist);

        assertSame(pharmacist, result);
        verify(repository, times(1)).save(pharmacist);
    }

    @Test
    void delete() {
        Pharmacist pharmacist = new Pharmacist();
        service.delete(pharmacist);

        verify(repository, times(1)).delete(pharmacist);
    }

    @Test
    void deleteById() {
        Long id = 1L;
        service.deleteById(id);

        verify(repository, times(1)).deleteById(id);
    }
}