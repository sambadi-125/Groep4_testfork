package research.paper.dockerdevcontainers.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import research.paper.dockerdevcontainers.api.dto.ProductDto;
import research.paper.dockerdevcontainers.domain.model.Product;
import research.paper.dockerdevcontainers.domain.repository.ProductRepository;

import javax.swing.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class ProductServiceTest {

    private Product product1;
    private Product product2;
    private Product product3;

    @SpyBean
    private ProductRepository repositorySpy;

    @Autowired
    private ProductService testSubject;

    @BeforeEach
    void beforeEach() {
        assertThat(testSubject).isNotNull();
        assertThat(repositorySpy).isNotNull();
        repositorySpy.deleteAll();

        product1 = new Product("Product 1", 2.5);
        product2 = new Product("Product 2", 5);
        product3 = new Product("Product 3", 7.5);
        repositorySpy.save(product1);
        repositorySpy.save(product2);
        repositorySpy.save(product3);
    }

    @AfterEach
    void afterEach() {
        repositorySpy.deleteAll();
    }

    @Test
    void createNewProduct() {
        ProductDto testProduct = new ProductDto("Test", 2.5);
        Product newProduct = testSubject.createNewProduct(testProduct);
        verify(repositorySpy).save(newProduct);

        assertThat(newProduct).isNotNull();
        assertThat(newProduct.getId()).isNotNull();
        assertThat(newProduct.getName()).isEqualTo(testProduct.getName());
        assertThat(newProduct.getPrice()).isEqualTo(testProduct.getPrice());
    }

    @Test
    void getAllProducts() {
        List<Product> actual = testSubject.getAllProducts();

        assertThat(actual).hasSize(3);
        assertThat(actual).containsExactlyInAnyOrder(product1, product2, product3);
    }

    @Test
    void deleteProduct() {
        testSubject.deleteProduct(product2.getId());

        List<Product> actual = repositorySpy.findAll();
        assertThat(actual).hasSize(2);
        assertThat(actual).doesNotContain(product2);
    }
}
