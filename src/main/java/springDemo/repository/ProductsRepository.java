package springDemo.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import springDemo.model.Product;

import java.util.List;

@Component
public class ProductsRepository implements Repository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ProductsRepository(NamedParameterJdbcTemplate template) {
        jdbcTemplate = template;
    }

    @Override
    public List<Product> getAll() {
        String sql = "SELECT sku, brand, name, price FROM products;";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    @Override
    public Product getProduct(String sku) {
        String sql = "SELECT sku, brand, name, price FROM products WHERE sku = :sku;";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("sku", sku);

        try {
            return jdbcTemplate.queryForObject(sql, parameters, new ProductRowMapper());
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void createProduct(Product product) throws RepositoryException {
        String sql = "INSERT INTO products (sku, brand, name, price) VALUES (:sku, :brand, :name, :price);";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("sku", product.getSku())
                .addValue("brand", product.getBrand())
                .addValue("name", product.getName())
                .addValue("price", product.getPrice());

        try {
            jdbcTemplate.update(sql, parameters);
        } catch (DataAccessException e) {
            throw new RepositoryException("Error creating: " + product, e);
        }
    }

    @Override
    public void updateProduct(Product product) throws RepositoryException {
        String sql = "UPDATE products SET brand = :brand, name = :name, price = :price WHERE sku = :sku";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("sku", product.getSku())
                .addValue("brand", product.getBrand())
                .addValue("name", product.getName())
                .addValue("price", product.getPrice());

        try {
            jdbcTemplate.update(sql, parameters);
        } catch (DataAccessException e) {
            throw new RepositoryException("Error updating " + product, e);
        }
    }

    @Override
    public void deleteProduct(String sku) throws RepositoryException {
        String sql = "DELETE FROM products WHERE sku = :sku";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("sku", sku);

        try {
            jdbcTemplate.update(sql, parameters);
        } catch (DataAccessException e) {
            throw new RepositoryException("Error deleting " + sku, e);
        }
    }

    @Override
    public List<Product> findProducts(String search) {
        String sql = "select sku, brand, name, price from products where name like CONCAT('%', CONCAT(:search, '%'));";

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("search", search);

        return jdbcTemplate.query(sql, parameters, new ProductRowMapper());
    }
}
