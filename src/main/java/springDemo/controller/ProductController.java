package springDemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springDemo.model.Product;
import springDemo.service.ProductService;
import springDemo.repository.RepositoryException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        try {
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        } catch (RepositoryException e) {
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Product> getProduct(@PathVariable("sku") String sku) {
        try {
            Product product = productService.getProduct(sku);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (RepositoryException e) {
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            productService.createProduct(product);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (RepositoryException e) {
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{sku}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("sku") String sku) {
        try {
            productService.deleteProduct(sku);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RepositoryException e) {
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        try {
            if (productService.getProduct(product.getSku()) == null) {
                productService.createProduct(product);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else {
                productService.updateProduct(product);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (RepositoryException e) {
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> findProduct(@RequestParam("query") String query) {
        try {
            return new ResponseEntity<>(productService.searchProducts(query), HttpStatus.OK);
        } catch (RepositoryException e) {
            log.error(e.toString());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
