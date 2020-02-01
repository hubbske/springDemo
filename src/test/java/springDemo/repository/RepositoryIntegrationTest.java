package springDemo.repository;

import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import springDemo.model.Product;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RepositoryIntegrationTest {

    private static ProductsRepository repo;

    private Product product;

    @Autowired
    private NamedParameterJdbcTemplate template;

    @ClassRule
    public static final PostgreSQLContainer container = new PostgreSQLContainer<>();

    @BeforeAll
    public void setUpClass() {
        repo = new ProductsRepository(template);
    }

    @BeforeEach
    void setUp() {
        product = Product.builder()
                .sku("mypr-100-100")
                .brand("test")
                .name("test")
                .price(1000)
                .build();
    }

    @Test
    @Sql("classpath:test-schema.sql")
    void testGetProduct() throws RepositoryException {
        repo.createProduct(product);
        assertEquals(product, repo.getProduct(product.getSku()));
    }

    @Test
    @Sql("classpath:test-schema.sql")
    void testGetProductReturnsNullIfDoesNotExistExist() {
        assertNull(repo.getProduct(product.getSku()));
    }

    @Test
    @Sql("classpath:test-schema.sql")
    public void testDeleteProduct() throws RepositoryException {
        repo.createProduct(product);
        repo.deleteProduct(product.getSku());

        assertNull(repo.getProduct(product.getSku()));
    }

    @Test
    @Sql("classpath:test-schema.sql")
    public void testGetAll() throws RepositoryException {
        Product newProduct = Product.builder()
                .sku("mypr-300-300")
                .brand("test")
                .name("test")
                .price(1000)
                .build();

        repo.createProduct(product);
        repo.createProduct(newProduct);

        List<Product> products = repo.getAll();

        assertEquals(2, products.size());
        assertTrue(products.contains(product));
        assertTrue(products.contains(newProduct));
    }

    @Test
    @Sql("classpath:test-schema.sql")
    public void testFindProducts() throws RepositoryException {
        repo.createProduct(product);

        List<Product> products = repo.findProducts(product.getName());

        assertTrue(products.contains(product));
    }

    @Test
    @Sql("classpath:test-schema.sql")
    public void testUpdateProduct() throws RepositoryException {
        Product updatedProduct = Product.builder()
                .sku(product.getSku())
                .brand("newValue")
                .name("newValue")
                .price(20000)
                .build();

        repo.createProduct(product);
        repo.updateProduct(updatedProduct);

        Product retrievedProduct = repo.getProduct(product.getSku());

        assertEquals(updatedProduct, retrievedProduct);
    }
}
