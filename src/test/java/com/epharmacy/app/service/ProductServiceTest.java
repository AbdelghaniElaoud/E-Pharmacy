package com.epharmacy.app.service;

import com.epharmacy.app.dto.category.CategoryDTO;
import com.epharmacy.app.dto.product.ProductDTO;
import com.epharmacy.app.exceptions.ProductNotFoundException;
import com.epharmacy.app.model.CartItem;
import com.epharmacy.app.model.Category;
import com.epharmacy.app.model.Product;
import com.epharmacy.app.repository.CartItemRepository;
import com.epharmacy.app.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartItemRepository cartItemRepository;

    @InjectMocks
    private ProductService productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(BigDecimal.valueOf(10.0));
        product.setStock(100L);
        product.setActive(true);
        product.setCategory(new Category());
    }

    @Test
    void testFindById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Optional<Product> foundProduct = productService.findById(1L);
        assertTrue(foundProduct.isPresent());
        assertEquals(product, foundProduct.get());
    }

    @Test
    void testGetAllActiveProducts() {
        when(productRepository.findAllByActiveTrue()).thenReturn(Arrays.asList(product));
        List<Product> activeProducts = productService.getAllActiveProducts();
        assertFalse(activeProducts.isEmpty());
        assertEquals(1, activeProducts.size());
        assertEquals(product, activeProducts.get(0));
    }

    @Test
    void testSaveProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product savedProduct = productService.save(product);
        assertNotNull(savedProduct);
        assertEquals(product, savedProduct);
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        productService.delete(1L);
        verify(productRepository, times(1)).deleteById(1L);
        verify(cartItemRepository, times(1)).getCartItemByAddedProduct_Id(1L);
        verify(cartItemRepository, times(1)).deleteAll(any());
    }

    @Test
    void testDeleteProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.delete(1L));
    }

    @Test
    void testUpdateProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Updated Product");
        productDTO.setPrice(BigDecimal.valueOf(20.0));
        productDTO.setStock(50L);
        productDTO.setDescription("Updated description");
        productDTO.setCategory(new CategoryDTO());

        Product updatedProduct = productService.update(1L, productDTO);

        assertNotNull(updatedProduct);
        assertEquals("Updated Product", updatedProduct.getName());
        assertEquals(BigDecimal.valueOf(20.0), updatedProduct.getPrice());
        assertEquals(50L, updatedProduct.getStock());
        assertEquals("Updated description", updatedProduct.getDescription());
    }

    @Test
    void testUpdateProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        ProductDTO productDTO = new ProductDTO();
        assertThrows(ProductNotFoundException.class, () -> productService.update(1L, productDTO));
    }
}