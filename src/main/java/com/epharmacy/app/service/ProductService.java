package com.epharmacy.app.service;

import com.epharmacy.app.dto.product.ProductDTO;
import com.epharmacy.app.dto.product.ProductRequestDTO;
import com.epharmacy.app.exceptions.ProductNotFoundException;
import com.epharmacy.app.mappers.CategoryMapper;
import com.epharmacy.app.model.CartItem;
import com.epharmacy.app.model.Category;
import com.epharmacy.app.model.Product;
import com.epharmacy.app.repository.CartItemRepository;
import com.epharmacy.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final CartItemRepository cartItemRepository;

    public ProductService(ProductRepository repository,
                          CartItemRepository cartItemRepository) {
        this.repository = repository;
        this.cartItemRepository = cartItemRepository;
    }

    public Optional<Product> findById(Long id){
        return repository.findById(id);
    }
    public List<Product> getAllActiveProducts(){
        return repository.findAllByActiveTrue();
    }

    public Product save(Product item){
        return repository.save(item);
    }
    public void delete(Product item){
        repository.delete(item);
    }
    public void delete(Long productId){
        if (repository.findById(productId).isEmpty()){
            throw new ProductNotFoundException(productId);
        }
        List<CartItem> cartItems = cartItemRepository.getCartItemByAddedProduct_Id(productId);
        if (!cartItems.isEmpty()){
            cartItemRepository.deleteAll(cartItems);
        }
        repository.deleteById(productId);
    }
    public void delete(Iterable<Product> items){
        repository.deleteAll(items);
    }
    public void deleteById(Long id){
        repository.deleteById(id);
    }

    public Product update(Long id, ProductDTO productDTO) {
        Optional<Product> productOptional = repository.findById(id);

        if (productOptional.isEmpty()){
            throw new ProductNotFoundException(id);
        }

        Product product = productOptional.get();
        product.setCode(productDTO.getCode());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setStock(productDTO.getStock());
        product.setDescription(productDTO.getDescription());
        product.setCategory(CategoryMapper.INSTANCE.toModel(productDTO.getCategory(),new Category()));

        final Product updatedProduct = repository.save(product);
        return updatedProduct;
    }

}
