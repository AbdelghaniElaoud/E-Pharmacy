package com.epharmacy.app.restcontroller;

import com.epharmacy.app.dto.product.ProductDTO;
import com.epharmacy.app.exceptions.ProductNotFoundException;
import com.epharmacy.app.mappers.ProductMapper;
import com.epharmacy.app.model.Media;
import com.epharmacy.app.model.Product;
import com.epharmacy.app.repository.ProductRepository;
import com.epharmacy.app.service.CategoryService;
import com.epharmacy.app.service.MediaService;
import com.epharmacy.app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/products")
@Slf4j
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final MediaService mediaService;

    public ProductController(ProductService productService, CategoryService categoryService, MediaService mediaService,
                             ProductRepository productRepository) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.mediaService = mediaService;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductDTO> getAllProducts(){
        List<Product> allActiveProducts = productService.getAllActiveProducts();
        return ProductMapper.INSTANCE.convertAll(allActiveProducts);
    }

    @GetMapping("/{productId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ProductDTO getProductById(@PathVariable Long productId){
        Product product = productRepository.findById(productId).get();
        return ProductMapper.INSTANCE.convert(product);
    }

    @PostMapping
    @PreAuthorize("hasRole('PHARMACIST') or hasRole('ADMIN')")
    public ProductDTO createProduct(@RequestBody ProductDTO productDTO){
        Product product = ProductMapper.INSTANCE.convert(productDTO, categoryService);
        return ProductMapper.INSTANCE.convert(productService.save(product));
    }
    @PostMapping("/{productId}/add-media")
    @PreAuthorize("hasRole('PHARMACIST') or hasRole('ADMIN')")
    public ProductDTO createProduct(@PathVariable Long productId, MultipartFile file){
        Optional<Product> productOptional = productService.findById(productId);
        if (productOptional.isEmpty()){
            throw new ProductNotFoundException(productId);
        }
        try {
            String link = mediaService.uploadFile(file);
            Media media = Media.builder().link(link).altText("alt 1").build();
            Product product = productOptional.get();
            List<Media> medias = new ArrayList<>(CollectionUtils.emptyIfNull(product.getMedias()));
            medias.add(mediaService.save(media));
            product.setMedias(medias);
            Product savedProduct = productService.save(product);
            return ProductMapper.INSTANCE.convert(savedProduct);
        } catch (IOException e) {
            log.error("Could not attach media to product {}", productId, e);
        }
        return new ProductDTO();
    }
    @DeleteMapping("{productId}/remove-product")
    @PreAuthorize("hasRole('PHARMACIST') or hasRole('ADMIN')")
    public void deleteProduct(@PathVariable Long productId){
        productService.delete(productId);
    }

    @PutMapping("update-product/{id}")
    @PreAuthorize("hasRole('PHARMACIST') or hasRole('ADMIN')")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){

        return ProductMapper.INSTANCE.convert(productService.update(id, productDTO));
    }
}
