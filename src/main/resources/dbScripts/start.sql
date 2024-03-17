CREATE TABLE `admin` (
                         `id` BIGINT NOT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `cart` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT,
                        `address` VARCHAR(255) NOT NULL,
                        `code` VARCHAR(255) NOT NULL,
                        `total_price` FLOAT(53) NOT NULL,
                        `customer_id` BIGINT NOT NULL,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `FKdebwvad6pp1ekiqy5jtixqbaj` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `cart_item` (
                             `id` BIGINT NOT NULL AUTO_INCREMENT,
                             `base_price` FLOAT(23) NOT NULL,
                             `discount` FLOAT(53) NOT NULL,
                             `quantity` INT NOT NULL,
                             `total_price` FLOAT(23) NOT NULL,
                             `cart_id` BIGINT,
                             `product_id` BIGINT NOT NULL,
                             PRIMARY KEY (`id`),
                             CONSTRAINT `FK1uobyhgl1wvgt1jpccia8xxs3` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
                             CONSTRAINT `FKjcyd5wv4igqnw413rgxbfu4nv` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `category` (
                            `id` BIGINT NOT NULL AUTO_INCREMENT,
                            `category_name` VARCHAR(255) NOT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE TABLE `customer` (
                            `address` VARCHAR(255) NOT NULL,
                            `balance` DECIMAL(38,2) NOT NULL,
                            `phone` VARCHAR(255) NOT NULL,
                            `id` BIGINT NOT NULL,
                            PRIMARY KEY (`id`),
                            CONSTRAINT `FKg2o3t8h0g17smtr9jgypagdtv` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `delivery_man` (
                                `phone` VARCHAR(255) NOT NULL,
                                `id` BIGINT NOT NULL,
                                PRIMARY KEY (`id`),
                                CONSTRAINT `FK83i7mg9lotd2f2wmvgojryjs1` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `image` (
                         `id` BIGINT NOT NULL AUTO_INCREMENT,
                         `file_path` VARCHAR(255) NOT NULL,
                         `name` VARCHAR(255) NOT NULL,
                         `prescription_id` BIGINT NOT NULL,
                         `product_id` BIGINT NOT NULL,
                         PRIMARY KEY (`id`),
                         CONSTRAINT `FKa5u5jeoggvy11kxfwq2ww2nl1` FOREIGN KEY (`prescription_id`) REFERENCES `prescription` (`id`),
                         CONSTRAINT `FKgpextbyee3uk9u6o2381m7ft1` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `order_item` (
                              `id` BIGINT NOT NULL AUTO_INCREMENT,
                              `base_price` FLOAT(23) NOT NULL,
                              `discount` FLOAT(53) NOT NULL,
                              `quantity` INT NOT NULL,
                              `total_price` FLOAT(23) NOT NULL,
                              `order_id` BIGINT NOT NULL,
                              `product_id` BIGINT,
                              PRIMARY KEY (`id`),
                              CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
                              CONSTRAINT `FK551losx9j75ss5d6bfsqvijna` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `orders` (
                          `id` BIGINT NOT NULL AUTO_INCREMENT,
                          `address` VARCHAR(255) NOT NULL,
                          `status` ENUM('INIT','CANCELED','IN_PROGRESS','DELIVERING','COMPLETED','ISSUE','PRESCRIPTION_REFUSED') NOT NULL,
                          `payment_status` ENUM('NOT_PAID','WAITING','PAID'),
                          `total_price` FLOAT(23) NOT NULL,
                          `customer_id` BIGINT NOT NULL,
                          `delivery_man_id` BIGINT NOT NULL,
                          `pharmacist_id` BIGINT NOT NULL,
                          PRIMARY KEY (`id`),
                          CONSTRAINT `FK624gtjin3po807j3vix093tlf` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
                          CONSTRAINT `FK6onv535asmqt7u1j7om2o6lxw` FOREIGN KEY (`delivery_man_id`) REFERENCES `delivery_man` (`id`),
                          CONSTRAINT `FKnv494gmw6qjdqqxc3vlhw2y6t` FOREIGN KEY (`pharmacist_id`) REFERENCES `pharmacist` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `pharmacist` (
                              `phone` VARCHAR(255) NOT NULL,
                              `id` BIGINT NOT NULL,
                              PRIMARY KEY (`id`),
                              CONSTRAINT `FKi5x03tfeqiedmbdu1lcpitc5g` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `prescription` (
                                `id` BIGINT NOT NULL AUTO_INCREMENT,
                                `issue_date` DATETIME(6) NOT NULL,
                                `doctor` VARCHAR(255) NOT NULL,
                                `cart_id` BIGINT NOT NULL,
                                `customer_id` BIGINT NOT NULL,
                                `order_id` BIGINT NOT NULL,
                                PRIMARY KEY (`id`),
                                CONSTRAINT `FKq8tvu62tu9lt49affdqwplnp5` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
                                CONSTRAINT `FK352mb3bhatlofnmky5wnsg9nq` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
                                CONSTRAINT `FKjkgfja3itsiqp0vrs6vqcgovy` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `product` (
                           `id` BIGINT NOT NULL AUTO_INCREMENT,
                           `code` VARCHAR(255) NOT NULL,
                           `name` VARCHAR(255) NOT NULL,
                           `require_prescription` BIT NOT NULL,
                           `price` FLOAT(23) NOT NULL,
                           `stock` BIGINT NOT NULL,
                           `category_id` BIGINT,
                           PRIMARY KEY (`id`),
                           CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `review` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `label` VARCHAR(255),
                          `rate` INT,
                          `customer_id` BIGINT NOT NULL,
                          `delivery_id` BIGINT NOT NULL,
                          `order_id` BIGINT NOT NULL,
                          PRIMARY KEY (`id`),
                          CONSTRAINT `FKgce54o0p6uugoc2tev4awewly` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
                          CONSTRAINT `FKr69bg2vtklebe6dx9novxs9nc` FOREIGN KEY (`delivery_id`) REFERENCES `delivery_man` (`id`),
                          CONSTRAINT `FKnkc5s3da46cbx8oeqrfhnm7es` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB;

CREATE TABLE `user` (
                        `id` BIGINT NOT NULL AUTO_INCREMENT,
                        `created_at` DATETIME(6) NOT NULL,
                        `first_name` VARCHAR(255) NOT NULL,
                        `last_name` VARCHAR(255) NOT NULL,
                        `password` VARCHAR(255) NOT NULL,
                        `role` ENUM('CUSTOMER','DELIVERY_MAN','ADMIN','PHARMACIST'),
                        `status` ENUM('ACTIVE','INACTIVE'),
                        `username` VARCHAR(255) NOT NULL,
                        PRIMARY KEY (`id`),
                        CONSTRAINT `UK_sb8bbouer5wak8vyiiy4pf2bx` UNIQUE (`username`)
) ENGINE=InnoDB;
