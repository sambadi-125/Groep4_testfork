package research.paper.dockerdevcontainers.domain;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import research.paper.dockerdevcontainers.domain.model.Product;
import research.paper.dockerdevcontainers.domain.repository.ProductRepository;

import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner commandLineRunner(ProductRepository productRepository) {
        return args -> {
            Product apple = new Product("Apple", 1.75);
            Product orange = new Product("Orange", 2.50);
            Product banana = new Product("Banana", 0.99);
            Product grape = new Product("Grape", 3.25);
            Product watermelon = new Product("Watermelon", 5.50);
            Product peach = new Product("Peach", 1.25);
            productRepository.saveAll(List.of(apple, orange, banana, grape, watermelon, peach));
        };
    }
}
