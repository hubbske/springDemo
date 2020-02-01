package springDemo.service;

import org.springframework.stereotype.Service;
import springDemo.model.Product;
import springDemo.repository.Repository;
import springDemo.repository.RepositoryException;

import java.util.List;

@Service
public class ProductService {

    private final Repository productsRepository;

    public ProductService(Repository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List<Product> getAllProducts() throws RepositoryException {
        return productsRepository.getAll();
    }

    public Product getProduct(String sku) throws RepositoryException {
        return productsRepository.getProduct(sku);
    }

    public void deleteProduct(String sku) throws RepositoryException {
        productsRepository.deleteProduct(sku);
    }

    public void updateProduct(Product product) throws RepositoryException {
        productsRepository.updateProduct(product);
    }

    public void createProduct(Product product) throws RepositoryException {
        productsRepository.createProduct(product);
    }

    public List<Product> searchProducts(String search) throws RepositoryException {
        return productsRepository.findProducts(search);
    }
}
