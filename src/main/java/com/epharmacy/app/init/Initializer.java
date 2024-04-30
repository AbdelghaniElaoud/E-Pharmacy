package com.epharmacy.app.init;

import com.epharmacy.app.enums.UserRole;
import com.epharmacy.app.enums.UserStatus;
import com.epharmacy.app.model.*;
import com.epharmacy.app.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class Initializer implements CommandLineRunner {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final MediaService mediaService;
    private final CartService cartService;
    private final CustomerService customerService;
    private final DeliveryManService deliveryManService;
    private final PharmacistService pharmacistService;
    private final RoleService roleService;

    public Initializer(ProductService productService, CategoryService categoryService, MediaService mediaService, CartService cartService, CustomerService customerService, DeliveryManService deliveryManService, PharmacistService pharmacistService, RoleService roleService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.mediaService = mediaService;
        this.cartService = cartService;
        this.customerService = customerService;
        this.deliveryManService = deliveryManService;
        this.pharmacistService = pharmacistService;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) throws Exception {
        settingTheRolesTable();
        Product product = createProduct();
        anotherOne();
        anotherOneV1();
        Customer customer = createCustomer();
        Cart newCart = cartService.createNewCart(customer.getId());
        //cartService.addToCart(newCart.getId(), product.getId(), 2L);
        createDeliveryMan();
        createPharmacist();
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
                .prescription(false)
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

    private Product anotherOneV1() {
        Category vitamins = categoryService.save(Category.builder().name("Essential Oils").build());

        Media image1 = mediaService.save(
                Media.builder()
                        .altText("Image 1")
                        .link("https://cdn01.pharmeasy.in/dam/products_otc/L79986/everherb-karela-jamun-juice-helps-maintains-healthy-sugar-levels-helps-in-weight-management-1l-2-1698385993.jpg?dim=700x0&dpr=1&q=100")
                        .build()
        );

        Product product = Product
                .builder()
                .code("eph-000003")
                .name("Everherb Karela Jamun Juice - Helps Maintains Healthy Sugar Levels -Helps In Weight Management - 1l")
                .active(true)
                .price(BigDecimal.valueOf(19.99).setScale(2, RoundingMode.HALF_UP))
                .stock(100L)
                .prescription(false)
                .description("""
                        EverHerb Karela Jamun Juice is formulated with the extracts of Karela or bitter gourd and the whole Jamun fruit. Since ancient times, Karela and Jamun juice have been used to keep blood sugar levels steady. Bitter gourd is a natural blood-sugar controller and has been used by Ayurvedic doctors for centuries to help diabetes patients. When used along with appropriate medication, a healthy diet and regular exercise, EverHerb Karela Jamun Juice for diabetes can be helpful as it keeps blood glucose levels in check.

Moreover, when seeking the finest in natural wellness, EverHerb Karela Jamun Juice stands out as the best Karela Jamun Juice online. The Karela Jamun juice price is pocket-friendly as well. It can help to remove toxins from the blood can aid in improving the body’s immunity to help prevent infections and can also manage cough. Because of its hydrating effect on the bowels, it can help to ease constipation. EverHerb Karela Jamun Juice is 100 % natural and vegan. It contains no added sugar.                        """)
                .category(vitamins)
                .medias(List.of(image1)).build();
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
        Role customerRole = roleService.findByName(UserRole.ROLE_CUSTOMER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(customerRole);
        customer.setRoles(roles);
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

    private DeliveryMan createDeliveryMan() {
        DeliveryMan deliveryMan = DeliveryMan.builder()
                .phone("0744240259")
                .build();
        deliveryMan.setFirstName("abdelghani");
        deliveryMan.setUsername("abdel");
        deliveryMan.setLastName("El aoud");
        deliveryMan.setPassword("1234");
        Role deliveryRole = roleService.findByName(UserRole.ROLE_DELIVERY_MAN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(deliveryRole);
        deliveryMan.setRoles(roles);
        deliveryMan.setStatus(UserStatus.ACTIVE);
        deliveryMan.setEmail("abdelghani.elaoud@gmail.com");
        return deliveryManService.save(deliveryMan);
    }

    private Pharmacist createPharmacist() {
        Pharmacist pharmacist = Pharmacist.builder()
                .phone("0635525677")
                .build();
        pharmacist.setFirstName("Paul");
        pharmacist.setUsername("paul Pharma");
        pharmacist.setLastName("Dolo");
        pharmacist.setPassword("1234");
        Role pharmacistRole = roleService.findByName(UserRole.ROLE_PHARMACIST)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Set<Role> roles = new HashSet<>();
        roles.add(pharmacistRole);
        pharmacist.setRoles(roles);
        pharmacist.setStatus(UserStatus.ACTIVE);
        pharmacist.setEmail("paul.pharma@gmail.com");
        return pharmacistService.save(pharmacist);
    }

    public void settingTheRolesTable(){

        Role admin = new Role(UserRole.ROLE_ADMIN);
        Role deliveryMan = new Role(UserRole.ROLE_DELIVERY_MAN);
        Role pharmacist = new Role(UserRole.ROLE_PHARMACIST);
        Role customer = new Role(UserRole.ROLE_CUSTOMER);
        roleService.save(admin);
        roleService.save(deliveryMan);
        roleService.save(pharmacist);
        roleService.save(customer);

    }
}
