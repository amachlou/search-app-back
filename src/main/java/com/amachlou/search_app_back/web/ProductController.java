package com.amachlou.search_app_back.web;

import com.amachlou.search_app_back.entities.Product;
import com.amachlou.search_app_back.entities.ProductDocument;
import com.amachlou.search_app_back.repositories.elastic.ProductSearchRepository;
import com.amachlou.search_app_back.repositories.jpa.ProductRepository;
import com.amachlou.search_app_back.repositories.search.ProductSearchCustomRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Product API", description = "CRUD and search for products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductSearchRepository productSearchRepository;
    private final ProductSearchCustomRepository productSearchCustomRepository;

    public ProductController(ProductRepository productRepository,
                             ProductSearchRepository productSearchRepository,
                             ProductSearchCustomRepository productSearchCustomRepository) {

        this.productRepository = productRepository;
        this.productSearchRepository = productSearchRepository;
        this.productSearchCustomRepository = productSearchCustomRepository;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product saved = productRepository.save(product);
        productSearchRepository.save(ProductDocument.fromEntity(saved));
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> list = productRepository.findAll();
        return ResponseEntity.ok(list);
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());

            Product saved = productRepository.save(product);
            productSearchRepository.save(ProductDocument.fromEntity(saved));

            return ResponseEntity.ok(saved);
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (!productRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productRepository.deleteById(id);
        productSearchRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<ProductDocument>> searchProducts(@RequestParam(name = "q") String term) {
        List<ProductDocument> results = productSearchRepository.findByNameContainingOrDescriptionContaining(term, term);
        return ResponseEntity.ok(results);
    }
}
