package research.paper.dockerdevcontainers.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import research.paper.dockerdevcontainers.api.dto.ProductDto;
import research.paper.dockerdevcontainers.domain.model.Product;
import research.paper.dockerdevcontainers.service.ProductService;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    private ProductDto product1, product2;
    @SpyBean
    private ProductService serviceSpy;
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        product1 = new ProductDto("Product 1", 2.5);
        product2 = new ProductDto("Product 2", 5);
    }

    @Test
    void createNewProduct() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .post("/product")
                        .content(objectMapper.writeValueAsString(product1))
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getAllProducts() throws Exception {
        Product newProduct1 = serviceSpy.createNewProduct(product1);
        Product newProduct2 = serviceSpy.createNewProduct(product2);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/product")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<Product> products = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {});

        assertThat(products).hasSize(2);
        assertThat(products).containsExactlyInAnyOrder(newProduct1, newProduct2);
    }

    @Test
    void deleteProduct() {
    }
}
