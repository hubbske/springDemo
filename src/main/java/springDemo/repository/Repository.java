package springDemo.repository;

import springDemo.model.Product;

import java.util.List;

public interface Repository {
    void createProduct(Product product) throws RepositoryException;
    void updateProduct(Product product) throws RepositoryException;
    void deleteProduct(String sku) throws RepositoryException;
    Product getProduct(String sku) throws RepositoryException;
    List<Product> findProducts(String search) throws RepositoryException;
    List<Product> getAll () throws RepositoryException;
}
