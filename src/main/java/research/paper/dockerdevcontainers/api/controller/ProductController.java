package research.paper.dockerdevcontainers.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import research.paper.dockerdevcontainers.api.dto.ProductDto;
import research.paper.dockerdevcontainers.domain.model.Product;
import research.paper.dockerdevcontainers.service.ProductService;

import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/shoppinglist")
    String getProductPage(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        model.addAttribute("productDto", new ProductDto());
        return "ProductList";
    }

//    @PostMapping
//    public ResponseEntity<Product> createNewProduct(@RequestBody ProductDto product) {
//        Product newProduct = productService.createNewProduct(product);
//        return ResponseEntity.accepted().body(newProduct);
//    }
    @PostMapping("/createnewproduct")
    public String createNewProduct(@ModelAttribute("productDto") ProductDto product) {
        productService.createNewProduct(product);
        return "redirect:/product/shoppinglist";
}

    @GetMapping
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProduct(@RequestParam Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.accepted().build();
    }
    
}
