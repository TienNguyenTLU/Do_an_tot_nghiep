package tlu.web.doan.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import tlu.web.doan.models.entities.Brand;
import tlu.web.doan.models.entities.Product;
import tlu.web.doan.models.entities.User;
import tlu.web.doan.models.enums.UserRole;
import tlu.web.doan.repositories.BrandRepository;
import tlu.web.doan.repositories.ProductRepository;
import tlu.web.doan.repositories.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        seedUsers();
        seedBrandsAndProducts();
    }

    private void seedUsers() {
        if (userRepository.count() == 0) {
            List<User> users = new ArrayList<>();
            // 1 Admin
            users.add(User.builder()
                    .email("admin@test.com")
                    .passwordHash(passwordEncoder.encode("password"))
                    .fullName("Admin User")
                    .role(UserRole.ROLE_ADMIN)
                    .build());

            // 2 Sale Staff
            users.add(User.builder()
                    .email("sale1@test.com")
                    .passwordHash(passwordEncoder.encode("password"))
                    .fullName("Sale Staff 1")
                    .role(UserRole.ROLE_SALE_STAFF)
                    .build());
            users.add(User.builder()
                    .email("sale2@test.com")
                    .passwordHash(passwordEncoder.encode("password"))
                    .fullName("Sale Staff 2")
                    .role(UserRole.ROLE_SALE_STAFF)
                    .build());

            // 2 Customers
            users.add(User.builder()
                    .email("customer1@test.com")
                    .passwordHash(passwordEncoder.encode("password"))
                    .fullName("Customer 1")
                    .role(UserRole.ROLE_CUSTOMER)
                    .build());
            users.add(User.builder()
                    .email("customer2@test.com")
                    .passwordHash(passwordEncoder.encode("password"))
                    .fullName("Customer 2")
                    .role(UserRole.ROLE_CUSTOMER)
                    .build());
            userRepository.saveAll(users);
        }
    }

    private void seedBrandsAndProducts() {
        if (brandRepository.count() == 0) {
            String[] brandNames = {
                    "Asus", "MSI", "Gigabyte", "Intel", "AMD",
                    "Corsair", "NZXT", "Logitech", "Razer", "Cooler Master",
                    "Samsung", "Western Digital", "Seagate", "Kingston", "G.Skill",
                    "EVGA"
            };

            for (String brandName : brandNames) {
                Brand brand = Brand.builder()
                        .name(brandName)
                        .slug(brandName.toLowerCase().replace(" ", "-") + "-" + UUID.randomUUID().toString().substring(0, 5))
                        .country("Global")
                        .build();
                brand = brandRepository.save(brand);

                // Create 2 products for each brand
                for (int i = 1; i <= 2; i++) {
                    String slug = (brandName.toLowerCase().replace(" ", "-") + "-product-" + i + "-" + UUID.randomUUID().toString().substring(0, 5));
                    String sku = "SKU-" + brandName.toUpperCase().replace(" ", "").substring(0, Math.min(3, brandName.length())) + "-" + i + "-" + UUID.randomUUID().toString().substring(0, 5);
                    
                    Product product = Product.builder()
                            .name(brandName + " Product " + i)
                            .sku(sku)
                            .slug(slug)
                            .sellingPrice(BigDecimal.valueOf(100.00 * i))
                            .costPrice(BigDecimal.valueOf(80.00 * i))
                            .stockQuantity(10 * i)
                            .status("ACTIVE")
                            .brand(brand)
                            .build();
                    productRepository.save(product);
                }
            }
        }
    }
}
