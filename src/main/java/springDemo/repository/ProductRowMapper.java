package springDemo.repository;

import org.springframework.jdbc.core.RowMapper;
import springDemo.model.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper implements RowMapper<Product> {

    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Product.builder()
                .sku(rs.getString("sku"))
                .brand(rs.getString("brand"))
                .name(rs.getString("name"))
                .price(rs.getInt("price"))
                .build();
    }

}
