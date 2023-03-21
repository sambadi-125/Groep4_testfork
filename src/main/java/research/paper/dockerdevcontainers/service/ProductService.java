package research.paper.dockerdevcontainers.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import research.paper.dockerdevcontainers.api.dto.ProductDto;
import research.paper.dockerdevcontainers.domain.model.Product;
import research.paper.dockerdevcontainers.domain.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    @Autowired
    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product createNewProduct(ProductDto product) {
        return repository.save(new Product(product.getName(), product.getPrice()));
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}
