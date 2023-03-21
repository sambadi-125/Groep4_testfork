package research.paper.dockerdevcontainers.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import research.paper.dockerdevcontainers.domain.model.Product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase
@ComponentScan(basePackages = "research.paper.dockerdevcontainers.domain")
class ProductRepositoryTest {

    private Product testProduct;
    private Long id;
    private static final String NAME = "Test";
    private static final double PRICE = 2.5;
    @Autowired
    private ProductRepository testSubject;

    @BeforeEach
    void beforeEach() {
        assertThat(testSubject).isNotNull();
        testSubject.deleteAll();
        id = testSubject.save(new Product(NAME, PRICE)).getId();
        testProduct = testSubject.findById(id).orElse(null);
    }

    @AfterEach
    void afterEach() {
        testSubject.deleteAll();
    }

    @Test
    void itSaves() {
        assertThat(testProduct).isNotNull();
        assertThat(testProduct.getName()).isEqualTo(NAME);
        assertThat(testProduct.getPrice()).isEqualTo(PRICE);

        assertThat(testSubject.findAll()).hasSize(1);
    }

    @Test
    void itUpdates() {
        final String newTestName = "New Test Name";
        final double newTestPrice = testProduct.getPrice() * 2;
        testProduct.setName(newTestName);
        testProduct.setPrice(newTestPrice);
        testSubject.save(testProduct);

        testProduct = testSubject.findById(id).orElse(null);

        assertThat(testProduct).isNotNull();
        assertThat(testProduct.getName()).isEqualTo(newTestName);
        assertThat(testProduct.getPrice()).isEqualTo(newTestPrice);
    }

    @Test
    void itDeletes() {
        testSubject.deleteById(id);

        assertThat(testSubject.findAll()).hasSize(0);
    }
}
