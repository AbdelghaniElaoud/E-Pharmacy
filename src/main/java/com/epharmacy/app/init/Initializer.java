package com.epharmacy.app.init;

import com.epharmacy.app.enums.UserRole;
import com.epharmacy.app.enums.UserStatus;
import com.epharmacy.app.model.*;
import com.epharmacy.app.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class Initializer implements CommandLineRunner {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final MediaService mediaService;
    private final CartService cartService;
    private final CustomerService customerService;

    public Initializer(ProductService productService, CategoryService categoryService, MediaService mediaService, CartService cartService, CustomerService customerService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.mediaService = mediaService;
        this.cartService = cartService;
        this.customerService = customerService;
    }

    @Override
    public void run(String... args) throws Exception {
        Product product = createProduct();
        anotherOne();
        Customer customer = createCustomer();
        Cart newCart = cartService.createNewCart(customer.getId());
        cartService.addToCart(newCart.getId(), product.getId(), 2L);
    }

    private Product anotherOne() {
        Category vitamins = categoryService.save(Category.builder().name("Essential Oils").build());

        Media image1 = mediaService.save(
                Media.builder()
                        .altText("Image 1")
                        .link("https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/clg/clg00757/v/39.jpg")
                        .build()
        );

        Media image2 = mediaService.save(
                Media.builder()
                        .altText("Image 2")
                        .link("https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/clg/clg00757/l/41.jpg")
                        .build()
        );

        Product product = Product
                .builder()
                .code("eph-000002")
                .name("Cliganic, Huile essentielle 100 % pure, Huile essentielle de lavande, 10 ml")
                .active(true)
                .price(BigDecimal.valueOf(19.99).setScale(2, RoundingMode.HALF_UP))
                .stock(100L)
                .prescription(true)
                .description("""
                        Avertissements
                        En raison de sa forte concentration, nous recommandons de toujours diluer ce produit avec une huile de base avant toute utilisation autre que l'aromathérapie.
                                                
                        Réservé à un usage externe. Tenir à l'écart des flammes et des sources de chaleur élevée. Tenir hors de portée des enfants et des animaux de compagnie. Éviter tout contact avec les yeux. Consultez un professionnel de la santé avant utilisation si vous êtes enceinte ou allaitez.
                                                
                        Ces huiles essentielles contiennent des allergènes cutanés connus. Cesser l'utilisation en cas de rougeur ou d'irritation.
                        """)
                .category(vitamins)
                .medias(List.of(image1, image2)).build();
        return productService.save(product);
    }

    private Customer createCustomer() {
        Customer customer = Customer.builder()
                .address("9 rue albert bailly, Marcq-en-baroeul")
                .phone("0744240259")
                .balance(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP))
                .build();
        customer.setFirstName("Jaouad");
        customer.setUsername("jaouadel");
        customer.setLastName("El aoud");
        customer.setPassword("1234");
        customer.setRole(UserRole.CUSTOMER);
        customer.setStatus(UserStatus.ACTIVE);
        customer.setEmail("jaouad.elaoud@gmail.com");
        return customerService.save(customer);
    }

    private Product createProduct() {
        Category vitamins = categoryService.save(Category.builder().name("Vitamins").build());

        Product product = Product
                .builder()
                .code("eph-000001")
                .name("Vitamin B344")
                .active(true)
                .price(BigDecimal.valueOf(9.99).setScale(2, RoundingMode.HALF_UP))
                .stock(100L)
                .prescription(true)
                .description("It will boost your mood")
                .category(vitamins).build();
        Product savedWithoutMedia = productService.save(product);
        Media image1 = mediaService.save(
                Media.builder()
                        .altText("Image 1")
                        .link("https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/now/now00373/v/90.jpg")
                        .build()
        );

        Media image2 = mediaService.save(
                Media.builder()
                        .altText("Image 2")
                        .link("https://cloudinary.images-iherb.com/image/upload/f_auto,q_auto:eco/images/now/now00373/l/95.jpg")
                        .build()
        );
        savedWithoutMedia.setMedias(List.of(image1, image2));
       return productService.save(savedWithoutMedia);
    }
}
